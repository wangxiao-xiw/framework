/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		IMyBatisBaseDAO
 *	包	名：		com.wzitech.chaos.framework.dataaccess.mybatis
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-18
 *	描	述：		MyBatis基础数据操作泛型接口类
 *	更新纪录：	1. Shawn 创建于 2012-4-18 下午1:07:31
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.mybatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hn658.framework.dataaccess.IBaseDAO;
import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.entity.BaseEntity;

/**
 * MyBatis基础数据操作泛型接口类
 * @author Shawn
 *
 */
public interface IMyBatisBaseDAO<T extends BaseEntity, PK extends Serializable> extends IBaseDAO<T, PK> {

	/**
	 * 根据条件查询结果集数量
	 * @param param 包含propertyName及对应propertyValue的Map集合
	 * @return 符合查询条件的记录数
	 */
	int countByMap(Map<String, Object> queryParam);
	
	/**
	 * 根据条件查询结果集数量
	 * @param param 包含propertyName及对应propertyValue的Map集合
	 * @return 符合查询条件的记录数
	 */
	int countLikeByMap(Map<String, Object> queryParam);
	
	/**
	 * 通过自定义Mapper插入记录
	 * @param statementPostfix 自定义Mapper后缀名
	 * @param insertParam 包含propertyName及对应propertyValue的Map集合
	 * @return 影响的记录数，正常应返回1
	 */
	int insertByStatement(String statementPostfix, Map<String, Object> insertParam);
	
	/**
	 * 通过主键更新记录
	 * @param id 主键ID
	 * @param updateParam 包含propertyName及对应propertyValue的Map集合
	 * @return 影响的记录数，正常应返回1
	 */
	int updateById(PK id, Map<String, Object> updateParam);

	/**
	 * 批量通过主键更新记录
	 * @param ids 需要更新的主键ID列表
	 * @param updateParam 包含propertyName及对应propertyValue的Map集合
	 * @return 影响的记录数，正常应等于传入列表参数长度
	 */
	int batchUpdateByIds(List<PK> ids, Map<String, Object> updateParam);
	
	/**
	 * 通过自定义Mapper更新记录
	 * @param statementPostfix 自定义Mapper后缀名
	 * @param queryParam 包含propertyName及对应propertyValue的Map集合
	 * @return 影响的记录数
	 */
	int updateByStatement(String statementPostfix, Map<String, Object> queryParam);

	/**
	 * 批量通过主键删除记录
	 * @param ids 需要删除的主键ID列表
	 * @return 影响的记录数，正常应等于传入列表参数长度
	 */
	int batchDeleteByIds(List<PK> ids);
	
	/**
	 * 删除符合条件的记录
	 * @param deleteParam 包含propertyName及对应propertyValue的Map集合
	 * @return 影响的记录数
	 */
	int deleteByMap(Map<String, Object> deleteParam);
	
	/**
	 * 通过自定义Mapper删除记录
	 * @param statementPostfix 自定义Mapper后缀名
	 * @param queryParam 包含propertyName及对应propertyValue的Map集合
	 * @return 影响的记录数
	 */
	int deleteByStatement(String statementPostfix, Map<String, Object> deleteParam);
	
	/**
	 * 通过主键列表查询记录
	 * @param ids 主键ID列表
	 * @return 对应查询主键列表的实体对象
	 */
	List<T> selectByIds(List<PK> ids);
	
	/**
	 * 查询符合条件的实体对象
	 * @param queryParam 包含查询条件的propertyName及对应propertyValue的Map集合
	 * @return 返回符合查询条件的实体对象
	 */
	List<T> selectByMap(Map<String, Object> queryParam);
	
	/**
	 * 查询符合条件的实体对象
	 * @param queryParam 包含查询条件的propertyName及对应propertyValue的Map集合
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @return 返回符合查询条件的实体对象
	 */
	List<T> selectByMap(Map<String, Object> queryParam, String orderBy, boolean isAsc);
	
	/**
	 * 分页查询符合条件的实体对象
	 * @param queryParam 包含查询条件的propertyName及对应propertyValue的Map集合
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @return 返回符合查询条件的实体对象
	 */
	GenericPage<T> selectByMap(Map<String, Object> queryParam, int pageSize, int startIndex);
	
	/**
	 * 分页查询符合条件的实体对象
	 * @param queryParam 包含查询条件的propertyName及对应propertyValue的Map集合
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @return 返回符合查询条件的实体对象
	 */
	GenericPage<T> selectByMap(Map<String, Object> queryParam, int pageSize, int startIndex, String orderBy, boolean isAsc);
	
	/**
	 * 查询符合条件的记录主键
	 * @param queryParam 包含查询条件的propertyName及对应propertyValue的Map集合
	 * @return 返回符合查询条件的实体对象主键列表
	 */
	List<PK> selectIds(Map<String, Object> queryParam);
	
	/**
	 * 查询符合条件的记录主键
	 * @param queryParam 包含查询条件的propertyName及对应propertyValue的Map集合
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @return 返回符合查询条件的实体对象主键列表
	 */
	List<PK> selectIds(Map<String, Object> queryParam, String orderBy, boolean isAsc);
	
	/**
	 * 分页查询符合条件的记录主键
	 * @param queryParam 包含查询条件的propertyName及对应propertyValue的Map集合
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @return 返回符合查询条件的实体对象主键列表
	 */
	GenericPage<PK> selectIds(Map<String, Object> queryParam, int pageSize, int startIndex);
	
	/**
	 * 分页查询符合条件的记录主键
	 * @param queryParam 包含查询条件的propertyName及对应propertyValue的Map集合
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @return 返回符合查询条件的实体对象主键列表
	 */
	GenericPage<PK> selectIds(Map<String, Object> queryParam, int pageSize, int startIndex, String orderBy, boolean isAsc);

	/**
	 * 通过自定义Mapper查询记录
	 * @param statementPostfix 自定义Mapper后缀名
	 * @param queryParam 包含propertyName及对应propertyValue的Map集合
	 * @return 符合条件的实体对象记录
	 */
	List<T> selectByStatement(String statementPostfix, Map<String, Object> queryParam);
	
	/**
	 * 通过自定义Mapper查询记录
	 * @param statementPostfix 自定义Mapper后缀名
	 * @param queryParam 包含propertyName及对应propertyValue的Map集合
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @return 符合条件的实体对象记录
	 */
	List<T> selectByStatement(String statementPostfix, Map<String, Object> queryParam, String orderBy, boolean isAsc);
	
	/**
	 * 通过自定义Mapper分页查询记录
	 * @param statementPostfix 自定义Mapper后缀名
	 * @param queryParam 包含propertyName及对应propertyValue的Map集合
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @return 符合条件的实体对象记录
	 */
	GenericPage<T> selectByStatement(String statementPostfix, Map<String, Object> queryParam, int pageSize, int startIndex);
	
	/**
	 * 通过自定义Mapper分页查询记录
	 * @param statementPostfix 自定义Mapper后缀名
	 * @param queryParam 包含propertyName及对应propertyValue的Map集合
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @return 符合条件的实体对象记录
	 */
	GenericPage<T> selectByStatement(String statementPostfix, Map<String, Object> queryParam, int pageSize, int startIndex, String orderBy, boolean isAsc);

	/**
	 * 查询未删除的唯一对象
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public T selectUnDeletedUniqueByProp(String propertyName, Object propertyValue);
}
