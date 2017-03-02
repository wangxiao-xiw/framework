/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		AbstractBaseService
 *	包	名：		com.wzitech.chaos.framework.server.common
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-7 上午1:07:01
 *	描	述：		服务抽象
 *	更新纪录：	
 * 				
 ************************************************************************************/
package com.hn658.framework.common;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.hn658.framework.cache.message.MessageCache;
import com.hn658.framework.logging.Logger;
import com.hn658.framework.logging.LoggerFactory;
import com.hn658.framework.shared.enums.CommonResponseCodes;
import com.hn658.framework.shared.exception.BusinessException;

/**
 * 服务抽象基类
 * @author Shawn
 *
 */
public abstract class AbstractBaseService {
	/**
	 * 日志记录器
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="messageCache")
	private MessageCache messageCache;
	
	protected void success(AbstractServiceResponse response) {
		success(response, null);
	}

	protected void success(AbstractServiceResponse response, String message) {
		ResponseStatus status = new ResponseStatus();
		if(message==null){
			message = messageCache.getMessage(CommonResponseCodes.Success.getCode());
		}
		response.setSuccess(true);
		response.setIsException(false);
		status.setStatus(CommonResponseCodes.Success.getCode(), message);
		response.setResponseStatus(status);
	}

	protected void error(AbstractServiceResponse response, String message) {
		ResponseStatus status = new ResponseStatus();
		if(message==null){
			message = messageCache.getMessage(CommonResponseCodes.Error.getCode());
		}
		response.setSuccess(false);
		response.setIsException(false);
		status.setStatus(CommonResponseCodes.Error.getCode(), message);
		response.setResponseStatus(status);
	}

	protected void error(AbstractServiceResponse response, BusinessException e) {
		ResponseStatus status = new ResponseStatus();
		String message = messageCache.getMessage(e.getErrorCode());
		if(StringUtils.isEmpty(message)){
			message = e.getErrorMsg();
		}
		if(e.getArgs()!=null&&e.getArgs().length>0){
			message = String.format(message, (Object[])e.getArgs());			
		}
		response.setSuccess(false);
		response.setIsException(false);
		status.setStatus(e.getErrorCode(), message);
		response.setResponseStatus(status);
	}
}
