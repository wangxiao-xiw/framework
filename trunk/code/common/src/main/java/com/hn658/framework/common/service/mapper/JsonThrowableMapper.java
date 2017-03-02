/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		JacksonJsonpProvider
 *	包	名：		com.wzitech.chaos.gaea.jgm.facade.services.providers
 *	项目名称：	jgm-facade
 *	作	者：		Shawn
 *	创建时间：	2012-7-26
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-7-26 上午11:16:06
 * 				
 ************************************************************************************/
package com.hn658.framework.common.service.mapper;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hn658.framework.cache.message.MessageCache;
import com.hn658.framework.common.ResponseStatus;
import com.hn658.framework.exception.ExceptionManager;
import com.hn658.framework.shared.constants.CommonConstants;
import com.hn658.framework.shared.exception.BaseException;
import com.hn658.framework.shared.exception.SystemException;
import com.hn658.framework.shared.exception.UnknowException;
import com.hn658.framework.shared.exception.UserNotLoginException;

public class JsonThrowableMapper implements ExceptionMapper<Throwable> {
	
	/**
	 * 日志记录器
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageCache messageCache;

	@Override
    public Response toResponse(Throwable e) {
    	ExceptionManager.getInstance().getExceptionPolicy().handleException(null, null, null, e);
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
		}else{
			logger.error(LogCategory.APPLICATION, e.getMessage(), e);
			SystemException systemException = this.convert(e);
			baseException = systemException;
		}
        final String localizationMsg = messageCache.getMessage(baseException.getErrorCode());
        ResponseStatus status = new ResponseStatus(baseException.getErrorCode(), localizationMsg);
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("success", false);
		errorMap.put("isException", true);
		errorMap.put("responseStatus", status);
		if(logger.isDebugEnabled()){
			errorMap.put("stackTrace", ExceptionUtils.getStackTrace(e));			
		}
		return Response.status(Response.Status.OK).type(CommonConstants.JSON_CONTENT_TYPE).entity(errorMap).build();
    }
	
	private SystemException convert(Throwable target) {
		if (SystemException.class.isAssignableFrom(target.getClass())) {
			return (SystemException) target;
		}
		return new UnknowException(target.toString(), target);
	}
}