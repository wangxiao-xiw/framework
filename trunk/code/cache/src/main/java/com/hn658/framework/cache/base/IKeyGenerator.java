package com.hn658.framework.cache.base;

/**
 * 缓存key的生成器，用于自定义扩展key的构造过程
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-4-18 上午8:46:29,content:TODO </p>
 * @author ningyu
 * @date 2013-4-18 上午8:46:29
 * @since
 * @version
 */
public interface IKeyGenerator {
    
	/** key分隔字符 */
	public static final String KEY_SEPERATOR = "_";

    /**
     * 自定义key构造过程
     * @author ningyu
     * @date 2013-4-22 下午5:55:33
     * @param key
     * @return
     * @see
     */
    String generate(String key);
    
}
