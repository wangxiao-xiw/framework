/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	CryptoException
 *	包        名：	com.wzitech.iboxpayment.framework.security
 *	项目名称：	iboxpayment-framework-security 
 *	作        者： 	Shawn
 *	创建时间：	2011-11-6
 *	描        述：	加密异常类
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.security.exception;

import com.hn658.framework.shared.exception.SystemException;


/**
 * 加密异常类
 * @author Shawn
 *
 */
public class CryptoException extends SystemException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2447084360507743017L;

	public CryptoException(String str, Throwable cause) {
		super(str, cause);
	}
}
