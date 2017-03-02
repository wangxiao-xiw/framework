/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		UserMyBatisDAOImpl
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.mybatis.dao.impl
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-5-14
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-5-14 下午6:18:51
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.mybatis.dao.impl;

import org.springframework.stereotype.Repository;

import com.hn658.framework.dataaccess.mybatis.AbstractMyBatisDAO;
import com.hn658.framework.dataaccess.mybatis.dao.IUserMyBatisDAO;
import com.hn658.framework.dataaccess.mybatis.entity.UserMyBatisEO;

/**
 * @author Shawn
 *
 */
@Repository("userMyBatisDAOImpl")
public class UserMyBatisDAOImpl extends AbstractMyBatisDAO<UserMyBatisEO, Long> implements IUserMyBatisDAO {

}
