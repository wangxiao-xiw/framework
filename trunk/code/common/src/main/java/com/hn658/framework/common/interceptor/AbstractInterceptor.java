package com.hn658.framework.common.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;

import com.hn658.framework.cache.message.MessageCache;
import com.hn658.framework.common.ResponseStatus;
import com.hn658.framework.exception.ExceptionManager;
import com.hn658.framework.logging.Logger;
import com.hn658.framework.logging.LoggerFactory;
import com.hn658.framework.logging.enums.LogCategory;
import com.hn658.framework.security.cache.ISecurityCacheProvider;
import com.hn658.framework.shared.constants.CommonConstants;
import com.hn658.framework.shared.context.RequestContext;
import com.hn658.framework.shared.context.UserContext;
import com.hn658.framework.shared.entity.IUser;
import com.hn658.framework.shared.enums.CommonResponseCodes;
import com.hn658.framework.shared.exception.AccessNotAllowException;
import com.hn658.framework.shared.exception.BaseException;
import com.hn658.framework.shared.exception.SystemException;
import com.hn658.framework.shared.exception.UnknowException;
import com.hn658.framework.shared.exception.UserNotLoginException;
import com.hn658.framework.shared.utils.JsonMapper;

/**
 * 抽象拦截器基类
 * 
 * @author ztjie
 * 
 */
public abstract class AbstractInterceptor extends
		AbstractPhaseInterceptor<Message> {

	public AbstractInterceptor(String phase) {
		super(phase);
	}

	public AbstractInterceptor(String id, String phase) {
		super(id, phase);
	}

	public AbstractInterceptor(String phase, boolean uniqueId) {
		super(phase, uniqueId);
	}

	public AbstractInterceptor(String id, String phase, boolean uniqueId) {
		super(id, phase, uniqueId);
	}

	/**
	 * 日志输出
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageCache messageCache;
	
	@Autowired(required=false)
	protected ISecurityCacheProvider securityCacheProvider;
	
	/**
	 * 
	 * <p>设置当前请求上下文信息</p> 
	 * @author ztjie
	 * @date 2015-11-5 下午1:16:24
	 * @param message
	 * @param request
	 * @param response
	 * @see
	 */
	protected void initContext(Message message, HttpServletRequest request, HttpServletResponse response){
		// 设置当前请求上下文信息
		this.initRequestContext(message, request, response);
		this.initUserContext(message, request, response);
	}
	
	/**
	 * 初始化请求上下文
	 */
	protected void initRequestContext(Message message, HttpServletRequest request, HttpServletResponse response) {
		String path = (String) message.get(Message.PATH_INFO);
        logger.debug(LogCategory.SYSTEM,String.format("当前拦截的URL为 %s", path));

		String realIp = request.getHeader(CommonConstants.SERVICE_REQUEST_HEADER_REAL_IP);
		logger.debug(LogCategory.SYSTEM,
				String.format("当前拦截请求的realIp为%s", realIp));
		//设置请求上下文
		RequestContext.setCurrentContext(path, realIp);
	}

	/**
	 * 初始化用户上下文
	 */
	protected void initUserContext(Message message, HttpServletRequest request, HttpServletResponse response) {
		String uid = request.getHeader(CommonConstants.SERVICE_REQUEST_HEADER_UID);
		if(uid==null){
			uid = (String) request.getAttribute(CommonConstants.SERVICE_REQUEST_HEADER_UID);
			if(uid==null){
				uid = (String) request.getSession().getAttribute(CommonConstants.SERVICE_REQUEST_HEADER_UID);
			}
		}
		logger.debug(LogCategory.SYSTEM, String.format("当前拦截请求的Uid为 %s", uid));
		String authkey = request.getHeader(CommonConstants.SERVICE_REQUEST_HEADER_AUTHKEY);
		if(authkey==null){
			authkey = (String) request.getAttribute(CommonConstants.SERVICE_REQUEST_HEADER_AUTHKEY);
			if(authkey==null){
				authkey = (String) request.getSession().getAttribute(CommonConstants.SERVICE_REQUEST_HEADER_AUTHKEY);
			}
		}
		logger.debug(LogCategory.SYSTEM, String.format("当前拦截请求的authkey为 %s", authkey));
		IUser user = null;
		if (!StringUtils.isEmpty(uid)) {
			if(securityCacheProvider==null){
				throw new SystemException("未实现缓存接口，请实现com.hn658.framework.security.cache.ISecurityCacheProvider接口。");
			}
			user = securityCacheProvider.getUser(Long.valueOf(uid));
		}
		UserContext.setCurrentContext(user, authkey);
	}
	
	/**
	 * 
	 * <p>清理上下文信息</p> 
	 * @author ztjie
	 * @date 2015-11-5 下午1:11:06
	 * @see
	 */
	protected void cleanContext(){
		RequestContext.clean();
		UserContext.clean();
	}

	/**
	 * 
	 * <p>
	 * 处理方法
	 * </p>
	 * 
	 * @author ztjie
	 * @date 2014-8-14 下午5:08:11
	 * @param message
	 * @see
	 */
	public abstract void handle(Message message);

	@Override
	public void handleMessage(Message message) throws Fault {
		// 获取返回值
		HttpServletResponse response = (HttpServletResponse) message
				.getExchange().getInMessage()
				.get(AbstractHTTPDestination.HTTP_RESPONSE);
		try {
			this.handle(message);
		} catch (Throwable e) {
			ExceptionManager.getInstance().getExceptionPolicy()
					.handleException(null, null, null, e);
			BaseException baseException = null;
			if (BaseException.class.isAssignableFrom(e.getClass())) {
				baseException = (BaseException) e;
			}
			if (UserNotLoginException.class.isAssignableFrom(e.getClass())) {
				UserNotLoginException userNotLoginException = (UserNotLoginException) e;
				if (logger.isInfoEnabled()) {
					logger.info(LogCategory.USER, e.getMessage(), e);
				}
				baseException = userNotLoginException;
			}else if (AccessNotAllowException.class.isAssignableFrom(e.getClass())) {
				AccessNotAllowException accessNotAllowException = (AccessNotAllowException) e;
				if (logger.isInfoEnabled()) {
					logger.info(LogCategory.USER, e.getMessage(), e);
				}
				baseException = accessNotAllowException;
			} else {
				logger.error(LogCategory.APPLICATION, e.getMessage(), e);
				SystemException systemException = this.convert(e);
				baseException = systemException;
			}
			final String localizationMsg = messageCache
					.getMessage(baseException.getErrorCode());
			ResponseStatus status = new ResponseStatus(
					baseException.getErrorCode(), localizationMsg);
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("isException", true);
			errorMap.put("responseStatus", status);
			if (logger.isDebugEnabled()) {
				errorMap.put("stackTrace", ExceptionUtils.getStackTrace(e));
			}

			response.setContentType(CommonConstants.JSON_CONTENT_TYPE);
			try {
				response.getOutputStream().write(
						JsonMapper.nonEmptyMapper().toJson(errorMap)
								.getBytes("utf-8"));
				response.getOutputStream().flush();
			} catch (Exception e1) {
				logger.error(LogCategory.APPLICATION, "写出异常返回信息失败", e1);
			}
			// 中断此次请求
			message.getInterceptorChain().abort();
		}
	}

	private SystemException convert(Throwable target) {
		if (SystemException.class.isAssignableFrom(target.getClass())) {
			return (SystemException) target;
		}
		return new UnknowException(target.toString(), target);
	}

}