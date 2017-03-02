/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		RuntimeConstants
 *	包	名：		com.wzitech.chaos.framework.common
 *	项目名称：	chaos-common 
 *	作	者：		Shawn
 *	创建时间：	2012-4-13
 *	描	述：		系统运行时常量定义类
 *	更新纪录：	1. Shawn 创建于 2012-4-13 下午4:15:17
 * 				
 ************************************************************************************/
package com.hn658.framework.shared.constants;

/**
 * 系统运行时常量定义类
 * @author Shawn
 *
 */
public class CommonConstants {
	/**
	 * 业务异常错误码前缀
	 */
	public static final String BUSSINESS_EXCEPTION_PREFIX = "B";

	/**
	 * 系统异常错误码前缀
	 */
	public static final String SYSTEM_EXCEPTION_PREFIX = "S";
	
	/**
	 * HTTP请求前缀
	 */
	public static final String HTTP_PREFIX = "http://";
	
	/**
	 * HTTP请求间隔号
	 */
	public static final String HTTP_INTERVAL = "/";
    
    /**
     * json头定义
     */
    public static final String JSON_CONTENT_TYPE = "application/json; charset=UTF-8";
    
	/**
	 * cookie中的session key
	 */
	public static final String JSESSIONID = "JSESSIONID";
    
    /** 
     * 校验token参数名 
     */
	public static final String ACCESS_TOKEN = "access_token";

	/**
     * 请求用户IP
     */
    public static final String SERVICE_REQUEST_HEADER_REAL_IP = "X-real-Ip";
    
    /**
	 * 用户ID-请求认证HTTP HEAD标签
	 */
	public static final String SERVICE_REQUEST_HEADER_APPID = "appId";
    
    /**
	 * 用户ID-请求认证HTTP HEAD标签
	 */
	public static final String SERVICE_REQUEST_HEADER_UID = "uid";
	
	/**
	 * 用户authkey-请求认证HTTP HEAD标签
	 */
	public static final String SERVICE_REQUEST_HEADER_AUTHKEY = "authkey";
	
	/**
	 * 通过子系统编号获取系统错误码
	 *
	 * @param subSystemCode 系统号
	 * @param errorCode 四位错误码
	 * @return 返回由“系统异常标识”+“子系统编号”+“错误码”组成的错误码
	 */
	public static final String getSystemErrorCode(String subSystemCode, String moduleCode, String errorCode) {
		return SYSTEM_EXCEPTION_PREFIX + subSystemCode + moduleCode + errorCode;
	}

	/**
	 * 通过子系统编号获取业务错误码
	 *
	 * @param subSystemCode 系统号
	 * @param errorCode 四位错误码
	 * @return 返回由“业务异常标识”+“子系统编号”+“错误码”组成的错误码
	 */
	public static final String getBusinessErrorCode(String subSystemCode, String moduleCode, String errorCode) {
		return BUSSINESS_EXCEPTION_PREFIX + subSystemCode + moduleCode + errorCode;
	}
}
