package com.hn658.framework.security.tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:页面国际化与权限配置缓存</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1  2013-1-12 钟庭杰  新增
* 2  2013-5-29 钟庭杰  修改  修改isPermission的正则表达式，可以使KEY配置任何特殊字符
* </div>  
********************************************
 */
public class TagsCache {
	
	private static final Log logger = LogFactory
	.getLog(TagsCache.class);

	private static final String PERM_STRING = "isPermission\\(\\s*['\"]([^'\"]+)['\"]";
	
	private static TagsCache INSTANCE;
	
	//是否开启缓存，true开启，false未开启
	private boolean enableTagsCache = true;

	/**
	 * 保存所有缓存实例
	 */
	private final Map<String,Map<String,String>> caches = new ConcurrentHashMap<String, Map<String,String>>();

	/**
	 *  禁止从外部拿到实例
	  * 创建一个新的实例 TagsCache.
	  * @since 0.6
	 */
	private TagsCache() {
	}

	public void setEnableTagsCache(boolean enableTagsCache) {
		this.enableTagsCache = enableTagsCache;
	}

	public void init(){
		INSTANCE = this;
    }
	
	public static TagsCache getInstance() {
		if(INSTANCE == null){
			INSTANCE = new TagsCache();
		}
		return INSTANCE;
	}
	
	public boolean getEnableTagsCache(){
		if(TagsCache.getInstance()==null){
			return true;
		}else{
			return TagsCache.getInstance().enableTagsCache; 
		}
	}
	
	/**
	 * 根据得到模块内所有JS的国际化与权限信息
	 * getTagesInfo
	 * @param moduleName
	 * @return Map<String,String>
	 * @since:
	 */
	public Map<String, String> getTagesInfo(){
		StringBuilder urlSb = new StringBuilder();
		StringBuilder tagsIdBuffer = new StringBuilder("tags");
		String tagsId = tagsIdBuffer.toString();
		Map<String,String> tagsInfo = caches.get(tagsId);
		if (!getEnableTagsCache() || tagsInfo == null) {
			try {
				Resource[] resources = FileLoadUtil.getResourcesForServletpath("/app/" + "**/*.js");
	
				Set<String> urlSet = new HashSet<String>();
				if (resources != null && resources.length > 0) {
					Pattern permPattern = Pattern.compile(TagsCache.PERM_STRING);
					
					for (Resource resource : resources) {
						BufferedReader br = null;
						try {
							br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
							char[] charBuffer = new char[1];
							StringBuilder fileBuffer = new StringBuilder();
							while ((br.read(charBuffer)) > 0) {
								fileBuffer.append(charBuffer);
							}
							String fileString = fileBuffer.toString();
							Matcher pm = permPattern.matcher(fileString);
							while (pm.find()) {
								urlSet.add(pm.group(1));
							}
						} catch (Exception e) {
							logger.error(e.getMessage());
						} finally {
							if (br != null) {
								br.close();
							}
						}
	
					}
					for (String str : urlSet) {
						urlSb.append(str);
						urlSb.append(",");
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			tagsInfo = new HashMap<String, String>();
			
			if (urlSb.length() > 0) {
				tagsInfo.put("urls", urlSb.substring(0, urlSb.length() - 1));
			} else {
				tagsInfo.put("urls", "");
			}
			caches.put(tagsId, tagsInfo);
		}
		return tagsInfo;
	}
}