package com.hn658.${projectName}.service;

import java.util.List;
import java.util.Map;

import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.exceptions.RoleException;
import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.entity.IRole;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:角色业务逻辑服务层</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-08-30 钟庭杰    新增
* </div>  
********************************************
 */
public interface IRoleInfoService {

	/**
	 * 
	 * <p>分页查询所有的角色对象</p> 
	 * @author ztjie
	 * @date 2015-11-26 下午1:32:12
	 * @param queryMap
	 * @param limit
	 * @param start
	 * @param sortBy
	 * @param isAsc
	 * @return
	 * @see
	 */
	public GenericPage<RoleInfoEO> queryRoleInfo(Map<String, Object> queryMap,
			int limit, int start, String sortBy, boolean isAsc);
	
	/**
	 * 
	 * <p>保存角色对象</p> 
	 * @author ztjie
	 * @date 2015-11-26 下午1:32:57
	 * @param role
	 * @param functionIds
	 * @throws RoleException
	 * @see
	 */
	void save(RoleInfoEO role, List<Long> functionIds) throws RoleException;
	
	/**
	 * 
	 * <p>删除角色对象</p> 
	 * @author ztjie
	 * @date 2015-11-26 下午1:35:31
	 * @param roleId
	 * @throws RoleException
	 * @see
	 */
	void delete(Long roleId) throws RoleException;

	/**
	 * 
	 * <p>查询用户待分配的角色</p> 
	 * @author Think
	 * @date 2015-11-26 下午1:35:47
	 * @param userId
	 * @return
	 * @see
	 */
	List<RoleInfoEO> queryLeftRoles(Long userId);
	
	/**
	 * 
	 * <p>查询用户已经分配的角色</p> 
	 * @author ztjie
	 * @date 2015-11-26 下午1:35:59
	 * @param userId
	 * @return
	 * @throws RoleException
	 * @see
	 */
	List<RoleInfoEO> queryAuthedRoles(Long userId) throws RoleException;
	
	/**
	 * 
	 * <p>更新角色信息</p> 
	 * @author ztjie
	 * @date 2015-11-26 下午1:36:19
	 * @param roleInfo
	 * @param functionIds
	 * @see
	 */
	void update(RoleInfoEO roleInfo, List<Long> functionIds);
	
	/**
	 * 
	 * <p>获取角色相关的权限树</p> 
	 * @author ztjie
	 * @date 2015-11-26 下午1:38:04
	 * @param roleCode
	 * @param function
	 * @param state
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	TreeNode queryCheckedTreeNode(Long roleId, Boolean state);

	/**
	 * 
	 * <p>通过角色ID，获得角色对象</p> 
	 * @author ztjie
	 * @date 2015-12-4 上午11:59:53
	 * @param roleId
	 * @return
	 * @see
	 */
	public IRole queryRoleInfoById(Long roleId); 
}