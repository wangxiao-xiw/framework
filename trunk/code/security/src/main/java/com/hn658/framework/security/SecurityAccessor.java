package com.hn658.framework.security;

import java.util.Set;

import com.hn658.framework.security.cache.ISecurityCacheProvider;
import com.hn658.framework.shared.context.UserContext;
import com.hn658.framework.shared.entity.IFunction;
import com.hn658.framework.shared.entity.IUser;
import com.hn658.framework.shared.exception.AccessNotAllowException;
import com.hn658.framework.shared.exception.UserNotLoginException;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:权限访问控制器</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1  2015-10-16 ztjie 新增
* </div>  
********************************************
 */
public final class SecurityAccessor {
	
	private static ISecurityCacheProvider cacheProvider;
    
    private SecurityAccessor() {}
    
    /**
     * 
     * <p>初始化权限访问类</p> 
     * @author ztjie
     * @date 2015-10-20 下午3:51:06
     * @param securityCacheProvider
     * @see
     */
    public static void init(ISecurityCacheProvider securityCacheProvider){
    	cacheProvider = securityCacheProvider;
    }
    
    /**
     * 校验权限
     * checkURLAccessSecurity
     * @param accessURL
     * @return void
     * @since:0.6
     */
	public static void checkURLAccessSecurity(String accessURL) {
		checkURLAccessSecurity(accessURL, true);
    }
	
    /**
     * 校验权限
     * checkURLAccessSecurity
     * @param accessURL
     * @return void
     * @since:0.6
     */
	public static void checkURLAccessSecurity(String accessURL, boolean ignoreUnstoredFunction) {
		
		IUser user = UserContext.getCurrentUser();
        if(user==null){
        	throw new UserNotLoginException();
        }
		
        // 去掉多余的'/'
        if (accessURL != null) {
        	accessURL = accessURL.replaceAll("[/]{2,}", "/");
        }
        IFunction function = cacheProvider.getFunction(accessURL);
        if (function == null) {
        	if (ignoreUnstoredFunction) {
        		return ;
        	}
        	
        	throw new AccessNotAllowException(); 
        }
        
        if(!function.getValidFlag()){
        	return;
        }
        Set<String> accessUris = cacheProvider.queryAccessUris(user.getId());
        
        // 是否拥有访问权限
        if (accessUris==null || !accessUris.contains(function.getUri())) {
        	throw new AccessNotAllowException();
        }
    }
    
    /**
     * 判断请求是否被允许
     * hasAccessSecurity
     * @param accessURL
     * @return
     * @return boolean
     * @since:0.7
     */
    public static boolean hasAccessSecurity(String accessURL) {
        try {
        	checkURLAccessSecurity(accessURL);
        	return true;
        } catch (Exception t) {
        	return false;
        }
    }
    
}
