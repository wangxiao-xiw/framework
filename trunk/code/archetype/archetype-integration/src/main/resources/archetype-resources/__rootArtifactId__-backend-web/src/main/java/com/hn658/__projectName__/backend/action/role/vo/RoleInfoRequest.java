package com.hn658.${projectName}.backend.action.role.vo;

import java.util.List;

import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.framework.common.AbstractServiceRequest;

public class RoleInfoRequest extends AbstractServiceRequest {
	
	private Long id;
	
	private RoleInfoEO roleInfo;
	
	private List<Long> functionIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleInfoEO getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RoleInfoEO roleInfo) {
		this.roleInfo = roleInfo;
	}

	public List<Long> getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(List<Long> functionIds) {
		this.functionIds = functionIds;
	}

}
