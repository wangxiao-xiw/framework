package com.hn658.${projectName}.dao.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hn658.${projectName}.dao.db.IRoleInfoDBDAO;
import com.hn658.${projectName}.common.constants.*;
import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.framework.dataaccess.mybatis.AbstractMyBatisDAO;

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

@Repository
public class RoleInfoDBDAO extends AbstractMyBatisDAO<RoleInfoEO, Long> implements IRoleInfoDBDAO {

	/**
	 * 保存角色权限信息
	 * @param roleId
	 * @param functionId
	 */
	@Override
	public void insertRoleFunction(Long roleId, Long functionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("functionId", functionId);
		getSqlSession().insert(this.getMapperNamespace()+".insertRoleFunction", map);
	}

	/**
	 * 根据角色编码，删除权限角色表中的数据
	 * @param roleId
	 */
	@Override
	public void deleteFunctionRoleById(Long roleId) {
		getSqlSession().delete(this.getMapperNamespace()+".deleteFunctionRoleById", roleId);
	}

	/**
	 * 根据角色ID，删除授权用户角色表中的数据
	 * @param roleId
	 */
	@Override
	public void deleteUserAuthRoleById(Long roleId) {
		getSqlSession().delete(this.getMapperNamespace()+".deleteByRoleId", roleId);
	}

	/**
	 *查询用户待分配的角色
	 * @param userCode 待分配用户Code
	 * @return
	 */
	@Override
	public List<RoleInfoEO> getLeftRoles(Long userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("isDeleted",${ConstantClassName}.NO);
		List<RoleInfoEO> list = getSqlSession().selectList(this.getMapperNamespace()+".getLeftRoleByUserCode",map);
		return list;
	}

	/**
	  * 查询用户Code查询该用户已经分配的角色
	 * @param userCode 待分配用户Code
	 * @return
	 */
	@Override
	public List<RoleInfoEO> getAuthedRoles(Long userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("isDeleted", ${ConstantClassName}.NO);
		List<RoleInfoEO> list = getSqlSession().selectList(this.getMapperNamespace()+".getAuthedRoleByUserCode",map);
		return list;
	}
	
	/**
	 * 查询该用户可以授权的角色
	 * @param currentUserId 当前授权用户ID
	 */
	@Override
	public List<RoleInfoEO> getAllAuthRoles(Long currentUserId) {
		List<RoleInfoEO> list = getSqlSession().selectList(this.getMapperNamespace()+".getAllAuthRoleByUserId",currentUserId);
		return list;
	}
	
	/**
	 * <p>根据角色ID和functionId删除指定角色的权限</p> 
	 * @author ztjie
	 * @date 2013-4-26 下午2:23:34
	 * @param roleId 角色ID
	 * @see
	 */
	@Override
	public void deleteRoleFunctionById(Long roleId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleId", roleId);
		getSqlSession().delete(this.getMapperNamespace()+".deleteFunctionRoleById",
				map);
	}
	
	/**
	 * 
	 * <p>查询某角色权限是否存在</p> 
	 * @author 向延楷
	 * @date 2013-4-26 下午3:35:32
	 * @param roleId
	 * @param functionId
	 * @return
	 * @see
	 */
	@Override
	public boolean existRoleFunction(Long roleId, Long functionId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleId", roleId);
		map.put("functionId", functionId);
		Long count = (Long)getSqlSession().selectOne(this.getMapperNamespace()+".existRoleFunction", map);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean existRoleFunctionByRoleId(Long roleId) {
		Long count = (Long)getSqlSession().selectOne(this.getMapperNamespace()+".existRoleFunctionByRoleId",
				roleId);
		if(count>0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean existRoleUserByRoleId(Long roleId) {
		Long count = (Long)getSqlSession().selectOne(this.getMapperNamespace()+".existRoleUserByRoleId",
				roleId);
		if(count>0)
			return true;
		else
			return false;
	}

	
	@Override
	public List<Long> selectUserIdByRoleId(Long roleId) {
		return getSqlSession().selectList(this.getMapperNamespace()+".selectUserIdByRoleId",roleId);
	}
}