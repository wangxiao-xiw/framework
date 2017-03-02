/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		IUserDAO
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.hibernate.dao
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-5-14
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-5-14 下午6:17:19
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.hibernate.dao;

import com.hn658.framework.dataaccess.hibernate.IHibernateBaseDAO;
import com.hn658.framework.dataaccess.hibernate.entity.UserHibernateEO;

/**
 * @author Shawn
 *
 */
public interface IUserHibernateDAO extends IHibernateBaseDAO<UserHibernateEO, Long> {

}
