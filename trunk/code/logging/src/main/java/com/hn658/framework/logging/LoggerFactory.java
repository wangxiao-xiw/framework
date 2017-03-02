package com.hn658.framework.logging;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * 日志工厂
 * 
 * @author ztjie
 * @date 2014-7-31 下午3:33:36
 * @since
 * @version
 */
public final class LoggerFactory {
	
	private static Map<String, WeakReference<Logger>> loggerMap = new ConcurrentHashMap<String, WeakReference<Logger>>();

	private LoggerFactory() {
	}

	/**
	 * 
	 * 获得日志对象
	 * @author Think
	 * @date 2014-7-31 下午4:03:26
	 * @param name
	 * @param category
	 * @return
	 * @see
	 */
	public static Logger getLogger(String name) {
		WeakReference<Logger> refLog = loggerMap.get(name);
		if(refLog==null){
			org.slf4j.Logger lo = org.slf4j.LoggerFactory.getLogger(name);
			Logger logger = new Logger(lo);
			WeakReference<Logger> ref = new WeakReference<Logger>(logger);
			loggerMap.put(name, ref);
			return logger;
		}else{
			return refLog.get();			
		}
	}

	/**
	 * 
	 * 获得日志对象
	 * @author Think
	 * @date 2014-7-31 下午4:03:12
	 * @param clazz
	 * @param category
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class clazz) {
		return getLogger(clazz.getName());
	}
}
