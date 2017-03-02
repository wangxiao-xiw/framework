/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		IBaseDAO
 *	包	名：		com.wzitech.chaos.framework.dataaccess
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-16
 *	描	述：		基础数据操作泛型接口类
 *	更新纪录：	1. Shawn 创建于 2012-4-16 下午6:22:56
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess;

import java.io.Serializable;
import java.util.List;

import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.entity.BaseEntity;

/**
 * 基础数据操作泛型接口类
 * 定义基础DAO功能
 * @param <T> 泛型实体类
 * @param <PK> 泛型主键类，必须实现Serializable接口
 * @author Shawn
 *
 */
public interface IBaseDAO<T extends BaseEntity, PK extends Serializable> {
	
	/**
	 * 查询记录总条数
	 * @return 当前实体类型记录数
	 */
	int count();
	
	/**
	 * 新增记录
	 * @param entity 新增的实体对象
	 * @return 新增记录的主键ID
	 */
	Object insert(T entity);
	
	/**
	 * 批量新增记录
	 * @param list 新增的对象列表
	 * @return 影响的记录数，正常应等于传入列表参数长度
	 */
	int batchInsert(final List<T> list);
	
	/**
	 * 更新记录
	 * @param entity 需要更新的实体对象
	 * @return 影响的记录数，正常应返回1
	 */
	int update(T entity);
	
	/**
	 * 批量更新记录
	 * @param list 需要批量更新的实体对象列表
	 * @return 影响的记录数，正常应等于传入列表参数长度
	 */
	int batchUpdate(final List<T> list);

	/**
	 * 删除记录
	 * @param entity 需要删除的实体对象
	 * @return 影响的记录数，正常应返回1
	 */
	int delete(T entity);
	
	/**
	 * 通过主键ID删除记录
	 * @param id 主键ID
	 * @return 影响的记录数，正常应返回1
	 */
	int deleteById(PK id);
	
	/**
	 * 批量删除记录
	 * @param list 需要删除的实体对象列表
	 * @return 影响的记录数，正常应等于传入列表参数长度
	 */
	int batchDelete(final List<T> list);
	
	/**
	 * 通过主键查找记录
	 * @param id 主键ID
	 * @return 符合条件的实体对象
	 */
	T selectById(PK id);
	
	/**
	 * 查询符合条件的唯一记录
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @return 返回符合条件的实体对象
	 */
	T selectUniqueByProp(String propertyName, Object propertyValue);
	
	/**
	 * 查询符合条件的记录集
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @return 返回符合条件的实体对象列表
	 */
	List<T> selectByProp(String propertyName, Object propertyValue);
	
	/**
	 * 查询符合条件的记录集
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @return 返回符合条件的实体对象列表
	 */
	List<T> selectByProp(String propertyName, Object propertyValue, String orderBy, boolean isAsc);
	
	/**
	 * 分页查询符合条件的记录集
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @return 返回符合条件的实体对象列表
	 */
	GenericPage<T> selectByProp(String propertyName, Object propertyValue, int pageSize, int startIndex);
	
	/**
	 * 分页查询符合条件的记录集
	 * @param propertyName 属性名
	 * @param propertyValue 属性值
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @return 返回符合条件的实体对象列表
	 */
	GenericPage<T> selectByProp(String propertyName, Object propertyValue, int pageSize, int startIndex, String orderBy, boolean isAsc);
	
	/**
	 * 获取全部记录
	 * 注意：对于大数据量的表，强烈推荐使用分页查询{@link #selectByPage(int, int, String, boolean)}
	 * @return 所有实体对象
	 */
	List<T> selectAll();
	
	/**
	 * 获取全部记录
	 * 注意：对于大数据量的表，强烈推荐使用分页查询{@link #selectByPage(int, int, String, boolean)}
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @return 所有实体对象
	 */
	List<T> selectAll(String orderBy, boolean isAsc);
	
	/**
	 * 分页获取全部记录
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @return 分页结果实体对象
	 */
	GenericPage<T> selectAll(int pageSize, int startIndex);
	
	/**
	 * 分页获取全部记录
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @return 分页结果实体对象
	 */
	GenericPage<T> selectAll(int pageSize, int startIndex, String orderBy, boolean isAsc);
	
	/**
	 * 根据原生Sql查询记录
	 * @param sql 查询sql语句
	 * @return 返回符合查询条件的实体对象列表
	 */
	List<T> selectByNativeSql(String sql);
}
