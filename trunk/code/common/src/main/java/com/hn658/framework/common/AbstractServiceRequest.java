/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		AbstractServiceRequest
 *	包	名：		com.wzitech.chaos.framework.server.common
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-7 上午2:02:03
 *	描	述：		抽象服务请求信息基类
 *	更新纪录：	
 * 				
 ************************************************************************************/
package com.hn658.framework.common;

/**
 * 抽象服务请求信息基类
 * @author Shawn
 *
 */
public abstract class AbstractServiceRequest extends AbstractBaseDTO implements IServiceRequest {
	/**
	 * 设备Id号
	 */
	private String deviceId;

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}
