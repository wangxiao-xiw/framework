package com.hn658.framework.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hn658.framework.exception.config.ExceptionConfig;
import com.hn658.framework.exception.config.ExceptionHandlerConfig;
import com.hn658.framework.exception.config.ExceptionXmlConfigLoader;
import com.hn658.framework.exception.config.ExceptionsConfig;
import com.hn658.framework.exception.config.HandlerConfig;
import com.hn658.framework.exception.config.HandlersConfig;
import com.hn658.framework.exception.config.RefHandler;
import com.hn658.framework.exception.context.ExceptionConstants;
import com.hn658.framework.exception.handler.IExceptionHandler;

public final class ExceptionManager {

	private static Log log = LogFactory.getLog(ExceptionManager.class);

	private static ExceptionManager exceptionManagerInstance;

	private static ExceptionPolicy exceptionPolicy;
	
	private static Object lock = new Object();

	private ExceptionManager() {
		ExceptionXmlConfigLoader resource = ExceptionXmlConfigLoader
				.getInstance();
		ExceptionHandlerConfig exceptionHandlerConfig = resource.getExceptionHandlerConfig();
		exceptionPolicy = initExceptionPolicy(exceptionHandlerConfig);
	}
	
	/**
	 * 
	 * 获得配置完成的异常处理策略类 
	 * @author ztjie
	 * @date 2014-7-29 上午10:23:14
	 * @return
	 * @see
	 */
	public ExceptionPolicy getExceptionPolicy(){
		return exceptionPolicy;
	}

	/**
	 * 
	 * 获得一个实例
	 * 
	 * @return
	 */
	public static ExceptionManager getInstance() {
		if (exceptionManagerInstance == null) {
			synchronized (lock) {
				if (exceptionManagerInstance == null) {
					exceptionManagerInstance = new ExceptionManager();
				}
			}
		}
		return exceptionManagerInstance;
	}
	
	private ExceptionPolicy initExceptionPolicy(ExceptionHandlerConfig exceptionHandlerConfig){
		ExceptionPolicy exceptionPolicy = new ExceptionPolicy();
		Map<Class<?>, ExceptionPolicyEntry> exceptionPolicyEntryMap = new ConcurrentHashMap<Class<?>, ExceptionPolicyEntry>();// 异常类对应的策略列表
		Map<String, IExceptionHandler> handlerMap = new ConcurrentHashMap<String, IExceptionHandler>();// 异常处理器列表
		if (exceptionPolicyEntryMap.size() == 0) {
			List<ExceptionsConfig> exceptionHandlerConfigs = exceptionHandlerConfig.getExceptionsList();
			if(exceptionHandlerConfigs!=null&&exceptionHandlerConfigs.size()>0){
				for (ExceptionsConfig exceptionsConfig : exceptionHandlerConfigs) {
					List<ExceptionConfig> exceptionConfigs = exceptionsConfig.getExceptionList();
					if(exceptionConfigs!=null&&exceptionConfigs.size()>0){
						for(ExceptionConfig exceptionConfig : exceptionConfigs){
							try {
								Class<?> clazz = Class.forName(exceptionConfig.getClazz());
								if (exceptionPolicyEntryMap.containsKey(clazz)) {
									throw new RuntimeException("异常ID重复！请检查"+ExceptionConstants.EXCEPTION_XML+"文件配置");
								} else {
									List<RefHandler> refHandlers = exceptionConfig.getRefHandlerList();
									List<IExceptionHandler> handlers = new ArrayList<IExceptionHandler>();
									ExceptionPolicyEntry entry = new ExceptionPolicyEntry();
									for(RefHandler ref : refHandlers){
										handlers.add(this.getHandler(exceptionHandlerConfig, handlerMap, ref.getRef()));
									}
									entry.setHandlers(handlers);
									entry.setPostHandlingAction(PostHandlingAction.valueOf(exceptionConfig.getPostHandlingAction()));
									exceptionPolicyEntryMap.put(clazz, entry);
								}
							} catch (ClassNotFoundException e) {
								log.error("全类名为\""+exceptionConfig.getClazz()+"\"的类找不到", e);
								throw new RuntimeException("全类名为\""+exceptionConfig.getClazz()+"\"的类找不到");
							}
						}					
					}
				}
			}
		}
		exceptionPolicy.setPolicyEntries(exceptionPolicyEntryMap);
		return exceptionPolicy;
	}
	
	/**
	 * 
	 * 根据处理器ID获取一个异常处理器对象
	 * @author Think
	 * @date 2014-7-29 上午10:24:18
	 * @param exceptionHandlerConfig 配置信息
	 * @param handlerMap 异常处理器实例缓存
	 * @param handlerId 异常处理器ID
	 * @return
	 * @see
	 */
	private IExceptionHandler getHandler(ExceptionHandlerConfig exceptionHandlerConfig, Map<String, IExceptionHandler> handlerMap, String handlerId) {
		if (handlerMap.size() == 0) {
			for (HandlersConfig handlersConfig : exceptionHandlerConfig.getHandlersList()) {
				for(HandlerConfig handlerConfig : handlersConfig.getHandlerList()){
					String hid = handlerConfig.getId();
					if (handlerMap.containsKey(hid)) {
						throw new RuntimeException("异常处理器ID重复！请检查"+ExceptionConstants.EXCEPTION_XML+"文件配置");
					} else {
						try {
							IExceptionHandler handler = (IExceptionHandler) Class.forName(handlerConfig.getClazz()).newInstance();
							handlerMap.put(handlerId, handler);
						} catch (InstantiationException e) {
							log.error(handlerConfig.getClazz()+"类实例化异常", e);
							throw new RuntimeException(handlerConfig.getClazz()+"类实例化异常");
						} catch (IllegalAccessException e) {
							log.error(handlerConfig.getClazz()+"类实例化异常", e);
							throw new RuntimeException(handlerConfig.getClazz()+"类实例化异常");
						} catch (ClassNotFoundException e) {
							log.error("全类名为\""+handlerConfig.getClazz()+"\"的类找不到", e);
							throw new RuntimeException("全类名为\""+handlerConfig.getClazz()+"\"的类找不到");
						}
					}
				}
			}
		}
		IExceptionHandler exceptionHandler = handlerMap.get(handlerId);
		if (exceptionHandler == null) {
			throw new RuntimeException("异常处理器ID为" + handlerId + "的配置信息不存在，请检查"+ExceptionConstants.EXCEPTION_XML+"配置");
		}
		return exceptionHandler;
	}

}
