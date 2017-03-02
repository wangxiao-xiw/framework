package com.hn658.framework.cache.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 * 
 * 信息提供者
 * @author ztjie
 * @date 2014-7-11 下午4:11:24
 * @since
 * @version
 */
@Component("messageCache")
public class MessageCache implements InitializingBean {
    
    private Log logger = LogFactory.getLog(MessageCache.class);
    
    private Properties messageCache = new Properties();
    
    public String getMessage(String code){
    	return messageCache.getProperty(code, code);
    }

	@Override
	public void afterPropertiesSet() throws Exception {
        try {
        	//加载指定位置的properties文件
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath*:messages/*.properties");
            for (Resource resource : resources) {
                String path = resource.getURL().getPath();
                if (logger.isInfoEnabled()) {
                    logger.info("[Framework] add message bundle: " + path);
                }
                //取得文件名
                Properties properties = new Properties();
                InputStream in = resource.getInputStream();
                
                BufferedReader bf = new BufferedReader(new InputStreamReader(in));  
                try {
                    properties.load(bf);
                } finally {
                    in.close();
                }
                messageCache.putAll(properties);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
	}
	
}
