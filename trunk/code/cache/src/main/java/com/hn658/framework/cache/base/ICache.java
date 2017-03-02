package com.hn658.framework.cache.base;


import com.hn658.framework.cache.storage.ICacheStorage;

/**
 * 缓存接口
 * Created by baihai
 * @param <K> 缓存Key类型
 * @param <V> 缓存Value类型
 */
public interface ICache<K,V> {


    /**
     * 获取缓存
     *
     * @param key 缓存Key
     * @return 缓存Value
     */
    V get(K key);


    /**
     * 失效key对应的缓存
     *
     * 如果是LRU的会在下一次使用这个Key时自动加载最新的
     * @param key
     * @see
     */
    void invalid(K key);

    /**
     * 失效传入的多个key对应的缓存
     *
     * @param keys
     * @see
     */
    void invalidMulti(K ... keys);

    /**
     * 增加缓存
     * @param key
     * @param value
     */
    void set(K key,V value);

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
     * 得到底层缓存模板
     * @return
     */
    Object getNativeCache();

    /**
     * 设置缓存实现
     * @param storage
     */
    public void setStorage(ICacheStorage<String, V> storage);

    /**
     * 关联DB获取数据
     * @param key
     * @return
     */
    public V getRelationDB(String key,CacheCallBack<V> callBack);

    /**
     * 标记当前Cache的UUID
     * @return
     */
     String getCacheId();


}
