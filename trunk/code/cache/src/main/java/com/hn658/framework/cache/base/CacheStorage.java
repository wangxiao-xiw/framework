package com.hn658.framework.cache.base;

import java.lang.annotation.*;

/**
 * Created by baihai on 2015/10/27.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheStorage {

    /**
     * 缓存类型,必须指定
     */
    CacheType type();

}
