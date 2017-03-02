package com.hn658.framework.dataaccess.mongo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.hn658.framework.shared.entity.BaseEntity;

/**
 * 描述：BaseEntity超类下的 mongodb Dao泛型超类实现类
 * 
 * @author nimin
 * @date 2014-8-29 下午3:11:30
 * @param <T>
 */
public abstract class AbstractMongoDBDaoImpl<T extends BaseEntity> extends
		AbstractMongoDAO implements IAbstractMongoDBDao<T> {
	protected Class<T> clazz;

	protected String className;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AbstractMongoDBDaoImpl() {
		this.clazz = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.clazz = (Class<T>) p[0];
		}
		className = clazz.getSimpleName();
	}

	/**
	 * 描述：插入数据
	 */
	@Override
	public T insert(T entity) {
		List<T> list = new ArrayList<T>();
		list.add(entity);
		this.mongoTemplate.insert(list, entity.getClass());
		return entity;
	}
	
	/**
	 * 描述：获取长度
	 */
	@Override
	public Long getCount() {
		Query query = new Query();
		return getCount(query, clazz);
	}

	/**
	 * 描述：获取长度
	 */
	@Override
	public Long getCount(Query query) {
		if(query==null){
			query = new Query();
		}
		return getCount(query, clazz);
	}
	
	@Override
	public Long getCount(Class<? extends T> clazz) {
		Query query = new Query();
		return getCount(query, clazz);
	}

	@Override
	public Long getCount(Query query, Class<? extends T> clazz) {
		if(query==null){
			query = new Query();
		}
		return this.mongoTemplate.count(query, clazz);
	}

	/**
	 * 描述：批量插入
	 */
	@Override
	public void insertAll(List<T> entityList) {
		this.mongoTemplate.insertAll(entityList);
	}

	@Override
	public void insertAll(List<? extends T> entityList, Class<? extends T> clazz) {
		this.mongoTemplate.insert(entityList, clazz);
	}

	/**
	 * 描述：根据ID查找数据
	 */
	@Override
	public T findById(Long id) {
		return findById(id, clazz);
	}

	@Override
	public T findById(Long id, Class<? extends T> clazz) {
		return this.mongoTemplate.findById(id, clazz);
	}

	/**
	 * 描述：查找全部数据
	 */
	@Override
	public List<T> findAll() {
		return this.mongoTemplate.findAll(clazz);
	}

	@Override
	public List<? extends T> findAll(Class<? extends T> clazz) {
		return this.mongoTemplate.findAll(clazz);
	}

	/**
	 * 描述：根据条件查询
	 * 
	 * @author nimin
	 */
	@Override
	public List<? extends T> findByStatem(Map<String, Object> queryMap) {
		return this.findByStatem(queryMap, clazz);
	}

	@Override
	public List<? extends T> findByStatem(Map<String, Object> queryMap,
			Class<? extends T> clazz) {
		Criteria criteria = new Criteria();
		for (String key : queryMap.keySet()) {
			Object value = queryMap.get(key);
			if (value != null) {
				criteria.and(key).is(value);
			}
		}
		Query query = new Query(criteria);
		return this.mongoTemplate.find(query, clazz);
	}

	/**
	 * 描述：根据条件分页查询
	 * 
	 * @author nimin
	 */
	@Override
	public List<? extends T> findByStatem(Map<String, Object> queryMap,
			int pageSize, int start, String orderBy, Boolean isAsc) {
		return this.findByStatem(queryMap, clazz, pageSize, start, orderBy,
				isAsc);
	}

	@Override
	public List<? extends T> findByStatem(Map<String, Object> queryMap,
			Class<? extends T> clazz, int pageSize, int start, String orderBy,
			Boolean isAsc) {
		if (queryMap == null || pageSize < 0 || start < 0) {
			return null;
		}
		Criteria criteria = new Criteria();
		for (String key : queryMap.keySet()) {
			Object value = queryMap.get(key);
			if (value != null) {
				criteria.and(key).is(value);
			}
		}
		Query query = new Query(criteria);
		orderBy = (StringUtils.isEmpty(orderBy)) ? "id" : orderBy;
		if (isAsc) {
			query.with(new Sort(Sort.Direction.ASC, orderBy));
		} else {
			query.with(new Sort(Sort.Direction.DESC, orderBy));
		}
		query.skip(start);
		if (pageSize > 0) {
			query.limit(pageSize);
		}
		return this.mongoTemplate.find(query, clazz);
	}
}
