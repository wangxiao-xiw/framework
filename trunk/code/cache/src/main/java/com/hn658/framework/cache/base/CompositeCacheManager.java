package com.hn658.framework.cache.base;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by baihai on 2015/10/30.
 */
public class CompositeCacheManager implements ICacheManager,InitializingBean{


    private List<ICacheManager> cacheManagers;


    public void setCacheManagers(Collection<ICacheManager> cacheManagers) {
        Assert.notEmpty(cacheManagers, "cacheManagers 缓存集合为空!");
        this.cacheManagers = new ArrayList<ICacheManager>();
        this.cacheManagers.addAll(cacheManagers);
    }

    @Override
    public <K, V> ICache<K, V> getCache(String name) {
        for (ICacheManager cacheManager : this.cacheManagers) {
            ICache cache = cacheManager.getCache(name);
            if (cache != null) {
                return cache;
            }
        }
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        List<String> names = new ArrayList<String>();
        for (ICacheManager manager : this.cacheManagers) {
            names.addAll(manager.getCacheNames());
        }
        return Collections.unmodifiableList(names);
    }

    @Override
    public void afterPropertiesSet() throws Exception {}
}
