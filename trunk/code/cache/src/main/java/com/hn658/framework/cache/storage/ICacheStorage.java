package com.hn658.framework.cache.storage;

/**
 * Created by baihai on 2015/10/21.
 */
public interface ICacheStorage<K,V> {

    /**
     * 获取缓存原型模板
     * @return
     */
    public  Object getTemplate();

    /**
     * 根据key获取缓存
     * @param key
     * @return
     */
    public  V get(K key);

    /**
     * 添加缓存数据
     * @param key
     * @param value
     */
    void set(K key, V value);

    /**
     * 将value关联到key,并将key的生存时间设置为expire（以秒为单位）
     *
     * @param key
     * @param value
     * @param expire
     * @return
     * @see
     */
    void set(K key, V value, int expire);

    /**
     * 删除给定key
     *
     * @param key 缓存Key
     */
    void remove(K key);


    /**
     * 删除给定的一个或多个key
     *
     * @param keys 动态参数 数组[]
     * @see
     */
    void removeMulti(K ... keys);

    /**
     * 获取实体的泛型的类型
     * @param entityClass
     */
    void setEntityClass(Class<V> entityClass);


}
