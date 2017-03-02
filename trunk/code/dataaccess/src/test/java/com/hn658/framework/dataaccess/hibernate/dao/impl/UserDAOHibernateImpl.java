/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		UserDAOImpl
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.hibernate.dao.impl
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-5-14
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-5-14 下午6:17:33
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.hibernate.dao.impl;

import org.springframework.stereotype.Repository;

import com.hn658.framework.dataaccess.hibernate.AbstractHibernateBaseDAO;
import com.hn658.framework.dataaccess.hibernate.dao.IUserHibernateDAO;
import com.hn658.framework.dataaccess.hibernate.entity.UserHibernateEO;

/**
 * @author Shawn
 *
 */
@Repository("userDAOHibernateImpl")
public class UserDAOHibernateImpl extends AbstractHibernateBaseDAO<UserHibernateEO, Long> implements IUserHibernateDAO {

}
