/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		AbstractHibernateBaseDAO
 *	包	名：		com.wzitech.chaos.framework.dataaccess.hibernate
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-4-14
 *	描	述：		Hibernate基础数据操作泛型接口类
 *	更新纪录：	1. Shawn 创建于 2012-4-14 上午2:01:49
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.entity.BaseEntity;
import com.hn658.framework.shared.exception.SystemException;
import com.hn658.framework.shared.utils.GeneralBeanUtils;
import com.hn658.framework.shared.utils.GenericsUtils;

/**
 * Hibernate基础数据操作泛型接口类
 * @author Shawn
 *
 */
public abstract class AbstractHibernateBaseDAO<T extends BaseEntity, PK extends Serializable> extends HibernateDaoSupport implements IHibernateBaseDAO<T, PK>{

	protected static final Logger logger = LoggerFactory.getLogger(AbstractHibernateBaseDAO.class);
	protected static final int BATCH_EXECUTE_COUNT = 30;
	
	protected Class<T> entityClass;
	
	/**
	 * 注入SessionFactory
	 * @param sessionFactory 会话工厂
	 */
	@Resource(name="sessionFactory")    
    public void setSuperSessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }
	
	@SuppressWarnings("unchecked")
	public AbstractHibernateBaseDAO() {
		// 通过范型反射，取得在子类中定义的class.
		this.entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#count()
	 */
	@Override
	public int count() {
		String hsql = String.format("SELECT COUNT(*) FROM %s", this.entityClass.getSimpleName());
		logger.debug("COUNT HSQL : {}", hsql);
		
		return  ((Long) this.getHibernateTemplate().find(hsql).get(0)).intValue();
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#insert(java.lang.Object)
	 */
	@Override
	public Object insert(T entity) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(entity);
		
		this.getHibernateTemplate().saveOrUpdate(entity);
		return entity.getId();
	}
	
	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#save(java.lang.Object)
	 */
	@Override
	public T save(T entity) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(entity);
		
		this.getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#batchInsert(java.util.List)
	 */
	@Override
	public int batchInsert(List<T> list) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(list);
		Validate.noNullElements(list);
		
		Session session = this.getSession();
		Transaction tx = null;
		int rowsAffected = 0;
		
		try{
			tx = session.beginTransaction();
			
			for(int i = 0; i < list.size(); i++){
				session.saveOrUpdate(list.get(i));
				
				// 影响行数加1
				rowsAffected ++;
				
				// flush当前一批数据
				if (i % BATCH_EXECUTE_COUNT == 0) {
					session.flush();
					session.clear();
				}
			}
			
			tx.commit();
		}catch(Exception ex){
			logger.error("数据库操作时发生严重错误.", ex);
			
			// 回滚事务
			if(null != tx){
				tx.rollback();
			}
			
			// 清零影响行数
			rowsAffected = 0;
		}finally{
			if(null != session){
				session.close();
			}
		}
		
		return rowsAffected;
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#update(java.lang.Object)
	 */
	@Override
	public int update(T entity) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(entity);
		
		int rowsAffected = 0;
		this.getHibernateTemplate().saveOrUpdate(entity);
		rowsAffected ++;
		return rowsAffected;
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#batchUpdate(java.util.List)
	 */
	@Override
	public int batchUpdate(List<T> list) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(list);
		Validate.noNullElements(list);
		
		Session session = this.getSession();
		Transaction tx = null;
		int rowsAffected = 0;
		
		try{
			tx = session.beginTransaction();
			
			for(int i = 0; i < list.size(); i++){
				session.saveOrUpdate(list.get(i));
				
				// 影响行数加1
				rowsAffected ++;
				
				// flush当前一批数据
				if (i % BATCH_EXECUTE_COUNT == 0) {
					session.flush();
					session.clear();
				}
			}
			
			tx.commit();
		}catch(Exception ex){
			logger.error("数据库操作时发生严重错误.", ex);
			
			// 回滚事务
			if(null != tx){
				tx.rollback();
			}
			
			// 清零影响行数
			rowsAffected = 0;
		}finally{
			if(null != session){
				session.close();
			}
		}
		
		return rowsAffected;
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#delete(java.lang.Object)
	 */
	@Override
	public int delete(T entity) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(entity);
		
		int rowsAffected = 0;
		this.getHibernateTemplate().delete(entity);
		rowsAffected ++;
		return rowsAffected;
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#deleteById(java.io.Serializable)
	 */
	@Override
	public int deleteById(PK id) {
		int rowsAffected = 0;
		this.getHibernateTemplate().delete(selectById(id));
		rowsAffected ++;
		return rowsAffected;
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#batchDelete(java.util.List)
	 */
	@Override
	public int batchDelete(List<T> list) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notEmpty(list);
		Validate.noNullElements(list);
		
		this.getHibernateTemplate().deleteAll(list);
		return list.size();
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectById(java.io.Serializable)
	 */
	@Override
	public T selectById(PK id) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectUniqueByProp(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T selectUniqueByProp(String propertyName, Object propertyValue) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(propertyName);
		Validate.notNull(propertyValue);
		
		List<T> resultList = this.getHibernateTemplate().findByCriteria(createCriteria(Restrictions.eq(propertyName, propertyValue)));
		
		if(!resultList.isEmpty()){
			return resultList.get(0);
		}
		else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectByProp(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectByProp(String propertyName, Object propertyValue) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(propertyName);
		Validate.notNull(propertyValue);
		
		return this.getHibernateTemplate().findByCriteria(createCriteria(Restrictions.eq(propertyName, propertyValue)));
	}
	
	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectByProp(java.lang.String, java.lang.Object, java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectByProp(String propertyName, Object propertyValue, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(propertyName);
		Validate.notNull(propertyValue);
		Validate.notBlank(orderBy);
		
		return this.getHibernateTemplate().findByCriteria(createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, propertyValue)));
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectByProp(java.lang.String, java.lang.Object, int, int)
	 */
	@Override
	public GenericPage<T> selectByProp(String propertyName, Object propertyValue, int pageSize, int startIndex) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(propertyName);
		Validate.notNull(propertyValue);
		
		DetachedCriteria criteria = createCriteria(Restrictions.eq(propertyName, propertyValue));
		
		return pagedQuery(criteria, pageSize, startIndex);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectByProp(java.lang.String, java.lang.Object, int, int, java.lang.String, boolean)
	 */
	@Override
	public GenericPage<T> selectByProp(String propertyName, Object propertyValue, int pageSize, int startIndex, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(propertyName);
		Validate.notNull(propertyValue);
		Validate.notBlank(orderBy);
		
		DetachedCriteria criteria = createCriteria(orderBy, isAsc, Restrictions.eq(propertyName, propertyValue));
		
		return pagedQuery(criteria, pageSize, startIndex);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectAll()
	 */
	@Override
	public List<T> selectAll() {
		return this.getHibernateTemplate().loadAll(this.entityClass);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectAll(java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectAll(String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(orderBy);
		
		DetachedCriteria criteria = createCriteria(orderBy, isAsc);
		
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectAll(int, int)
	 */
	@Override
	public GenericPage<T> selectAll(int pageSize, int startIndex) {
		return pagedQuery(pageSize, startIndex);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectAll(int, int, java.lang.String, boolean)
	 */
	@Override
	public GenericPage<T> selectAll(int pageSize, int startIndex, String orderBy, boolean isAsc) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(orderBy);
		
		DetachedCriteria criteria = createCriteria(orderBy, isAsc);
		
		return pagedQuery(criteria, pageSize, startIndex);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#selectByHql(java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectByHql(String hql, Object... values) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(hql);
		
		return this.getHibernateTemplate().find(hql, values);
	}
	
	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#select(java.lang.String, int, int, java.lang.Object[])
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public GenericPage<T> select(String hql, int pageSize, int startIndex, Object... values) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(hql);
		
		//检查分页参数
		if(pageSize < 1){
			throw new IllegalArgumentException("分页pageSize参数必须大于1");
		}
		
		if(startIndex < 0){
			throw new IllegalArgumentException("分页startIndex参数必须大于0");
		}
		
		// Count查询
        String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
        List countlist = this.getHibernateTemplate().find(countQueryString, values);
        int totalCount = (Integer) countlist.get(0);
        
        if (totalCount < 1){
            return new GenericPage<T>();
        }
        
        // 实际查询返回分页对象
        Session session = this.getSession();
        Query query = createQuery(session, hql, values);
        List<T> list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        this.releaseSession(session);
        
        return new GenericPage<T>(startIndex, totalCount, pageSize, list);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#select(org.hibernate.criterion.DetachedCriteria, int, int)
	 */
	@Override
	public GenericPage<T> select(DetachedCriteria criteria, int pageSize, int startIndex) {
		return pagedQuery(criteria, pageSize, startIndex);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#select(int, int, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public GenericPage<T> select(int pageSize, int startIndex, Criterion... criterions) {
		return pagedQuery(pageSize, startIndex, criterions);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#select(int, int, java.lang.String, boolean, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public GenericPage<T> select(int pageSize, int startIndex, String orderBy, boolean isAsc, Criterion... criterions) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(orderBy);
		
		DetachedCriteria criteria = createCriteria(orderBy, isAsc);
		
		return pagedQuery(criteria, pageSize, startIndex);
	}
	
	/**
	 * 分页查询函数
	 * @param criteria 查询条件
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @return 返回符合查询条件的实体对象
	 */
	@SuppressWarnings("unchecked")
	public GenericPage<T> pagedQuery(DetachedCriteria criteria, int pageSize, int startIndex) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(criteria);
		
		//检查分页参数
		if(pageSize < 1){
			throw new IllegalArgumentException("分页pageSize参数必须大于1");
		}
		
		if(startIndex < 0){
			throw new IllegalArgumentException("分页startIndex参数必须大于0");
		}
		
		Session session = this.getSession(true);
		CriteriaImpl impl =  (CriteriaImpl) criteria.getExecutableCriteria(session);
		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		List<CriteriaImpl.OrderEntry> orderEntries;
		try {
			orderEntries = (List<CriteriaImpl.OrderEntry>) GeneralBeanUtils.forceGetProperty(impl, "orderEntries");
			GeneralBeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList<T>());
		} catch (Exception e) {
			logger.error("运行时发生严重错误.", e);
			throw new SystemException("运行时发生严重错误.", e);
		}
		// 执行查询
		int totalCount = ((Number) impl.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		try {
			GeneralBeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("运行时发生严重错误.", e);
			throw new SystemException("运行时发生严重错误.", e);
		}
		// 返回分页对象
		if (totalCount < 1)
			return new GenericPage<T>();
		;
		List<T> list = impl.setFirstResult(startIndex).setMaxResults(pageSize).list();
		
		this.releaseSession(session);
		return new GenericPage<T>(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 分页查询函数
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @param criterions 查询条件
	 * @return 返回符合查询条件的实体对象
	 */
	public GenericPage<T> pagedQuery(int pageSize, int startIndex, Criterion... criterions) {
		DetachedCriteria criteria = createCriteria(criterions);
		return pagedQuery(criteria, pageSize, startIndex);
	}

	/**
	 * 分页查询函数
	 * @param pageSize 分页大小，默认为20
	 * @param startIndex 起始数据索引，默认从0开始
	 * @param orderBy 排序列
	 * @param isAsc 排序规则，true代表升序，false代表降序
	 * @param criterions 查询条件
	 * @return 返回符合查询条件的实体对象
	 */
	public GenericPage<T> pagedQuery(int pageSize, int startIndex, String orderBy, boolean isAsc, Criterion... criterions) {
		DetachedCriteria criteria = createCriteria(orderBy, isAsc, criterions);
		return pagedQuery(criteria, pageSize, startIndex);
	}
	
	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.IBaseDAO#selectByNativeSql(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectByNativeSql(String sql) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(sql);
		
		Session session = this.getSession();
    	List<T> resultList = session.createSQLQuery(sql).list();
    	this.releaseSession(session);
    	return resultList;
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#createCriteria(org.hibernate.criterion.Criterion[])
	 */
	@Override
	public DetachedCriteria createCriteria(Criterion... criterions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(this.entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#createCriteria(java.lang.String, boolean, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public DetachedCriteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notBlank(orderBy);
		
		DetachedCriteria criteria = createCriteria(criterions);
		if (isAsc)
			criteria.addOrder(Order.asc(orderBy));
		else
			criteria.addOrder(Order.desc(orderBy));
		return criteria;
	}
	
	public Query createQuery(Session session, String hql, Object... values) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(session);
		Validate.notBlank(hql);
		
        Query query = session.createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query;
    }
	
	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#isUnique(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean isUnique(T entity, String propertyNames) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(entity);
		Validate.notBlank(propertyNames);
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#evit(java.lang.Object)
	 */
	@Override
	public void evit(T entity) {
		// 检查参数是否为null或者元素长度为0
		// 如果是抛出异常
		Validate.notNull(entity);
		
		this.getHibernateTemplate().evict(entity);
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#flush()
	 */
	@Override
	public void flush() {
		this.getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see com.wzitech.chaos.framework.server.dataaccess.hibernate.IHibernateBaseDAO#clear()
	 */
	@Override
	public void clear() {
		this.getHibernateTemplate().clear();
	}
	
	/**
     * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
     */
    private static String removeSelect(String hql) {
    	// 检查参数是否为null或者元素长度为0
    	// 如果是抛出异常
        Validate.notBlank(hql);
        
        int beginPos = hql.toLowerCase().indexOf("from");
        
        if(beginPos == -1){
        	throw new IllegalArgumentException(String.format("HQL：%s 中找不到from关键字", hql));
        }
        
        return hql.substring(beginPos);
    }
    
    /**
     * 去除hql的orderby 子句，用于pagedQuery.
     */
    private static String removeOrders(String hql) {
    	// 检查参数是否为null或者元素长度为0
    	// 如果是抛出异常
        Validate.notBlank(hql);
        
        Pattern p = Pattern.compile("order//s*by[//w|//W|//s|//S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        
        return sb.toString();
    }
}
