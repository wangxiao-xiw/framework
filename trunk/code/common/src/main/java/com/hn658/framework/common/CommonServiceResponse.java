/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		CommonDTO
 *	包	名：		com.wzitech.chaos.framework.server.common
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-7 上午12:41:10
 *	描	述：		通用DTO
 *	更新纪录：	
 * 				
 ************************************************************************************/
package com.hn658.framework.common;

/**
 * 通用DTO类
 * 用于只有ResponseStatus返回的情况
 * @author Shawn
 *
 */
public class CommonServiceResponse extends AbstractServiceResponse {

	/**
	 * @param code		返回状态码
	 * @param message	返回信息
	 */
	public CommonServiceResponse(String code, String message) {
		this.responseStatus = new ResponseStatus(code, message);
	}
}
