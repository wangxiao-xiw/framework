package com.hn658.framework.cache.base;



import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by baihai on 2015/10/29.
 */
public abstract class AbstractCacheManager implements ICacheManager,InitializingBean {



    /*public CacheManager(){
    }

    private static class CacheMangerInstance{
        private static final CacheManager instance = new CacheManager();
    }

    public static CacheManager getInstance(){
        return CacheMangerInstance.instance;
    }*/

    private final ConcurrentMap<String, ICache> cacheMap = new ConcurrentHashMap<String, ICache>();


    private Set<String> cacheNames = new LinkedHashSet<String>();


    @Override
    public void afterPropertiesSet() throws Exception {
        Collection<? extends ICache> caches = loadCaches();
        Assert.notEmpty(caches, "loadCaches 返回的缓存实例为空!");
        this.cacheMap.clear();

        // preserve the initial order of the cache names
        for (ICache cache : caches) {
            this.cacheMap.put(cache.getCacheId(), cache);
            this.cacheNames.add(cache.getCacheId());
        }
    }

    @Override
    public <K, V> ICache<K, V> getCache(String name) {
        return cacheMap.get(name);
    }

    @Override
    public Collection<String> getCacheNames() {
       return Collections.unmodifiableSet(this.cacheNames);
    }

    public <K, V> void registerCacheProvider(ICache<K, V> cache) {
        String cacheId = cache.getCacheId();
        cacheMap.putIfAbsent(cacheId,cache);
        cacheNames.add(cacheId);
    }


    protected abstract Collection<? extends ICache> loadCaches();

}
