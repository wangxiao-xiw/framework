package com.hn658.framework.compress;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 解压缩服务注册类
 * @author davidcun
 *
 */
public class CompressRegistry {

	private static final String propertiesLocation = "META-INF/compress.properties";
	
	private static final Log LOG = LogFactory.getLog(CompressRegistry.class);
	
	private static Map<String, ICompressor> compressCache = new HashMap<String, ICompressor>();
	
	static{
		init();
	}
	
	public static ICompressor getCompress(String compressType){
		return compressCache.get(compressType);
	}
	
	public static void registry(String compressType, ICompressor compress){
		compressCache.put(compressType, compress);
	}
	
	private static void registry(Properties prop){
		
		Set<Entry<Object, Object>> set = prop.entrySet();
		for (Entry<Object, Object> entry : set) {
			String cls = (String) entry.getValue();
			try {
				Class<?> clazz = Class.forName(cls);
				
				Object obj = clazz.newInstance();
				
				if ( ! (obj instanceof ICompressor)) {
					
					LOG.error(String.format("the class {%s} in compress.properties file must realize from %s",cls,ICompressor.class.getName()));
					
				}
				
				registry((String)entry.getKey(), (ICompressor)obj);
				
			} catch (ClassNotFoundException e) {
				LOG.error(String.format("can not fond class in compress.properties %s",cls),e);
			} catch (InstantiationException e) {
				LOG.error(String.format("the compress impl class can not be instanced %s",cls),e);
			} catch (IllegalAccessException e) {
				LOG.error(String.format("the compress impl class can not be instanced %s",cls),e);
			}
		}
	}
	
	private static void init(){
		
		Properties prop = new Properties();
		try {
			Enumeration<URL> urls = CompressRegistry.class.getClassLoader().getResources(propertiesLocation);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				InputStream is = url.openStream();
				Properties tmp = new Properties();
				tmp.load(is);
				prop.putAll(tmp);
				is.close();
			}
			
			registry(prop);
			
		} catch (IOException e) {
			LOG.error("load compress.properties error", e);
		}
		
	}
}
