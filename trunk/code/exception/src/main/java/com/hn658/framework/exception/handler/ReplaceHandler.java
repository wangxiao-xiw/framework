/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	ReplaceHandler
 *	包        名：	com.wzitech.iboxpayment.framework.exception.handler
 *	项目名称：	iboxpayment-framework-exception 
 *	作        者： 	Shawn
 *	创建时间：	2011-10-17
 *	描        述：
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.exception.handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hn658.framework.shared.exception.SystemException;

/**
 * 异常替换处理器
 * 
 * @author Shawn
 * 
 */
public class ReplaceHandler<T extends SystemException> extends GeneralLoggingHandler {

	private static final Log LOG = LogFactory.getLog(ReplaceHandler.class);

	private String exceptionMessage;
	private Class<T> replaceExceptionType;

	public ReplaceHandler() {

	}

	public ReplaceHandler(String exceptionMessage, Class<T> replaceExceptionType) {
		if (replaceExceptionType == null)
			throw new NullPointerException("replaceExceptionType");

		if (!Exception.class.isAssignableFrom(replaceExceptionType)) {
			throw new IllegalArgumentException(String.format(
					"The type {0} must be of type Exception.",
					replaceExceptionType.getName()));
		}

		this.exceptionMessage = exceptionMessage;
		this.replaceExceptionType = replaceExceptionType;
	}

	@Override
	public SystemException handleException(Method method, Object[] args,
			Object sourceObject, Throwable exception) {
		// 记录异常至文件
		LOG.error(formatExceptionMessage(method, args, sourceObject, exception));
		
		try {
			Object[] extraParameters = new Object[] { this.exceptionMessage };
			Constructor<T> ctor = replaceExceptionType
					.getConstructor(String.class);
			return (SystemException) ctor.newInstance(extraParameters);
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * @return the replaceExceptionType
	 */
	public Class<T> getReplaceExceptionType() {
		return replaceExceptionType;
	}

	/**
	 * @param exceptionMessage
	 *            the exceptionMessage to set
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * @param replaceExceptionType
	 *            the replaceExceptionType to set
	 */
	public void setReplaceExceptionType(Class<T> replaceExceptionType) {
		this.replaceExceptionType = replaceExceptionType;
	}
}
