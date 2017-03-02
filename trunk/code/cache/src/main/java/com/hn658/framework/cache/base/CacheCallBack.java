package com.hn658.framework.cache.base;



/**
 * 回调接口
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-4-19 下午1:37:28,content:TODO </p>
 * @author ningyu
 * @date 2013-4-19 下午1:37:28
 * @since
 * @version
 */
public interface CacheCallBack<V> {
    
    /**
     * 获取数据，给缓存使用
     * 
     * @return
     * @return V
     * @since:
     */
    V doGet();
    
}
