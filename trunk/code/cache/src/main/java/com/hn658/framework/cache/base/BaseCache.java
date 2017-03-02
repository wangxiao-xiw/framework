package com.hn658.framework.cache.base;


import com.hn658.framework.cache.local.LocallyHashMap;
import com.hn658.framework.cache.storage.ICacheStorage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 缓存基类
 * Created by baihai on 2015/10/19.
 */
public abstract class BaseCache<K,V> extends MultiTemplate<K,V> implements ICache<K,V>, InitializingBean {


    /**
     * 初始化模板
     * @param storage
     */
    public void cacheTemolateInit(ICacheStorage storage){
        Class<?> storageClass  =  storage.getClass();
        CacheStorage  cacheStorage = storageClass.getAnnotation(CacheStorage.class);
        CacheType type = cacheStorage.type();
        switch (type){
            case Local:
                cacheLocalTemplateInit();
                break;
            case Redis:
                cacheRedisTemplateInit();
                break;
        }
    }


    /**
     * 初始化redis的各类模板
     */
    public void cacheRedisTemplateInit(){
        super.template = (StringRedisTemplate)getNativeCache();
        super.valueOps = super.template.opsForValue();
        super.hashOps = super.template.opsForHash();
        super.listOps = super.template.opsForList();
        super.setOps = super.template.opsForSet();
        super.zSetOps = super.template.opsForZSet();
    }

    /**
     * 初始化本地缓存的各类模板
     */
    @SuppressWarnings("unchecked")
    public void cacheLocalTemplateInit(){
        super.map = (LocallyHashMap<K,V>) getNativeCache();
    }


}
