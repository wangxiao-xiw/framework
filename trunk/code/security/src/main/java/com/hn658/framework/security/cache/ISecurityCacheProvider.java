package com.hn658.framework.security.cache;

import java.util.Set;

import com.hn658.framework.shared.entity.IFunction;
import com.hn658.framework.shared.entity.IRole;
import com.hn658.framework.shared.entity.IUser;


/**
 * 
 * 用户角色权限缓存接口
 * @author ztjie
 * @date 2015-10-16 下午4:13:16
 * @since
 * @version
 */
public interface ISecurityCacheProvider {

	/**
	 * 
	 * <p>通过访问的URL，获得权限对象</p> 
	 * @author ztjie
	 * @date 2015-10-16 下午4:54:43
	 * @param accessURL
	 * @return
	 * @see
	 */
	public IFunction getFunction(String accessURL);
	
	/**
	 * 
	 * <p>通过角色编码，获得角色对象</p> 
	 * @author ztjie
	 * @date 2015-10-19 上午10:22:29
	 * @param uid
	 * @return
	 * @see
	 */
	public IRole getRole(String roleCode);
	
	
	/**
	 * 
	 * <p>通过用户ID，获得用户对象</p> 
	 * @author ztjie
	 * @date 2015-10-19 上午10:22:29
	 * @param uid
	 * @return
	 * @see
	 */
	public IUser getUser(Long uid);
	
	/**
	 * 获取角色拥有的权限Id
	 * queryFunctionIds
	 * @return
	 * @return Set<String>
	 * @since:0.9
	 */
	public Set<Long> queryFunctionIds(Long roleId);
	
	/**
	 * 获取角色拥有的权限Uri
	 * queryFunctionUris
	 * @return
	 * @return Set<String>
	 * @since:0.9
	 */
	public Set<String> queryFunctionUris(Long roleId);

	/**
	 * 
	 * <p>通过用户ID，获得用户访问的URl列表</p> 
	 * @author ztjie
	 * @date 2015-12-4 下午12:25:10
	 * @param uid
	 * @return
	 * @see
	 */
	public Set<String> queryAccessUris(Long uid);
	
	/**
	 * 获取用户拥有的角色Id
	 * queryRoleIds
	 * @return
	 * @return Set<String>
	 * @since:0.9
	 */
	public Set<Long> queryRoleIds(Long uid);

}
