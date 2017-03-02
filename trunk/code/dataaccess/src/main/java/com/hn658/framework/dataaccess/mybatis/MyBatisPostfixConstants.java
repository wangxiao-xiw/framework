/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		MyBatisPostfixConstants
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.mybatis
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-20
 *	描	述：		MyBatis后缀名常量
 *	更新纪录：	1. Shawn 创建于 2012-4-20 下午2:01:13
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.mybatis;

/**
 * MyBatis后缀名常量
 * @author Shawn
 *
 */
public class MyBatisPostfixConstants {
	/**
	 * 查询记录总条数
	 */
	public static final String COUNT = ".count";
	
	/**
	 * 根据条件查询结果集数量
	 */
	public static final String COUNT_BY_MAP = ".countByMap";
	
	/**
	 * 根据条件查询结果集数量
	 */
	public static final String COUNT_LIKE_BY_MAP = ".countLikeByMap";
	
	/**
	 * 新增记录
	 */
	public static final String INSERT = ".insert";
	
	/**
	 * 批量新增记录
	 */
	public static final String BATCH_INSERT = ".batchInsert";
	
	/**
	 * 通过自定义Mapper插入记录
	 */
	public static final String INSERT_BY_STATEMENT = ".insertByStatement";
	
	/**
	 * 更新记录
	 */
	public static final String UPDATE = ".update";
		
	/**
	 * 批量更新记录
	 */
	public static final String BATCH_UPDATE = ".batchUpdate";
	
	/**
	 * 通过主键更新记录
	 */
	public static final String UPDATE_BY_ID = ".updateById";
	
	/**
	 * 批量通过主键更新记录
	 */
	public static final String BATCH_UPDATE_BY_IDS = ".batchUpdateByIds";
	
	/**
	 * 通过自定义Mapper更新记录
	 */
	public static final String UPDATE_BY_STATEMENT = ".updateByStatement";
	
	/**
	 * 删除记录
	 */
	public static final String DELETE = ".delete";  
	
	/**
	 * 通过主键ID删除记录
	 */
    public static final String DELETE_BY_ID = ".deleteById";  
	
    /**
	 * 通过主键批量删除记录
	 */
    public static final String BATCH_DELETE_BY_IDS = ".batchDeleteByIds";  
    
    /**
     * 删除符合条件的记录
     */
    public static final String DELETE_BY_MAP = ".deleteByMap";
    
    /**
     * 通过自定义Mapper删除记录
     */
    public static final String DELETE_BY_STATEMENT = ".deleteByStatement";
    
    /**
     * 通过主键查找记录
     */
    public static final String SELECT_BY_ID = ".selectById";
    
    /**
     * 通过主键列表查询记录
     */
    public static final String SELECT_BY_IDS = ".selectByIds";
    
    /**
     * 查询符合条件的唯一记录
     */
    public static final String SELECT_UNIQUE_BY_PROP = ".selectUniqueByProp";
    
    /**
     * 查询符合条件的实体对象
     */
    public static final String SELECT_BY_MAP = ".selectByMap";
    
    /**
     * 查询符合条件的记录集
     */
    public static final String SELECT_BY_PROP = ".selectByProp";
    
    /**
     * 获取全部记录
     */
    public static final String SELECT_ALL = ".selectAll";
    
    /**
     * 查询符合条件的记录主键
     */
    public static final String SELECT_IDS = ".selectIds";
    
    /**
     * 通过自定义Mapper查询记录
     */
    public static final String SELECT_BY_STATEMENT = ".selectByStatement";
    
    /**
     * 根据原生Sql查询记录
     */
    public static final String SELECT_BY_NATIVE_SQL = ".selectByNativeSql";
}
