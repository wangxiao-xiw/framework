/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		AbstractBusinessObject
 *	包	名：		com.wzitech.chaos.framework.server.common
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-7
 *	描	述：		业务逻辑抽象基类
 *	更新纪录：	1. Shawn 创建于 2012-7-7 上午4:03:04
 * 				
 ************************************************************************************/
package com.hn658.framework.common;

import com.hn658.framework.logging.Logger;
import com.hn658.framework.logging.LoggerFactory;

/**
 * 业务逻辑抽象基类
 * @author Shawn
 *
 */
public abstract class AbstractBusinessObject {
	/**
	 * 日志记录器
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
