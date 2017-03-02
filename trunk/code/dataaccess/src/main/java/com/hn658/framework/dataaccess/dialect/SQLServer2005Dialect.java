/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		SQLServer2005Dialect
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.dialect
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		SQLServer2005分页方言
 *	更新纪录：	1. Shawn 创建于 2012-4-20 上午2:05:11
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.dialect;

/**
 * SQLServer2005分页方言
 * @author Shawn
 *
 */
public class SQLServer2005Dialect extends AbstractBaseDialect {

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}
	
	/* 
	 * Add a LIMIT clause to the given SQL SELECT
	 *
	 * The LIMIT SQL will look like:
	 *
	 * WITH query AS
	 *      (SELECT TOP 100 percent ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) as __row_number__, * from table_name)
	 * SELECT *
	 * FROM query
	 * WHERE __row_number__ BETWEEN :offset and :lastRows
	 * ORDER BY __row_number__
	 * 
	 * @see com.wzitech.chaos.framework.server.dataaccess.dialect.AbstractBaseDialect#getLimitString(java.lang.String, int, java.lang.String, int, java.lang.String)
	 */
	@Override
	public String getLimitString(String querySqlString, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		StringBuffer pagingBuilder = new StringBuffer();
		String orderby = getOrderByPart(querySqlString);
		String distinctStr = "";

		String loweredString = querySqlString.toLowerCase();
		String sqlPartString = querySqlString;
		if (loweredString.trim().startsWith("select")) {
			int index = 6;
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15;
			}
			sqlPartString = sqlPartString.substring(index);
		}
		pagingBuilder.append(sqlPartString);

		// if no ORDER BY is specified use fake ORDER BY field to avoid errors
		if (orderby == null || orderby.length() == 0) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		StringBuffer result = new StringBuffer();
		result.append("WITH query AS (SELECT ")
				.append(distinctStr)
				.append("TOP 100 PERCENT ")
				.append(" ROW_NUMBER() OVER (")
				.append(orderby)
				.append(") as __row_number__, ")
				.append(pagingBuilder)
				.append(") SELECT * FROM query WHERE __row_number__ BETWEEN ")
				.append(offset).append(" AND ").append(offset+limit)
				.append(" ORDER BY __row_number__");

		return result.toString();
	}

	static String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		if (orderByIndex != -1) {
			// if we find a new "order by" then we need to ignore
			// the previous one since it was probably used for a subquery
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
	}

}
