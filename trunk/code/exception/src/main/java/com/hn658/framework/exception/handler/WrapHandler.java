/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	WrapHandler
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
 * 异常包装处理器
 * @author Shawn
 * 
 */
public class WrapHandler<T extends SystemException> extends GeneralLoggingHandler {

	private static final Log LOG = LogFactory.getLog(WrapHandler.class);
	
	private String exceptionMessage;
	private Class<T> wrapExceptionClass;

	public WrapHandler(String exceptionMessage, Class<T> wrapExceptionClass) {
		if(null == wrapExceptionClass){
			throw new NullPointerException("wrapExceptionClass");
		}
		
		if(!Exception.class.isAssignableFrom(wrapExceptionClass)){
			throw new IllegalArgumentException(String.format("The type {0} must be of type Exception.",
					wrapExceptionClass.getName()));
		}
		
		this.setExceptionMessage(exceptionMessage);
		this.setWrapExceptionClass(wrapExceptionClass);
	}

	@Override
	public SystemException handleException(Method method, Object[] args,
			Object sourceObject, Throwable exception) {
		// 记录异常至文件
		LOG.error(formatExceptionMessage(method, args, sourceObject, exception));
		
        try {
        	Object[] extraParameters = new Object[2];
            extraParameters[0] = this.exceptionMessage;
            extraParameters[1] = exception;
			Constructor<T> ctor = wrapExceptionClass.getConstructor(String.class, Exception.class);
			return (SystemException) ctor.newInstance(extraParameters);
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	/**
	 * @param exceptionMessage the exceptionMessage to set
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return this.exceptionMessage;
	}

	/**
	 * @param wrapExceptionClass the wrapExceptionClass to set
	 */
	public void setWrapExceptionClass(Class<T> wrapExceptionClass) {
		this.wrapExceptionClass = wrapExceptionClass;
	}

	/**
	 * @return the wrapExceptionClass
	 */
	public Class<T> getWrapExceptionClass() {
		return this.wrapExceptionClass;
	}

}
