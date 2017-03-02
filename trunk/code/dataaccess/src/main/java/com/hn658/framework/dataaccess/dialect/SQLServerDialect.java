/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		SQLServerDialect
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.dialect
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		SQLServer分页方言
 *	更新纪录：	1. Shawn 创建于 2012-4-20 上午2:07:55
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.dialect;

/**
 * SQLServer分页方言
 * 
 * @author Shawn
 * 
 */
public class SQLServerDialect extends AbstractBaseDialect {

	@Override
	public boolean supportsLimitOffset() {
		return false;
	}

	@Override
	public boolean supportsLimit() {
		return true;
	}

	static int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		final int selectDistinctIndex = sql.toLowerCase().indexOf(
				"select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(sql, offset, null, limit, null);
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
	public String getLimitString(String querySelect, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		if (offset > 0) {
			throw new UnsupportedOperationException("sql server has no offset");
		}

		return new StringBuffer(querySelect.length() + 8)
				.append(querySelect)
				.insert(getAfterSelectInsertPoint(querySelect), " top " + limit)
				.toString();
	}

}
