package com.hn658.framework.cache.storage;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.hn658.framework.cache.base.CacheStorage;
import com.hn658.framework.cache.base.CacheType;
import com.hn658.framework.shared.utils.JsonMapper;

/**
 * Created by baihai on 2015/10/16.
 */
@CacheStorage(type = CacheType.Redis)
public class RedisStorage<V> implements ICacheStorage<String,V>{

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected  Class<V> entityClass;

    /**
     * 字符类型模板
     */
    protected StringRedisTemplate template;

    /**
     * 字符类型操作
     */
    protected ValueOperations<String, String> valueOps;


    private JsonMapper jsonMapper = new JsonMapper();

    public RedisStorage(){}

    @SuppressWarnings("unused")
    @PostConstruct
    private void afterInitialization(){
        this.valueOps = this.template.opsForValue();
    }

    /**
     * @return the template
     */
    public StringRedisTemplate getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }


    @Override
    public V get(String key) {
        return jsonMapper.fromJson(valueOps.get(key),entityClass);
    }

    @Override
    public void set(String key, V value) {
        Validate.notEmpty(key);
        valueOps.set(key,jsonMapper.toJson(value));
    }

    @Override
    public void set(String key, V value, int expire) {
        Validate.notEmpty(key);
        valueOps.set(key,jsonMapper.toJson(value));
        template.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        //key不能为空
        Validate.notEmpty(key);
        template.delete(key);
    }

    @Override
    public void removeMulti(String... keys) {
        //key不能为空
        Validate.notEmpty(keys);
        template.delete(Arrays.asList(keys));
    }

    @Override
    public void setEntityClass(Class<V> entityClass) {
        this.entityClass = entityClass;
    }
}
