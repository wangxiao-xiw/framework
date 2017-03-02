/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		AbstractBaseDTO
 *	包	名：		com.wzitech.chaos.framework.server.common
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-7 上午2:05:08
 *	描	述：		抽象DTO基类
 *	更新纪录：	
 * 				
 ************************************************************************************/
package com.hn658.framework.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hn658.framework.logging.Logger;
import com.hn658.framework.logging.LoggerFactory;

/**
 * 抽象DTO基类
 * @author Shawn
 *
 */
// 解决Spring Data Redis使用Jason1.X，与Jason2.X不兼容的问题
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractBaseDTO {
	/**
	 * 日志记录器
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Json序列化器
	 */
	protected ObjectMapper jsonMapper;
	
	/**
	 * 重载Entity类的toString()方法
	 * 返回Entity类所有属性值
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
