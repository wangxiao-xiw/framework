/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		DB2Dialect
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.dialect
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		DB2分页方言
 *	更新纪录：	1. Shawn 创建于 2012-4-20 上午1:52:59
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.dialect;

/**
 * DB2分页方言
 * @author Shawn
 *
 */
public class DB2Dialect extends AbstractBaseDialect {

	@Override
	public boolean supportsLimit() {
		return true;
	}
	
	@Override
	public boolean supportsLimitOffset(){
		return true;
	}
	
	private static String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer(50)
			.append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if ( orderByIndex>0 && !hasDistinct(sql) ) {
			rownumber.append( sql.substring(orderByIndex) );
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}
	
	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct")>=0;
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.dialect.AbstractBaseDialect#getLimitString(java.lang.String, int, java.lang.String, int, java.lang.String)
	 */
	@Override
	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		int startOfSelect = sql.toLowerCase().indexOf("select");

		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 )
					.append( sql.substring(0, startOfSelect) ) //add the comment
					.append("select * from ( select ") //nest the main query in an outer select
					.append( getRowNumber(sql) ); //add the rownnumber bit into the outer query select list

		if ( hasDistinct(sql) ) {
			pagingSelect.append(" row_.* from ( ") //add another (inner) nested select
				.append( sql.substring(startOfSelect) ) //add the main query
				.append(" ) as row_"); //close off the inner nested select
		}
		else {
			pagingSelect.append( sql.substring( startOfSelect + 6 ) ); //add the main query
		}

		pagingSelect.append(" ) as temp_ where rownumber_ ");

		//add the restriction to the outer select
		if (offset > 0) {
//			int end = offset + limit;
			String endString = offsetPlaceholder+"+"+limitPlaceholder;
			pagingSelect.append("between "+offsetPlaceholder+"+1 and "+endString);
		}
		else {
			pagingSelect.append("<= "+limitPlaceholder);
		}

		return pagingSelect.toString();
	}

}
