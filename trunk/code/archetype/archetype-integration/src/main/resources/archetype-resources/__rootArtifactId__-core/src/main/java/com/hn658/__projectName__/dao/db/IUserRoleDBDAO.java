package com.hn658.${projectName}.dao.db;

import java.util.List;

import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.framework.dataaccess.mybatis.IMyBatisBaseDAO;

public interface IUserRoleDBDAO extends IMyBatisBaseDAO<RoleInfoEO, Long>{

	/**
	 * 
	 * <p>插入用户分配的应用</p> 
	 * @author ztjie
	 * @date 2015-11-16 下午3:42:05
	 * @param uid
	 * @param roleId
	 * @see
	 */
	public void insertUserRole(Long uid, Long roleId);

	/**
	 * 
	 * <p>删除用户分配的角色信息</p> 
	 * @author ztjie
	 * @date 2015-11-16 下午3:45:12
	 * @param uid
	 * @see
	 */
	public void deleteByUid(Long uid);

	public List<RoleInfoEO> selectAuthedRoles(Long uid);

	public List<RoleInfoEO> selectUnAuthedRoles(Long uid);
	
}
