package com.hn658.${projectName}.dao.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hn658.${projectName}.dao.db.IUserRoleDBDAO;
import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.framework.dataaccess.mybatis.AbstractMyBatisDAO;

@Repository
public class UserRoleDBDAOImpl extends AbstractMyBatisDAO<RoleInfoEO, Long> implements IUserRoleDBDAO{

	@Override
	public List<RoleInfoEO> selectAuthedRoles(Long uid) {
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("uid", uid);
		queryParam.put("isDeleted", false);
		List<RoleInfoEO> authedApps = this.selectByStatement(".selectAuthedRoles", queryParam, "name", true);
		return authedApps;
	}

	@Override
	public List<RoleInfoEO> selectUnAuthedRoles(Long uid) {
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("uid", uid);
		queryParam.put("isDeleted", false);
		List<RoleInfoEO> authedRoles = this.selectByStatement(".selectUnAuthedRoles", queryParam, "name", true);
		return authedRoles;
	}

	@Override
	public void insertUserRole(Long uid, Long roleId) {
		Map<String, Object> insertParam = new HashMap<String, Object>();
		insertParam.put("uid", uid);
		insertParam.put("roleId", roleId);
		this.getSqlSession().insert(this.getMapperNamespace() + ".insertUserRole", insertParam);
	}

	@Override
	public void deleteByUid(Long uid) {
		this.getSqlSession().delete(this.getMapperNamespace() + ".deleteByUid", uid);
	}
	
}
