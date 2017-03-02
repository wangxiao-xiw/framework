/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		OracleDialect
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.dialect
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		Oracle分页方言
 *	更新纪录：	1. Shawn 创建于 2012-4-20 上午2:03:25
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.dialect;

/**
 * Oracle分页方言
 * @author Shawn
 *
 */
public class OracleDialect extends AbstractBaseDialect {

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.dialect.AbstractBaseDialect#getLimitString(java.lang.String, int, java.lang.String, int, java.lang.String)
	 */
	@Override
	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if ( sql.toLowerCase().endsWith(" for update") ) {
			sql = sql.substring( 0, sql.length()-11 );
			isForUpdate = true;
		}
		
		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );
		if (offset > 0) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		}
		else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (offset > 0) {
//			int end = offset+limit;
			String endString = offsetPlaceholder+"+"+limitPlaceholder;
			pagingSelect.append(" ) row_ ) where rownum_ <= " + endString + " and rownum_ > " + offsetPlaceholder);
		}
		else {
			pagingSelect.append(" ) where rownum <= " + limitPlaceholder);
		}

		if ( isForUpdate ) {
			pagingSelect.append( " for update" );
		}
		
		return pagingSelect.toString();
	}

}
