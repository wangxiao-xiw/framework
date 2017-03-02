/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	PostHandlingAction
 *	包        名：	com.wzitech.iboxpayment.framework.exception
 *	项目名称：	iboxpayment-framework-exception 
 *	作        者： 	Shawn
 *	创建时间：	2011-10-17
 *	描        述：
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.exception;

/**
 * @author Shawn
 *
 */
public enum PostHandlingAction {
	/**
	 * 处理异常后不做任何后续处理
	 */
	None,
	
	/**
	 * 重新抛出异常
	 */
	NotifyRethrow,
	
	/**
	 * 抛出新异常
	 */
	ThrowNewException
}
