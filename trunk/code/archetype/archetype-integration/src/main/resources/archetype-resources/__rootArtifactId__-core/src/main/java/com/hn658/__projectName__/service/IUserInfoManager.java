package com.hn658.${projectName}.service;

import java.util.List;
import java.util.Map;

import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.exceptions.UserException;
import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.exception.BusinessException;
import com.hn658.user.itf.vo.QueryUserInfoDTO;
import com.hn658.user.itf.vo.UserInfoDTO;

public interface IUserInfoManager {

	/**
	 * 
	 * <p>通过公司用户ID，查询公司用户信息</p> 
	 * @author ztjie
	 * @date 2015-11-11 下午2:34:56
	 * @param appId
	 * @return
	 * @see
	 */
	public UserInfoDTO findUserInfoById(Long userId) throws BusinessException;

	/**
	 * 
	 * <p>能过登录名，获取公司用户信息</p> 
	 * @author ztjie
	 * @date 2015-11-11 下午3:40:34
	 * @param loginAccount
	 * @return
	 * @throws BusinessException
	 * @see
	 */
	public UserInfoDTO findUserInfoByLoginAccount(String loginAccount)
			throws BusinessException;

	/**
	 * 
	 * <p>查询所有启用的公司用户信息</p> 
	 * @author ztjie
	 * @date 2015-11-12 下午9:24:36
	 * @return
	 * @see
	 */
	public List<UserInfoDTO> queryEnableUser();

	/**
	 * 
	 * <p>通过查询条件，分页查询公司用户信息</p> 
	 * @author ztjie
	 * @date 2015-11-12 下午9:33:55
	 * @param queryInfo
	 * @param limit
	 * @param start
	 * @param sortBy
	 * @param isAsc
	 * @return
	 * @see
	 */
	public GenericPage<UserInfoDTO> queryUser(QueryUserInfoDTO queryInfo, int limit,
			int start, String sortBy, boolean isAsc);

	/**
	 * <p>验证用户是否登入</p> 
	 * @param 登入用户
	 * @return
	 */
	public UserInfoDTO validateCookie(String appKey, String cookie);
	
	/**
	 * 
	 * <p>通过用户ID，获得用户菜单树</p> 
	 * @author ztjie
	 * @date 2015-12-4 下午11:34:40
	 * @param id
	 * @return
	 * @see
	 */
	public List<TreeNode> queryUserTreeNodes(Long id);
	
	/**
	 * 
	 * <p>缓存用户权限相关信息</p> 
	 * @author ztjie
	 * @date 2015-12-4 下午8:41:54
	 * @param uid
	 * @throws UserException
	 * @see
	 */
	public void loadUserCacheInfo(Long uid) throws UserException;

	public List<RoleInfoEO> queryAuthedRoles(Long uid);

	List<RoleInfoEO> queryUnAuthedRoles(Long uid);

	public void authUserRoles(Long id, List<Long> roleIds);

	GenericPage<UserInfoDTO> queryAppUser(Map<String,Object> queryMap,
			String appid, int limit, int start, String sortBy, boolean isAsc);

}