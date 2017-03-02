/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	OperationLogEO
 *	包        名：	com.wzitech.iboxpayment.framework.logging.model
 *	项目名称：	iboxpayment-framework-logging 
 *	作        者： 	Shawn
 *	创建时间：	2011-10-17
 *	描        述：
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.logging.model;

import java.sql.Timestamp;

import com.hn658.framework.shared.entity.BaseEntity;

/**
 * @author Shawn
 *
 */
public class LogInfoEO extends BaseEntity{
	
	private static final long serialVersionUID = 70205098421648636L;
	
	/**
     * requestId 起日志信息关联作用
     */
    private String requestId;
	/**
	 * 日志类型
	 */
	private String logType;
	/**
	 * 日志分类
	 */
	private String category;
	/**
	 * 日志级别
	 */
	private String level;
	/**
	 * 日志信息
	 */
	private String message;
	/**
	 * 创建时间
	 */
	private Timestamp createdDateTime;
	
	public LogInfoEO(){
		
	}
	
	public LogInfoEO(String requestId, String type, String category, String level,
			String source, String relatedMsgId, String requestIP, String requestURL,
			String serverNode, String operator, String message, String createdOperator, Timestamp createdDateTime){
		this.requestId = requestId;
		this.logType = type;
		this.category = category;
		this.level = level;
		this.message = message;
		this.createdDateTime = createdDateTime;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the operationType
	 */
	public String getLogType() {
		return logType;
	}
	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return the createdDateTime
	 */
	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}
	
	/**
	 * @param operationType the operationType to set
	 */
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @param createdDateTime the createdDateTime to set
	 */
	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
}
