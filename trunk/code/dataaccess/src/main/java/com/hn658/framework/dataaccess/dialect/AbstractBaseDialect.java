/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		BaseDialect
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.dialect
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		用于分页的Dialect抽象基类
 *	更新纪录：	1. Shawn 创建于 2012-4-20 上午1:38:30
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.dialect;

/**
 * 用于分页的Dialect抽象基类
 * @author Shawn
 *
 */
public abstract class AbstractBaseDialect {

	public boolean supportsLimit(){
    	return false;
    }

    public boolean supportsLimitOffset() {
    	return supportsLimit();
    }
    
    /**
     * 将sql变成分页sql语句,直接使用offset,limit的值作为占位符.
     * @param sql sql语句
     * @param offset 分页数据起始offset
     * @param limit 分页大小
     * @return 分页sql
     */
    public String getLimitString(String sql, int offset, int limit) {
    	return getLimitString(sql,offset,Integer.toString(offset),limit,Integer.toString(limit));
    }
    
    /**
     *  将sql变成分页sql语句,提供将offset及limit使用占位符(placeholder)替换.
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
     * select * from user limit :offset,:limit
     * </pre>
     * @param sql sql语句
     * @param offset 分页数据起始offset
     * @param offsetPlaceholder sql语句中offset占位符
     * @param limit 分页大小
     * @param limitPlaceholder sql语句中limit占位符
     * @return 包含占位符的分页sql
     */
    public abstract String getLimitString(String sql, int offset,String offsetPlaceholder, int limit,String limitPlaceholder);
}
