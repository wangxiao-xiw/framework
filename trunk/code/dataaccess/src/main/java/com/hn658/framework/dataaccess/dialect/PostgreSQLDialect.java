/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		PostgreSQLDialect
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.dialect
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		PostgreSQL分页方言
 *	更新纪录：	1. Shawn 创建于 2012-4-20 上午1:45:16
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.dialect;

/**
 * PostgreSQL分页方言
 * @author Shawn
 * 
 */
public class PostgreSQLDialect extends AbstractBaseDialect {

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public boolean supportsLimitOffset() {
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
				.append(offset > 0 ? " limit " + limitPlaceholder + " offset "
						+ offsetPlaceholder : " limit " + limitPlaceholder)
				.toString();
	}

}
