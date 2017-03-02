package com.hn658.framework.cache.base;

import com.hn658.framework.cache.local.LocallyHashMap;
import org.springframework.data.redis.core.*;

/**
 * 缓存混合模板
 * Created by baihai on 2015/10/27.
 */
public class MultiTemplate<K,V> {

    /**
     * 字符类型模板
     */
    public  StringRedisTemplate template =null ;

    /**
     * 字符类型操作
     */
    public  ValueOperations<String, String> valueOps = null;

    /**
     * set类型操作
     */
    public  SetOperations<String, String> setOps = null;

    /**
     * zSet类型操作
     */
    public  ZSetOperations<String, String> zSetOps = null;

    /**
     * List类型操作
     */
    public  ListOperations<String, String> listOps = null;

    /**
     * Hash类型操作
     */
    public  HashOperations<String,String,String> hashOps = null;

    /**
     * 存储的map
     */
    public LocallyHashMap<K,V> map = null;




}
