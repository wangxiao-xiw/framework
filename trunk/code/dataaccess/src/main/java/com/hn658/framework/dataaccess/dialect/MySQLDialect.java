/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		MySQLDialect
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.dialect
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		MySQL分页方言
 *	更新纪录：	1. Shawn 创建于 2012-4-20 上午2:00:34
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.dialect;

/**
 * MySQL分页方言
 * 
 * @author Shawn
 * 
 */
public class MySQLDialect extends AbstractBaseDialect {

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}

	@Override
	public boolean supportsLimit() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wzitech.chaos.framework.server.dataaccess.dialect.AbstractBaseDialect
	 * #getLimitString(java.lang.String, int, java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		return new StringBuffer(sql.length() + 20)
				.append(sql)
				.append(offset > 0 ? " limit " + offsetPlaceholder + ","
						+ limitPlaceholder : " limit " + limitPlaceholder)
				.toString();
	}
}
