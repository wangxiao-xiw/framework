/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		CommonResponseCode
 *	包	名：		com.wzitech.chaos.framework.server.common.enums
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-5 下午11:51:51
 *	描	述：		通用信息状态码枚举
 *	更新纪录：	
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 通用信息状态码枚举类
 * @author Shawn
 *
 */
public enum CommonResponseCodes {
	Success("00"),
	Error("01"),
	UserNotLogin("11"),
	AccessNotAllow("12"),
	UnKnownError("X00"),
	AccessItfError("X01"),
	FileDealError("X02");
	
	private String code;
	
	CommonResponseCodes(String code) {
		this.code = code;
	}
	
	/**
	 * 通过code获取对应的CommonResponseCodes
	 * @param code 错误码
	 * @return 响应码对应的CommonResponseCodes枚举
	 */
	public static CommonResponseCodes getResponseByCode(String code){
		if(StringUtils.isEmpty(code)){
			throw new NullPointerException("响应编码为空");
		}
		
		for(CommonResponseCodes responseCode : CommonResponseCodes.values()){
			if(responseCode.getCode().equals(code)){
				return responseCode;
			}
		}
		
		throw new IllegalArgumentException("未能找到匹配的CommonResponseCode:" + code);
	}
	
	/**
	 * 获取响应编码
	 * @return
	 */
	public String getCode(){
		return this.code;
	}
}
