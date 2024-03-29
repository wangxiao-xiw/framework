/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		DerbyDialect
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.dialect
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		Derby分页方言
 *	更新纪录：	1. Shawn 创建于 2012-4-20 上午1:56:20
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.dialect;

/**
 * Derby分页方言
 * @author Shawn
 *
 */
public class DerbyDialect extends AbstractBaseDialect {

	@Override
	public boolean supportsLimit() {
		return false;
	}

	@Override
	public boolean supportsLimitOffset() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.dialect.AbstractBaseDialect#getLimitString(java.lang.String, int, java.lang.String, int, java.lang.String)
	 */
	@Override
	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		throw new UnsupportedOperationException( "paged queries not supported" );
	}

}
