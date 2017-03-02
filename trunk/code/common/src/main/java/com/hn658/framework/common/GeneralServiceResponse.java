/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		GeneralServiceResponse
 *	包	名：		com.wzitech.chaos.framework.server.common
 *	项目名称：	chaos-common
 *	作	者：		dev
 *	创建时间：	2013-3-21
 *	描	述：		
 *	更新纪录：	1. dev 创建于 2013-3-21 下午5:21:06
 * 				
 ************************************************************************************/
package com.hn658.framework.common;

import java.util.Map;

/**
 * @author dev
 *
 */
public class GeneralServiceResponse {
	/**
	 * 响应码
	 */
	private String code;
	
	/**
	 * 响应信息
	 */
	private String message;
	
	/**
	 * 响应数据
	 */
	private Map<String, Object> data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public void setStatus(String code, String message){
		this.code = code;
		this.message = message;
	}
}
