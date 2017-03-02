/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		SystemExceptin
 *	包	名：		com.wzitech.chaos.framework.common.exception
 *	项目名称：	chaos-common 
 *	作	者：		Shawn
 *	创建时间：	2012-4-13
 *	描	述：		系统异常基类（Checked Exception）
 *	更新纪录：	1. Shawn 创建于 2012-4-13 下午3:57:22
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.exception;


/**
 * 系统异常基类（UnChecked Exception）
 * @author Shawn
 *
 */
public class SystemException extends BaseException {
	
	private static final long serialVersionUID = -8569167241632276818L;

	/**
	 * 通过错误码及业务信息构造SystemException
	 * @param errorCode
	 * @param args
	 */
	public SystemException(String errorCode, String... args) {
		super(errorCode, args);
	}

	/**
	 * 通过错误码及错误异常构造SystemException
	 * @param errorCode
	 * @param t
	 */
	public SystemException(String errorCode, Throwable t) {
		super(errorCode, t);
	}
    
}
