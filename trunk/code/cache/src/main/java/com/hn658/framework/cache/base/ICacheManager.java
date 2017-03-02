package com.hn658.framework.cache.base;


import java.util.Collection;

/**
 *
 * 缓存管理
 * Created by baihai on 2015/10/29.
 */
public interface ICacheManager {


    /**
     * 获取缓存对象
     * @param name
     * @param <K>
     * @param <V>
     * @return
     */
    <K,V> ICache<K,V> getCache(String name);

    /**
     * 拿到所有的缓存
     * @return
     */
    Collection<String> getCacheNames();
}
