package com.hn658.framework.cache.local;

import com.hn658.framework.cache.base.AbstractCacheManager;
import com.hn658.framework.cache.base.ICache;

import java.util.Collection;

/**
 * Created by baihai on 2015/10/30.
 */
public class LocallyCacheManager extends AbstractCacheManager {

    private Collection<? extends ICache> caches;

    public void setCaches(Collection<? extends ICache> caches) {
        this.caches = caches;
    }

    @Override
    protected Collection<? extends ICache> loadCaches() {
        return this.caches;
    }
}
