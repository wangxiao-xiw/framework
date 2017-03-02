/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		ChaosException
 *	包	名：		com.wzitech.chaos.framework.common.exception
 *	项目名称：	chaos-common 
 *	作	者：		Shawn
 *	创建时间：	2012-4-13
 *	描	述：		Chaos 框架异常基类（Checked Exception）
 *	更新纪录：	1. Shawn 创建于 2012-4-13 下午3:45:05
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;

import com.hn658.framework.shared.constants.CommonConstants;
import com.hn658.framework.shared.enums.ErrorCodeType;

/**
 * 框架异常基类（UnChecked Exception）
 * @author Shawn
 *
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	private String errorCode;

	/**
	 * 异常信息
	 */
	private String errorMsg;
	
	/**
	 * 错误代码类型 :<br/>
	 * E001：内部错误<br/>
	 * E002：外部错误
	 */
	private ErrorCodeType errorCodeType;
	
	/**
	 * 业务信息参数
	 */
	private String[]  args ;
	
	/**
	 * 内部异常
	 */
	private Throwable t;
	
	/**
	 * 堆栈信息
	 */
	private String exceptionStackMsg;
	
	/**
	 * 使用错误码及业务参数构建BaseException
	 * @param errorCode 错误码
	 * @param args 业务参数
	 */
	public BaseException(String errorCode, String... args) {
		super(errorCode);
		this.errorCode = errorCode;
		this.args = args;
	}
	
	/**
	 * 使用错误码及内置异常构建BaseException
	 * @param errorCode
	 * @param errorMsg
	 * @param args
	 */
	public BaseException(String errorCode, String errorMsg, String... args) {
		super(errorCode);
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
		this.args = args;
	}

	/**
	 * 使用错误码及内置异常构建BaseException
	 * @param errorCode 错误码
	 * @param t 内置异常
	 */
	public BaseException(String errorCode, Throwable t) {
		super(t);
		this.t = t;
		this.errorCode=errorCode;
	}
	
	/**
	 * 获取异常堆栈信息
	 * @return 返回异常堆栈信息
	 */
	public String getExceptionStackMsg() {
		if(this.t!=null){
			return this.t.getStackTrace().toString();
		}
		if(exceptionStackMsg != null){
			return exceptionStackMsg;
		}
		return null;
	}
	
	/**
	 * 判断是否为系统异常
	 * @return 如果是系统异常返回true，反之返回false
	 */
	public boolean isSystemException() {
    	if (StringUtils.isNotBlank(errorCode)) {
    		if (errorCode.startsWith(CommonConstants.SYSTEM_EXCEPTION_PREFIX)) {
    			return true;
    		}
    	}
    	return false;
    }
    
	/**
	 * 判断是否为业务异常
	 * @return 如果是业务异常返回true，反之返回false
	 */
    public boolean isBusinessException() {
    	if (StringUtils.isNotBlank(errorCode)) {
    		if (errorCode.startsWith(CommonConstants.BUSSINESS_EXCEPTION_PREFIX)) {
    			return true;
    		}
    	}
    	return false;
    }
    
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public ErrorCodeType getErrorCodeType() {
		return errorCodeType;
	}

	public void setErrorCodeType(ErrorCodeType errorCodeType) {
		this.errorCodeType = errorCodeType;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public Throwable getT() {
		return t;
	}

	public void setT(Throwable t) {
		this.t = t;
	}

	public void setExceptionStackMsg(String exceptionStackMsg) {
		this.exceptionStackMsg = exceptionStackMsg;
	}
	
	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
}
