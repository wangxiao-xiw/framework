/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		UserEO
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.hibernate.entity
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-5-14
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-5-14 下午5:53:51
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.hibernate.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.hn658.framework.shared.entity.BaseEntity;

/**
 * @author Shawn
 *
 */
@Entity
@Table(name = "TBL_EGM_USER_TEST")
public class UserHibernateEO extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String loginName;

	private String realName;
	
	private String email;
	
	private String tel;
	
	private Timestamp createdDateTime;
	
	private boolean isDeleted;
	
	@Override
	@Id
	@Column(name = "ID")
	@Type(type = "long")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}
	
	/**
	 * @return the loginName
	 */
	@Column(name = "LOGIN_NAME", length=128)
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the realName
	 */
	@Column(name = "REAL_NAME", length=128)
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the email
	 */
	@Column(name = "EMAIL", length=128)
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the tel
	 */
	@Column(name = "TEL", length=128)
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the createdDateTime
	 */
	@Column(name = "CRTED_DATETIME")
	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * @param createdDateTime the createdDateTime to set
	 */
	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * @return the isDeleted
	 */
	@Column(name = "IS_DELETED")
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
