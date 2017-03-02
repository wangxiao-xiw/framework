package com.hn658.framework.cache;

import com.hn658.framework.cache.base.BaseCache;
import com.hn658.framework.cache.base.CacheCallBack;
import com.hn658.framework.cache.base.IKeyGenerator;
import com.hn658.framework.cache.storage.ICacheStorage;
import com.hn658.framework.shared.utils.GenericsUtils;
import org.apache.commons.lang3.Validate;


/**
 * Created by baihai on 2015/10/16.
 */
public abstract class CacheSupport<V> extends BaseCache<String,V> implements IKeyGenerator {


    protected ICacheStorage<String,V> storage = null;


    @Override
    public V get(String key) {
        // 检查参数是否为null或者元素长度为0
        // 如果是抛出异常
        Validate.notBlank(key);
        return storage.get(generate(key));
    }

    @Override
    public V getRelationDB(String key,CacheCallBack<V> callBack) {
        // 检查参数是否为null或者元素长度为0
        // 如果是抛出异常
        Validate.notBlank(key);
        V value = null;
          value = storage.get(generate(key));
        if(value == null){
            //如果缓存中未查到具体的值
            //执行回调查找DB中的数据
            value = execute(callBack);
            if(value !=null){
                //如果DB中有数据，则更新缓存
                storage.set(key,value);
            }
        }
        return value;
    }

    @Override
    public void set(String key,V value){
        storage.set(key,value);
    }

    @Override
    public void set(String key, V value, int expire) {
    	storage.set(key, value, expire);
    }

    /**
     *  失效某个key对应的值
     * @param key
     */
    @Override
    public void invalid(String key) {
        storage.remove(generate(key));
    }

    /**
     * 失效多个key
     * @param keys
     */
    @Override
    public void invalidMulti(String ... keys) {
        String[] newKeys = new String[keys.length];
        for(int i=0;i<keys.length;i++) {
            newKeys[i] = generate(keys[i]);
        }
        storage.removeMulti(newKeys);
    }

    @Override
    public String generate(String key) {
        return key;
    }

    /**
     * 执行回调
     *
     * @return
     * @see
     */
    private V execute(CacheCallBack<V> callBack)  {
        return callBack.doGet();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() throws Exception {

        //注册缓存实例
//        AbstractCacheManager.getInstance().registerCacheProvider(this);

        //设置storage中的泛型的数据类型
        Class<V> entityClass = GenericsUtils.getSuperClassGenricType(getClass());
        storage.setEntityClass(entityClass);

        //初始化模板
        cacheTemolateInit(storage);
    }

    /**
     * 获取原生模板
     * @return
     */
    @Override
    public Object getNativeCache() {
        return storage.getTemplate();
    }
}
