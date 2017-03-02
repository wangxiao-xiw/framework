package com.hn658.${projectName}.dao.db;

import java.util.List;

import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.framework.dataaccess.mybatis.IMyBatisBaseDAO;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:角色数据访问 </small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-08-30 钟庭杰    新增
* </div>  
********************************************
 */
public interface IRoleInfoDBDAO extends IMyBatisBaseDAO<RoleInfoEO, Long> {
	
	/**
	 * 保存角色权限信息
	 * @param roleId
	 * @param functionId
	 */
	void insertRoleFunction(Long roleId, Long functionId);
	
	/**
	 * 根据角色ID，删除权限角色表中的数据
	 * @param roleId
	 */
	void deleteFunctionRoleById(Long roleId);
	
	/**
	 * 根据角色ID，删除授权用户角色表中的数据
	 * @param roleId
	 */
	void deleteUserAuthRoleById(Long roleId);

	/**
	 * 查询用户待分配的角色
	 * @param userId 待分配用户Id
	 * @return
	 */
	List<RoleInfoEO> getLeftRoles(Long userId);
	
	/**
	 *  查询用户Code查询该用户已经分配的角色
	 * @param userId 已分配用户Id
	 * @return
	 */
	List<RoleInfoEO> getAuthedRoles(Long userId);

	/**
	 * 查询该用户可以授权的角色
	 * @param currentUserId 当前授权用户ID
	 */
	List<RoleInfoEO> getAllAuthRoles(Long currentUserId);
	
	/**
	 * <p>根据角色ID删除指定角色的权限</p> 
	 * @param roleId 角色ID
	 */
	public void deleteRoleFunctionById(Long roleId);
	
	/**
	 * <p>查询某角色权限是否存在</p> 
	 * @param roleId
	 * @param functionCode
	 */
	public boolean existRoleFunction(Long roleId,Long functionId);
	
	/**
	 * <p>查询某角色ID是否进行过权限配置</p> 
	 */
	public boolean existRoleFunctionByRoleId(Long roleId);
	
	/**
	 * <p>查询某角色ID是否进行过用户配置</p> 
	 */
	public boolean existRoleUserByRoleId(Long roleId);
	
	/**
	 * 根据角色id查询用户id
	 * @param roleId
	 * @return
	 */
	List<Long> selectUserIdByRoleId(Long roleId);
}
