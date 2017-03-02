/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		AbstractMybatisBaseDAO
 *	包	名：		com.wzitech.chaos.framework.dataaccess.mybatis
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-14
 *	描	述：		MyBatis DAO的抽象泛型基类. 
 *	更新纪录：	1. Shawn 创建于 2012-4-14 上午1:32:35
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.mybatis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.entity.BaseEntity;
import com.hn658.framework.shared.utils.GenericsUtils;

/**
 * MyBatis DAO的抽象泛型基类<br/>
 * 继承于MyBatis Spring的SqlSessionDaoSupport,提供了基于泛型的数据访问方法.
 * 
 * @author Shawn
 * 
 */
public abstract class AbstractMyBatisDAO<T extends BaseEntity, PK extends Serializable> extends SqlSessionDaoSupport implements IMyBatisBaseDAO<T, PK>{

	protected static final Logger logger = LoggerFactory.getLogger(AbstractMyBatisDAO.class);

	protected Class<T> entityClass;

	protected String mapperNamespace;
	
	@SuppressWarnings("unchecked")
	public AbstractMyBatisDAO() {
		// 通过范型反射，取得在子类中定义的class.
		this.entityClass = GenericsUtils.getSuperClassGenricType(getClass());
		this.mapperNamespace = entityClass.getName();
	}

	/**
	 * @return the sqlmapNamespace
	 */
	public String getMapperNamespace() {
		return mapperNamespace;
	}

	/**
	 * @param sqlmapNamespace the sqlmapNamespace to set
	 */
	public void setMapNamespace(String mapperNamespace) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(mapperNamespace);
		
		this.mapperNamespace = mapperNamespace;
	}

	@Override
	public int count() {
		return (Integer)this.getSqlSession().selectOne(this.mapperNamespace + MyBatisPostfixConstants.COUNT);
	}
	
	@Override
	public int countByMap(Map<String, Object> queryParam) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(queryParam);
		
		return (Integer)this.getSqlSession().selectOne(this.mapperNamespace + MyBatisPostfixConstants.COUNT_BY_MAP, queryParam);
	}

	@Override
	public int countLikeByMap(Map<String, Object> queryParam) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(queryParam);
				
		return (Integer)this.getSqlSession().selectOne(this.mapperNamespace + MyBatisPostfixConstants.COUNT_LIKE_BY_MAP, queryParam);
	}
	
	@Override
	public Object insert(T entity) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(entity);
		
		long affectedRows = this.getSqlSession().insert(this.mapperNamespace + MyBatisPostfixConstants.INSERT, entity);
//		entity.setId(id);
		return affectedRows;
	}

	@Override
	public int batchInsert(List<T> list) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(list);
				
		// 使用update代替insert
		// 以便获取影响记录行数
		return this.getSqlSession().update(this.mapperNamespace + MyBatisPostfixConstants.BATCH_INSERT, list);
	}

	@Override
	public int insertByStatement(String statementPostfix, Map<String, Object> insertParam) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(statementPostfix);
		Validate.notEmpty(insertParam);
		
		// 使用update代替insert
		// 以便获取影响记录行数
		return this.getSqlSession().update(this.mapperNamespace + "." +statementPostfix, insertParam);
	}
	
	@Override
	public int update(T entity) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(entity);
		
		List<T> list = new ArrayList<T>();
		list.add(entity);
		
		return batchUpdate(list);
	}

	@Override
	public int batchUpdate(List<T> list) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(list);
		Validate.noNullElements(list);
		
		return this.getSqlSession().update(this.mapperNamespace + MyBatisPostfixConstants.BATCH_UPDATE, list);
	}
	
	@Override
	public int updateById(PK id, Map<String, Object> updateParam) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(updateParam);
		
		List<PK> ids = new ArrayList<PK>();
		ids.add(id);
		
		return batchUpdateByIds(ids, updateParam);
	}

	@Override
	public int batchUpdateByIds(List<PK> ids, Map<String, Object> updateParam) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(ids);
		Validate.notEmpty(updateParam);
		Validate.noNullElements(ids);
		
		updateParam.put("ids", ids);
		
		return this.getSqlSession().update(this.mapperNamespace + MyBatisPostfixConstants.BATCH_UPDATE_BY_IDS, updateParam);
	}

	@Override
	public int updateByStatement(String statementPostfix, Map<String, Object> queryParam) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(statementPostfix);
		Validate.notEmpty(queryParam);
		
		return this.getSqlSession().update(this.mapperNamespace + "." +statementPostfix, queryParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(T entity) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(entity);
		Validate.notNull(entity.getId());
		
		return deleteById((PK) entity.getId());
	}

	@Override
	public int deleteById(PK id) {
		List<PK> ids = new ArrayList<PK>();
		ids.add(id);
		
		return batchDeleteByIds(ids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int batchDelete(List<T> list) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(list);
		Validate.noNullElements(list);
		
		List<PK> ids = new ArrayList<PK>();
		for(T entity : list){
			ids.add((PK) entity.getId());
		}
		
		return batchDeleteByIds(ids);
	}
	
	@Override
	public int batchDeleteByIds(List<PK> ids) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(ids);
		Validate.noNullElements(ids);
		
		return this.getSqlSession().delete(this.mapperNamespace + MyBatisPostfixConstants.BATCH_DELETE_BY_IDS, ids);
	}

	@Override
	public int deleteByMap(Map<String, Object> deleteParam) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(deleteParam);
		
		return this.getSqlSession().delete(this.mapperNamespace + MyBatisPostfixConstants.DELETE_BY_MAP, deleteParam);
	}

	@Override
	public int deleteByStatement(String statementPostfix, Map<String, Object> deleteParam) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(statementPostfix);
		Validate.notEmpty(deleteParam);
		
		return this.getSqlSession().delete(this.mapperNamespace + "." +statementPostfix, deleteParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T selectById(PK id) {
		return (T)this.getSqlSession().selectOne(this.mapperNamespace + MyBatisPostfixConstants.SELECT_BY_ID, id);
	}
	
	@Override
	public List<T> selectByIds(List<PK> ids) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(ids);
		Validate.noNullElements(ids);
		
		return this.getSqlSession().selectList(this.mapperNamespace + MyBatisPostfixConstants.SELECT_BY_IDS, ids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T selectUniqueByProp(String propertyName, Object propertyValue) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(propertyName);
		Validate.notNull(propertyValue);
		
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put(propertyName, propertyValue);
		return (T)this.getSqlSession().selectOne(this.mapperNamespace + MyBatisPostfixConstants.SELECT_UNIQUE_BY_PROP, queryParam);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T selectUnDeletedUniqueByProp(String propertyName, Object propertyValue) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(propertyName);
		Validate.notNull(propertyValue);
		
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put(propertyName, propertyValue);
		queryParam.put("isDeleted", false);
		return (T)this.getSqlSession().selectOne(this.mapperNamespace + MyBatisPostfixConstants.SELECT_UNIQUE_BY_PROP, queryParam);
	}

	public List<T> selectByProp(String propertyName, Object propertyValue){
		return selectByProp(propertyName, propertyValue, "id", true);
	}
	
	@Override
	public List<T> selectByProp(String propertyName, Object propertyValue, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(propertyName);
		Validate.notNull(propertyValue);
		Validate.notBlank(orderBy);
		
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put(propertyName, propertyValue);
		
		return selectByMap(queryParam, orderBy, isAsc);
	}

	@Override
	public GenericPage<T> selectByProp(String propertyName, Object propertyValue, int pageSize, int startIndex) {
		return selectByProp(propertyName, propertyValue, pageSize, startIndex, "id", true);
	}

	@Override
	public GenericPage<T> selectByProp(String propertyName, Object propertyValue, int pageSize, int startIndex, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(propertyName);
		Validate.notNull(propertyValue);
		Validate.notBlank(orderBy);
		
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put(propertyName, propertyValue);
		
		return selectByMap(queryParam, pageSize, startIndex, orderBy, isAsc);
	}

	@Override
	public List<T> selectAll() {
		return selectByMap(null, "id", true);
	}

	@Override
	public List<T> selectAll(String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(orderBy);
		
		return selectByMap(null, orderBy, isAsc);
	}

	@Override
	public GenericPage<T> selectAll(int pageSize, int startIndex) {
		return selectByMap(null, pageSize, startIndex);
	}

	@Override
	public GenericPage<T> selectAll(int pageSize, int startIndex, String orderBy, boolean isAsc) {
		return selectByMap(null, pageSize, startIndex, orderBy, isAsc);
	}

	@Override
	public List<T> selectByMap(Map<String, Object> queryParam) {
		return selectByMap(queryParam, "id", true);
	}

	@Override
	public List<T> selectByMap(Map<String, Object> queryParam, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(orderBy);
		
		if(null == queryParam){
			queryParam = new HashMap<String, Object>();
		}
		queryParam.put("ORDERBY", orderBy);
		if(isAsc){
			queryParam.put("ORDER", "ASC");
		}else{
			queryParam.put("ORDER", "DESC");
		}
				
		return this.getSqlSession().selectList(this.mapperNamespace + MyBatisPostfixConstants.SELECT_BY_MAP, queryParam);
	}

	@Override
	public GenericPage<T> selectByMap(Map<String, Object> queryParam, int pageSize, int startIndex) {
		return selectByMap(queryParam, pageSize, startIndex, "id", true);
	}

	@Override
	public GenericPage<T> selectByMap(Map<String, Object> queryParam, int pageSize, int startIndex, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(orderBy);
		
		//检查分页参数
		if(pageSize < 1){
			throw new IllegalArgumentException("分页pageSize参数必须大于1");
		}
		
		if(startIndex < 0){
			throw new IllegalArgumentException("分页startIndex参数必须大于0");
		}
		
		if(null == queryParam){
			queryParam = new HashMap<String, Object>();
		}
		queryParam.put("ORDERBY", orderBy);
		if(isAsc){
			queryParam.put("ORDER", "ASC");
		}else{
			queryParam.put("ORDER", "DESC");
		}
		
		int totalSize = countByMap(queryParam);
		
		// 如果数据Count为0，则直接返回
		if(totalSize == 0){
			return new GenericPage<T>(startIndex, totalSize, pageSize, null);
		}
		
		List<T> pagedData = this.getSqlSession().selectList(this.mapperNamespace + MyBatisPostfixConstants.SELECT_BY_MAP, 
				queryParam, new RowBounds(startIndex, pageSize));
		
		return new GenericPage<T>(startIndex, totalSize, pageSize, pagedData);
	}

	@Override
	public List<PK> selectIds(Map<String, Object> queryParam) {
		return selectIds(queryParam, "id", true);
	}

	@Override
	public List<PK> selectIds(Map<String, Object> queryParam, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(orderBy);
		
		if(null == queryParam){
			queryParam = new HashMap<String, Object>();
		}
		queryParam.put("ORDERBY", orderBy);
		if(isAsc){
			queryParam.put("ORDER", "ASC");
		}else{
			queryParam.put("ORDER", "DESC");
		}
		
		return this.getSqlSession().selectList(this.mapperNamespace + MyBatisPostfixConstants.SELECT_IDS, queryParam);
	}

	@Override
	public GenericPage<PK> selectIds(Map<String, Object> queryParam, int pageSize, int startIndex) {
		return selectIds(queryParam, pageSize, startIndex, "id", true);
	}

	@Override
	public GenericPage<PK> selectIds(Map<String, Object> queryParam, int pageSize, int startIndex, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(orderBy);
		
		//检查分页参数
		if(pageSize < 1){
			throw new IllegalArgumentException("分页pageSize参数必须大于1");
		}
		
		if(startIndex < 0){
			throw new IllegalArgumentException("分页startIndex参数必须大于0");
		}
		
		if(null == queryParam){
			queryParam = new HashMap<String, Object>();
		}
		queryParam.put("ORDERBY", orderBy);
		if(isAsc){
			queryParam.put("ORDER", "ASC");
		}else{
			queryParam.put("ORDER", "DESC");
		}
		
		int totalSize = countByMap(queryParam);
		
		// 如果数据Count为0，则直接返回
		if(totalSize == 0){
			return new GenericPage<PK>(startIndex, totalSize, pageSize, null);
		}
		
		List<PK> pagedData = this.getSqlSession().selectList(this.mapperNamespace + MyBatisPostfixConstants.SELECT_IDS, 
				queryParam, new RowBounds(startIndex, pageSize));
		
		return new GenericPage<PK>(startIndex, totalSize, pageSize, pagedData);
	}

	@Override
	public List<T> selectByStatement(String statementPostfix, Map<String, Object> queryParam) {
		return selectByStatement(statementPostfix, queryParam, "id", true);
	}

	@Override
	public List<T> selectByStatement(String statementPostfix, Map<String, Object> queryParam, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(statementPostfix);
		
		if(null == queryParam){
			queryParam = new HashMap<String, Object>();
		}
		queryParam.put("ORDERBY", orderBy);
		if(isAsc){
			queryParam.put("ORDER", "ASC");
		}else{
			queryParam.put("ORDER", "DESC");
		}
		
		return this.getSqlSession().selectList(this.mapperNamespace + statementPostfix, queryParam);
	}

	@Override
	public GenericPage<T> selectByStatement(String statementPostfix, Map<String, Object> queryParam, int pageSize, int startIndex) {
		return selectByStatement(statementPostfix, queryParam, pageSize, startIndex, "id", true);
	}

	@Override
	public GenericPage<T> selectByStatement(String statementPostfix, Map<String, Object> queryParam, int pageSize, int startIndex, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(orderBy);
		
		//检查分页参数
		if(pageSize < 1){
			throw new IllegalArgumentException("分页pageSize参数必须大于1");
		}
		
		if(startIndex < 0){
			throw new IllegalArgumentException("分页startIndex参数必须大于0");
		}
		
		if(null == queryParam){
			queryParam = new HashMap<String, Object>();
		}
		queryParam.put("ORDERBY", orderBy);
		if(isAsc){
			queryParam.put("ORDER", "ASC");
		}else{
			queryParam.put("ORDER", "DESC");
		}
		
		int totalSize = countByMap(queryParam);
		
		// 如果数据Count为0，则直接返回
		if(totalSize == 0){
			return new GenericPage<T>(startIndex, totalSize, pageSize, null);
		}
		
		List<T> pagedData = this.getSqlSession().selectList(this.mapperNamespace + "." +statementPostfix, 
				queryParam, new RowBounds(startIndex, pageSize));
		
		return new GenericPage<T>(startIndex, totalSize, pageSize, pagedData);
	}
	
	@Override
	public List<T> selectByNativeSql(String sql) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(sql);
		
		return this.getSqlSession().selectList(this.mapperNamespace + MyBatisPostfixConstants.SELECT_BY_NATIVE_SQL, sql);
	}
}
