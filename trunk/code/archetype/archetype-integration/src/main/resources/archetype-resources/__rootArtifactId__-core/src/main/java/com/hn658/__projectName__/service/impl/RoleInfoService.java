package com.hn658.${projectName}.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hn658.${projectName}.dao.db.IRoleInfoDBDAO;
import com.hn658.${projectName}.dao.redis.IFunctionRedisDAO;
import com.hn658.${projectName}.dao.redis.IUserInfoRedisDAO;
import com.hn658.${projectName}.service.IFunctionService;
import com.hn658.${projectName}.service.IRoleInfoService;
import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.exceptions.RoleException;
import com.hn658.${projectName}.common.exceptions.UserException;
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
@Service
public class RoleInfoService implements IRoleInfoService {

	@Autowired
	private IRoleInfoDBDAO roleDao;
	
	@Autowired
	private IFunctionService functionService;
	
	@Autowired
	private IFunctionRedisDAO functionRedisDao;
	
	@Autowired
	private IUserInfoRedisDAO userInfoRedisDAO;

	/**
	 * 保存角色对象
	 * @param role 角色对象
	 */
	@Transactional()
	public void save(RoleInfoEO role, List<Long> functionIds) throws RoleException{
		// 验证角色对象属性
		validateRoleField(role);
		role.setCreateTime(new Date());
		role.setLastUpdateTime(new Date());
		//设置生效
		role.setIsDeleted(false);
		roleDao.insert(role);
		
		//保存角色权限
		if(functionIds!=null && functionIds.size()>0){
			for(Long fid:functionIds){
				roleDao.insertRoleFunction(role.getId(), fid);
			}
		}
	}

	/**
	 * 删除角色对象
	 * 
	 * @param role 角色对象
	 */
	@Override
	@Transactional()
	public void delete(Long roleId) throws RoleException{
		// 验证角色ID是否为空
		if (roleId == null) {
			throw new RoleException(RoleException.ROLE_ID_NULL);
		}
		
		// 查询该角色是否存在用户关联记录
		if(this.roleDao.existRoleUserByRoleId(roleId)){
			throw new RoleException(RoleException.EXIST_ROLEUSER_RECORD);
		}
		
		// 删除功能角色表中的记录
		roleDao.deleteFunctionRoleById(roleId);
		// 删除角色表中的记录
		roleDao.deleteById(roleId);
		this.flushCache(roleId);
	}

	/**
	 * 
	 * <p>验证角色对象属性</p> 
	 * @author ztjie
	 * @date 2015-11-26 下午1:47:21
	 * @param role
	 * @throws RoleException
	 * @see
	 */
	private void validateRoleField(RoleInfoEO role) throws RoleException{
		// 验证角色对象
		if (role == null) {
			throw new RoleException(RoleException.ROLE_ID_NULL);
		}
		// 验证角色名
		if (role.getRoleName() == null || "".equals(role.getRoleName().trim())) {
			throw new RoleException(RoleException.ROLE_NAME_NULL);
		}
	}
	
	/**
	 * 
	 * <p>查询用户待分配的角色</p> 
	 * @author ztjie
	 * @date 2015-11-26 下午1:53:12
	 * @param userId
	 * @return 
	 * @see com.hn658.user.core.service.IRoleInfoService#queryLeftRoles(java.lang.Long)
	 */
	@Override
	public List<RoleInfoEO> queryLeftRoles(Long userId) {
		List<RoleInfoEO> roles = null;
		if(userId==null){
			roles = roleDao.selectAll();
		}else{
			roles = roleDao.getLeftRoles(userId);
		}
		return roles;
	}

	/**
	 * 
	 * <p>查询用户Id查询该用户已经分配的角色</p> 
	 * @author ztjie
	 * @date 2015-11-26 下午1:53:31
	 * @param userCode
	 * @return
	 * @throws RoleException
	 * @see
	 */
	@Override
	public List<RoleInfoEO> queryAuthedRoles(Long userId) throws RoleException{
		if (userId==null) {
			throw new UserException(UserException.UserIdEmptyWrong);
		}
		return roleDao.getAuthedRoles(userId);
	}
	
	/**
	 * <p>更新角色所对应的权限</p> 
	 * @author 向延楷
	 * @date 2013-4-26 上午10:02:28
	 * @param role
	 * @see
	 */
	@Override
	@Transactional()
	public void update(RoleInfoEO role, List<Long> functionIds) throws RoleException {
		// 验证角色对象
		if (role == null) {
			throw new RoleException(RoleException.ROLE_ID_NULL);
		}
		// 验证角色ID是否为空
		if (role.getId() == null) {
			throw new RoleException(RoleException.ROLE_ID_NULL);
		}
		role.setLastUpdateTime(new Date());
		//更新角色信息
		roleDao.update(role);
		// 处理删除某角色ID的权限ID
		roleDao.deleteRoleFunctionById(role.getId());
		// 处理新增的权限ID,新增之前判断是否存在
		if (functionIds != null && functionIds.size() > 0) {
			for (Long fCode : functionIds) {
				// 处理新增某角色ID的权限ID
				this.roleDao.insertRoleFunction(role.getId(), fCode);
			}
		}
		this.flushCache(role.getId());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TreeNode queryCheckedTreeNode(Long roleId, Boolean checkable) {
		List<Long> selectedIds = null;
		if (roleId!=null) {
			selectedIds = functionService.queryAllFunctionIdByRoleId(roleId);
		}
		TreeNode rootNode = null;
		if(checkable){
			rootNode = functionService.queryCanCheckTreeNode(selectedIds);
		}else{
			rootNode = functionService.querySelectedTreeNode(selectedIds);
		}
		return rootNode;
	}

	public GenericPage<RoleInfoEO> queryRoleInfo(Map<String, Object> queryMap,
			int limit, int start, String sortBy, boolean isAsc) {
		return roleDao.selectByMap(queryMap, limit, start, sortBy, isAsc);
	}

	@Override
	public IRole queryRoleInfoById(Long id) {
		// 验证角色ID是否为空
		if (id == null) {
			throw new RoleException(RoleException.ROLE_ID_NULL);
		}
		RoleInfoEO roleInfo = roleDao.selectById(id);
		return roleInfo;
	}
	
	private void flushCache(Long roleId){
		functionRedisDao.delete(roleId.toString());
		List<Long> userIds = roleDao.selectUserIdByRoleId(roleId);
		for(Long uid:userIds){
			userInfoRedisDAO.destroyUserCache(uid);
		}
	}

}