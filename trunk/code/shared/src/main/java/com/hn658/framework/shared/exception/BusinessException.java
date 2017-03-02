/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		BusinessException
 *	包	名：		com.wzitech.chaos.framework.common.exception
 *	项目名称：	chaos-common 
 *	作	者：		Shawn
 *	创建时间：	2012-4-13
 *	描	述：		业务异常基类
 *	更新纪录：	1. Shawn 创建于 2012-4-13 下午3:56:23
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.exception;

/**
 * 业务异常基类
 * @author Shawn
 *
 */
public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * 实现父类自定义构造函数
	 * @param errorCode 错误码
	 * @param args 业务参数信息
	 */
	public BusinessException(String errorCode, String... args) {
		super(errorCode, args);
	}
	
	/**
	 * 实现父类自定义构造函数
	 * @param errorCode
	 * @param errorMsg
	 * @param args
	 */
	public BusinessException(String errorCode, String errorMsg, String... args) {
		super(errorCode, errorMsg, args);
	}

	/**
	 * 实现父类自定义构造函数
	 * @param errorCode 错误码
	 * @param t 内置异常
	 */
	public BusinessException(String errorCode, Throwable t) {
		super(errorCode, t);
	}
}
