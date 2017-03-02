/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	RemoteCallException
 *	包        名：	com.wzitech.iboxpayment.framework.exception
 *	项目名称：	iboxpayment-framework-exception 
 *	作        者： 	Shawn
 *	创建时间：	2011-10-16
 *	描        述：
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.exception;

import java.rmi.RemoteException;

/**
 * @author Shawn
 * 
 */
public class RemoteCallException extends RemoteException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1901294430262277927L;

	/**
	 * 错误码
	 */
	private String errorCode;

	/**
	 * 业务信息参数
	 */
	private String[] args;

	/**
	 * Throwable
	 */
	private Throwable t;

	/**
	 * 异常信息
	 */
	private String errorMsg;

	/**
	 * 错误代码类型 ER001：内部错误代码，ER002：外部错误代码
	 */
	private String errorType;

	/**
	 * @param errorCode
	 * @param args
	 */
	public RemoteCallException(String errorCode, String... args) {
		super(errorCode);
		this.errorCode = errorCode;
		this.args = args;
	}

	public RemoteCallException(String errorCode, Throwable t) {
		this.t = t;
		this.errorCode = errorCode;
	}

	public String getExceptionMsg() {
		if (this.t != null) {
			return this.t.getStackTrace().toString();
		}
		return null;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String[] getArgs() {
		return args;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
}
