/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		IHibernateBaseDAO
 *	包	名：		com.wzitech.chaos.framework.dataaccess.hibernate
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-18
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-4-18 下午1:07:52
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

import com.hn658.framework.dataaccess.IBaseDAO;
import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.entity.BaseEntity;

/**
 * @author Shawn
 *
 */
public interface IHibernateBaseDAO<T extends BaseEntity, PK extends Serializable> extends IBaseDAO<T, PK> {
	
	/**
	 * 保存对象
	 * 如果对象已在本session中持久化了,不做任何事。<br>
     * 如果另一个seesion拥有相同的持久化标识,抛出异常。<br>
     * 如果没有持久化标识属性,调用save()。<br>
     * 如果持久化标识表明是一个新的实例化对象,调用save()。<br>
     * 如果是附带版本信息的(<version>或<timestamp>)且版本属性表明为新的实例化对象就save()。<br>
     * 否则调用update()重新关联托管对象
	 * @param entity 需要保存的实体对象
	 * @return 保存后的实体对象
	 */
	T save(T entity);
	
	/**
	 * 创建脱机查询对象
	 * @param criterions 可变的Restrictions条件列表
	 * @return 返回根据条件创建完成的DetachedCriteria对象
	 */
	DetachedCriteria createCriteria(Criterion... criterions);

	/**
	 * 创建脱机查询对象
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @param criterions 可变的Restrictions条件列表
	 * @return 返回根据条件创建完成的DetachedCriteria对象
	 */
	DetachedCriteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions);

	/**
	 * 根据Hql查询记录
	 * @param hql 查询hql语句
	 * @param values 可变参数
	 * @return 返回符合查询条件的实体对象列表
	 */
	List<T> selectByHql(String hql, Object... values);
	
	/**
	 * 根据Hql分页查询记录
	 * @param hql 查询hql语句
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @param values 可变参数
	 * @return 返回符合查询条件的实体对象列表
	 */
	GenericPage<T> select(String hql, int pageSize, int startIndex, Object... values);
	
	/**
	 * 分页查询符合条件的记录
	 * @param criteria 查询条件
	 * @param pageNo 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @return 返回符合查询条件的实体对象列表
	 */
	GenericPage<T> select(DetachedCriteria criteria, int pageNo, int pageSize);
	
	/**
	 * 分页查询符合条件的记录
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @param criterions 可变查询条件
	 * @return 返回符合查询条件的实体对象列表
	 */
	GenericPage<T> select(int pageSize, int startIndex, Criterion... criterions);
	
	/**
	 * 分页查询符合条件的记录
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @param criterions 可变查询条件
	 * @return 返回符合查询条件的实体对象列表
	 */
	GenericPage<T> select(int pageSize, int startIndex, String orderBy, boolean isAsc, Criterion... criterions);
	
	/**
	 * 判断实体对象对应的属性在当前数据库表中是否唯一
	 * @param entity 实体对象
	 * @param propertyNames 对应判断唯一的属性列表，以逗号分割，如"id,name,sexual"
	 * @return 返回对应的属性值是否唯一
	 */
	boolean isUnique(T entity, String propertyNames);
	
	/**
	 * 取消与当前Hibernate Session的关联
	 * @param entity 实体对象
	 */
	void evit(T entity);
	
	/**
	 * flush当前Hibernate Session
	 */
	void flush();

	/**
	 * 清除当前Hibernate Session所有对象缓存
	 */
	void clear();
}
