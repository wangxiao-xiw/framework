/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	DataAccessException
 *	包        名：	com.wzitech.iboxpayment.framework.exception
 *	项目名称：	iboxpayment-framework-exception 
 *	作        者： 	Shawn
 *	创建时间：	2011-10-16
 *	描        述：
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.exception;

import com.hn658.framework.shared.exception.BaseException;

/**
 * 数据访问类异常基类
 * @author Shawn
 *
 */
public class DataAccessException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5198260996607806993L;
	
	/**
	 * @param errorCode
	 * @param args
	 */
	public DataAccessException(String errorCode, String... args) {
		super(errorCode, args);
	}

	/**
	 * @param errorCode
	 * @param t
	 */
	public DataAccessException(String errorCode, Throwable t) {
		super(errorCode, t);
	}
}
