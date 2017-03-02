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
package com.hn658.framework.file.exception;

import com.hn658.framework.shared.enums.CommonResponseCodes;
import com.hn658.framework.shared.exception.SystemException;


/**
 * 文件处理系统异常
 * @author ztjie
 *
 */
public class FileException extends SystemException {
	
	private static final long serialVersionUID = -8569167241632276818L;
	
	public static final String ERROR_CODE = CommonResponseCodes.FileDealError.getCode();

	/**
	 * 通过错误码及业务信息构造SystemException
	 * @param errorCode
	 * @param args
	 */
	public FileException(String errorCode, String... args) {
		super(errorCode, args);
	}

	/**
	 * 通过错误码及错误异常构造SystemException
	 * @param errorCode
	 * @param t
	 */
	public FileException(String errorCode, Throwable t) {
		super(errorCode, t);
	}
    
}
