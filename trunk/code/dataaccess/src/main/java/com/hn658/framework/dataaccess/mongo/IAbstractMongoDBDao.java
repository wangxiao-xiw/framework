package com.hn658.framework.dataaccess.mongo;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Query;

/**
 * 描述：BaseEntity超类下的 mongodb Dao泛型超类接口
 * 
 * @author nimin
 * @date 2014-8-29 下午3:23:24
 * @param <T>
 */
public interface IAbstractMongoDBDao<T> {

	/**
	 * 描述：插入 T 类数据
	 * 
	 * @param entity
	 *            需要插入的实体数据
	 * @author nimin
	 */
	T insert(T entity);

	/**
	 * 描述：查询 T 类的数据总数
	 * 
	 * @author nimin
	 */
	Long getCount(Query query);
	
	Long getCount(Class<? extends T> clazz);

	/**
	 * 描述：查询 T 的子类的数据总数
	 * 
	 * @param clazz
	 *            T的子类
	 * @author nimin
	 */
	Long getCount(Query query, Class<? extends T> clazz);

	/**
	 * 描述：批量插入 T
	 * 
	 * @param entityList
	 * @author nimin
	 */
	void insertAll(List<T> entityList);

	/**
	 * 描述：批量插入 T 的子类clazz
	 * 
	 * @param entityList
	 * @param clazz
	 *            T 的子类
	 * @author nimin
	 */
	void insertAll(List<? extends T> entityList, Class<? extends T> clazz);

	/**
	 * 描述：根据ID查询 T 类表
	 * 
	 * @param id
	 *            主键ID
	 * @author nimin
	 */
	T findById(Long id);

	/**
	 * 描述：根据ID查询T 类的子类表数据
	 * 
	 * @param id
	 *            主键ID
	 * @param clazz
	 *            T类的子类
	 * @author nimin
	 */
	T findById(Long id, Class<? extends T> clazz);

	/**
	 * 描述：查询T 类表中的所有数据
	 * 
	 * @author nimin
	 */
	List<T> findAll();

	/**
	 * 描述：查询 T 类的子类表中所有数据
	 * 
	 * @param clazz
	 * @author nimin
	 */
	List<? extends T> findAll(Class<? extends T> clazz);

	/**
	 * 描述：根据Map条件查询 T 类表中的所有数据
	 * 
	 * @param queryMap
	 *            查询条件
	 * @author nimin
	 */
	List<? extends T> findByStatem(Map<String, Object> queryMap);

	/**
	 * 描述：根据Map条件查询 T 类的子类的表中的所有数据
	 * 
	 * @param queryMap
	 *            查询条件
	 * @param clazz
	 *            T类的子类
	 * @author nimin
	 */
	List<? extends T> findByStatem(Map<String, Object> queryMap,
			Class<? extends T> clazz);

	/**
	 * 描述：根据Map条件查询 T 类的表中的数据(分页查询)
	 * 
	 * @param queryMap
	 *            查询条件
	 * @param clazz
	 *            T类的子类
	 * @param pageSize
	 *            一页记录条数
	 * @param start
	 *            其实记录数据
	 * @param orderBy
	 *            排序规则（默认ID，必须为表中的字段）
	 * @param isAsc
	 *            升降序 true为升序，false为降序
	 * @author nimin
	 */
	List<? extends T> findByStatem(Map<String, Object> queryMap, int pageSize,
			int start, String orderBy, Boolean isAsc);

	/**
	 * 描述：根据Map条件查询 T 类的子类的表中的数据(分页查询)
	 * 
	 * @param queryMap
	 *            查询条件
	 * @param clazz
	 *            T类的子类
	 * @param pageSize
	 *            一页记录条数
	 * @param start
	 *            其实记录数据
	 * @param orderBy
	 *            排序规则（默认ID，必须为表中的字段）
	 * @param isAsc
	 *            升降序 true为升序，false为降序
	 * @author nimin
	 */
	List<? extends T> findByStatem(Map<String, Object> queryMap,
			Class<? extends T> clazz, int pageSize, int start, String orderBy,
			Boolean isAsc);

	Long getCount();
}
