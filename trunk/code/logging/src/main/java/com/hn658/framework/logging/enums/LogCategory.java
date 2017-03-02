/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	LogCategory
 *	包        名：	com.wzitech.iboxpayment.framework.logging
 *	项目名称：	iboxpayment-framework-logging 
 *	作        者： 	Shawn
 *	创建时间：	2011-11-21
 *	描        述：
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.logging.enums;

/**
 * 日志分类
 * @author ztjie
 *
 */
public interface LogCategory {
	/**
	 * 应用
	 */
	public static final String APPLICATION = "APPLICATION";
	/**
	 * 定时任务
	 */
	public static final String JOB = "JOB";
	/**
	 * 接口调用
	 */
	public static final String INTERFACE = "INTERFACE";
	/**
	 * 搜索系统
	 */
	public static final String SEARCH = "SEARCH";
	/**
	 * 订单系统
	 */
	public static final String ORDER = "ORDER";
	/**
	 * 支付系统
	 */
	public static final String PAY = "PAY";
	/**
	 * 资金系统
	 */
	public static final String FUND = "FUND";
	/**
	 * 投诉系统
	 */
	public static final String COMPLAINT="COMPLAINT";
	/**
	 * 用户管理
	 */
	public static final String USER = "USER";
	
	/**
	 * 系统日志，非业务，功能组件调试用，一般打印到控制台或者文件
	 */
	public static final String SYSTEM = "SYSTEM";
}
