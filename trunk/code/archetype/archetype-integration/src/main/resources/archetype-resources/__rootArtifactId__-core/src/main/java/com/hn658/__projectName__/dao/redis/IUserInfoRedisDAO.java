package com.hn658.${projectName}.dao.redis;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.entity.UserInfoEO;

public interface IUserInfoRedisDAO {
	/**
	 * 通过Uid查找用户
	 * @param uid
	 * @return
	 */
	public UserInfoEO findUserByUid(Long uid);
	
	/**
	 * 通过帐号名查找用户
	 * @param loginUser
	 * @return
	 */
	UserInfoEO findUserByLoginAccount(String loginAccount);
	
	/**
	 * 保存用户信息至Redis
	 * @param userInfo
	 */
	public void saveUser(UserInfoEO userInfo);
	
	/**
	 * 设置用户Session
	 * @param uid
	 * @param authkey
	 */
	public void setUserAuthkey(Long uid, String authkey);
	
	/**
	 * 移除用户Session
	 * @param uid
	 */
	public void removeUserAuthkey(Long uid);
	
	/**
	 * 通过Session Authkey获取Uid
	 * @param authkey
	 * @return
	 */
	public Long getUidByAuthkey(String authkey);
	
	/**
	 * 删除用户
	 * @param uid
	 */
	public void deleteUser(Long uid);
	
	/**
	 * 
	 * <p>通过UID，查询authkey</p> 
	 * @author ztjie
	 * @date 2014-11-25 下午3:33:00
	 * @param uid
	 * @see
	 */
	public String getAuthkeyByUid(Long uid);

	/**
	 * 
	 * <p>通过用户ID，获得用户访问的URl列表</p> 
	 * @author ztjie
	 * @date 2015-12-4 下午1:35:28
	 * @param uid
	 * @return
	 * @see
	 */
	public Set<String> findUserAccessUris(Long uid);

	/**
	 * 
	 * <p>保存用户访问的URI列表</p> 
	 * @author ztjie
	 * @date 2015-12-4 下午1:36:33
	 * @param accessUris
	 * @see
	 */
	void saveUserAccessUris(Long uid, Set<String> accessUris);

	Set<Long> findUserAccessIds(Long uid);

	/**
	 * 
	 * <p>保存用户访问的ID列表</p> 
	 * @author ztjie
	 * @date 2015-12-4 下午9:08:22
	 * @param accessIds
	 * @see
	 */
	void saveUserAccessIds(Long uid, Set<Long> accessIds);

	List<TreeNode> findUserAccessTreeNodes(Long uid);


	/**
	 * 
	 * <p>保存用户菜单树</p> 
	 * @author ztjie
	 * @date 2015-12-4 下午9:10:28
	 * @param treeNodes
	 * @see
	 */
	void saveUserAccessTreeNodes(Long uid, LinkedHashSet<TreeNode> treeNodes);

	/**
	 * 删除用户权限相关缓存信息
	 * @param uid
	 */
	void destroyUserCache(Long uid);
}
