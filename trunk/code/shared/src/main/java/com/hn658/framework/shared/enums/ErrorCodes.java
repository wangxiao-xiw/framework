/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		ErrorCodes
 *	包	名：		com.wzitech.chaos.framework.common.enums
 *	项目名称：	chaos-common 
 *	作	者：		Shawn
 *	创建时间：	2012-4-13
 *	描	述：		错误码枚举类
 *	更新纪录：	1. Shawn 创建于 2012-4-13 下午4:56:24
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.enums;

import org.apache.commons.lang3.StringUtils;

import com.hn658.framework.shared.constants.CommonConstants;

/**
 * 错误码枚举类
 * @author Shawn
 *
 */
public enum ErrorCodes {
    UnknownError(CommonConstants.SYSTEM_EXCEPTION_PREFIX + "0001", "未知错误类型"),;
	
	private String code;
	private String message;
	
	ErrorCodes(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * 通过code获取对应的ErrorCodes
	 * @param code 错误码
	 * @return 错误码对应的ErrorCodes枚举
	 */
	public static ErrorCodes getResponseByCode(String code){
		if(StringUtils.isEmpty(code)){
			throw new NullPointerException("错误编码为空");
		}
		
		for(ErrorCodes responseCode : ErrorCodes.values()){
			if(responseCode.getCode().equals(code)){
				return responseCode;
			}
		}
		
		throw new IllegalArgumentException("未能找到匹配的ErrorCode:" + code);
	}
	
	/**
	 * 获取错误编码
	 * @return
	 */
	public String getCode(){
		return this.code;
	}
	
	/**
	 * 获取错误编码对应消息
	 * @return
	 */
	public String getMessage(){
		return this.message;
	}
}
