/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	IExceptionHandler
 *	包        名：	com.wzitech.iboxpayment.framework.exception.handler
 *	项目名称：	iboxpayment-framework-exception 
 *	作        者： 	Shawn
 *	创建时间：	2011-10-16
 *	描        述：
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.exception.handler;

import java.lang.reflect.Method;

import com.hn658.framework.shared.exception.SystemException;

/**
 * 异常处理器接口
 * 所有异常经处理后会被转换成Unchecked Exception.
 * @author Shawn
 *
 */
public interface IExceptionHandler {
	SystemException handleException(Method method, Object[] args, 
			Object sourceObject, Throwable exception);
}
