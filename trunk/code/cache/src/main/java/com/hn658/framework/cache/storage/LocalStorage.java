package com.hn658.framework.cache.storage;

import com.hn658.framework.cache.base.CacheStorage;
import com.hn658.framework.cache.base.CacheType;
import com.hn658.framework.cache.local.LocallyHashMap;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;

/**
 * Created by baihai on 2015/10/16.
 */
@CacheStorage(type = CacheType.Local)
public  class LocalStorage<V> implements ICacheStorage<String,V>{

    protected  Class<V> entityClass;

    /**
     * 存储的map
     */
    private LocallyHashMap<String, V> map;

    /**
     * 线程安全lock对象
     */
    final Object mutex;

    /**
     * 最大容量，默认值：10000
     */
    private int maxCapacity = 10000;


    /**
     * 通过指定初始化容量初始化缓存存储器
     * @author ningyu
     * @date 2013-4-22 下午5:34:22
     * @param maxCapacity
     */
    public LocalStorage(int maxCapacity) {
        if(maxCapacity <= 0) {

        }
        this.maxCapacity = maxCapacity;
        this.map = new LocallyHashMap<String, V>(maxCapacity);
        mutex = this;
    }

    /**
     * 默认
     * @author ningyu
     * @date 2013-4-22 下午5:34:39
     */
    public LocalStorage() {
        this.map = new LocallyHashMap<String, V>(maxCapacity);
        mutex = this;
    }

    @Override
    public V get(String key) {
        synchronized (mutex) {
            if(map.containsKey(key)) {
                V value = map.get(key);
                if(value == null) {
                    //key存在，value为null
                    return null;
                }
                return value;
            } else {
                //key不存在
                return null;
            }
        }
    }


    @Override
    public HashMap<String,V> getTemplate() {
        return map;
    }



    @Override
    public void set(String key, V value) {
        synchronized (mutex) {
            //传入的key不能为空
            Validate.notEmpty(key);
            map.put(key, value);
        }
    }

    @Override
    public void set(String key, V value, int expire) {
        synchronized (mutex) {
            Validate.notEmpty(key);
            if(expire <= 0) {
                map.put(key, value);
            }else{
                map.put(key, value, expire);
            }
        }
    }

    @Override
    public void remove(String key) {
        synchronized (mutex) {
            //传入的key不能为空
            Validate.notEmpty(key);
            map.remove(key);
        }
    }

    @Override
    public void removeMulti(String... keys) {
        synchronized (mutex) {
            //传入的keys不能为空
            Validate.notEmpty(keys);
            if(keys.length <= 0) {
                return;
            }
            for(int i=0;i<keys.length;i++) {
                map.remove(keys[i]);
            }
        }
    }

    @Override
    public void setEntityClass(Class<V> entityClass) {
        this.entityClass = entityClass;
    }

}
