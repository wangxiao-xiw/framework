/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		ErrorCodeType
 *	包	名：		com.wzitech.chaos.framework.common.enums
 *	项目名称：	chaos-common 
 *	作	者：		Shawn
 *	创建时间：	2012-4-13
 *	描	述：		错误编码类型
 *	更新纪录：	1. Shawn 创建于 2012-4-13 下午4:28:22
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.enums;

import org.apache.commons.lang3.StringUtils;

import com.hn658.framework.shared.constants.CommonConstants;

/**
 * 错误编码类型
 * @author Shawn
 *
 */
public enum ErrorCodeType {
    InternalError(CommonConstants.SYSTEM_EXCEPTION_PREFIX + "E001", "内部错误"),
    ExternalError(CommonConstants.SYSTEM_EXCEPTION_PREFIX + "E002", "外部错误"),
    IllegalArgument(CommonConstants.SYSTEM_EXCEPTION_PREFIX + "0102", "向服务器传递了不合法或不正确的参数"),;
	
	/**
	 * 错误类型编码
	 */
	private String code;
	
	/**
	 * 错误类型编码描述
	 */
	private String description;
	
	ErrorCodeType(String code, String description){
		this.code = code;
		this.description = description;
	}
	
	/**
	 * 通过code获取对应的ErrorCodeType
	 * @param code 错误码类型编码
	 * @return 错误码类型编码对应的ErrorCodeType枚举
	 */
	public static ErrorCodeType getErrorCodeTypeByCode(String code){
		if(StringUtils.isEmpty(code)){
			throw new NullPointerException("错误类型编码为空");
		}
		
		for(ErrorCodeType errorCodeType : ErrorCodeType.values()){
			if(errorCodeType.getCode().equals(code)){
				return errorCodeType;
			}
		}
		
		throw new IllegalArgumentException("未能找到匹配的ErrorCodeType:" + code);
	}
	
	/**
	 * 获取错误类型编码
	 * @return 错误类型编码
	 */
	public String getCode(){
		return this.code;
	}
	
	/**
	 * 获取错误类型编码描述
	 * @return 错误类型编码描述
	 */
	public String getDescription(){
		return this.description;
	}
}
