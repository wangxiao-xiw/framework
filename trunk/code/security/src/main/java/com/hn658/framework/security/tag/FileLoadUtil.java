package com.hn658.framework.security.tag;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

import com.hn658.framework.shared.context.WebApplicationContextHolder;
/**
 * 
 * 提供文件操作的的工具类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-3-29 下午2:33:36,content:TODO </p>
 * @author ztjie
 * @date 2013-3-29 下午2:33:36
 * @since
 * @version
 */
public final class FileLoadUtil {
	
	public static Resource[] getResourcesForServletpath(String path) throws IOException{
		Resource[] resources = WebApplicationContextHolder.getWebApplicationContext().getResources(path);
		return resources;
	}
	
	public static InputStream getInputStreamForServletpath(String filePath) throws FileNotFoundException,IOException{
		Resource[] resources = WebApplicationContextHolder.getWebApplicationContext().getResources(filePath);
		if (resources == null || resources.length<1) {
			throw new FileNotFoundException("file '"+filePath+"' not found in this module");
		}
		InputStream  in = resources[0].getInputStream();
		return in;
	}
}
