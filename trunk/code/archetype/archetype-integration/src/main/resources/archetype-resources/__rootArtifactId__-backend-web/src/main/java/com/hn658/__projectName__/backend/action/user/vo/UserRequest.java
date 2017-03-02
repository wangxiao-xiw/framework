package com.hn658.${projectName}.backend.action.user.vo;

import java.util.List;

import com.hn658.framework.common.AbstractServiceRequest;

public class UserRequest extends AbstractServiceRequest {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private List<Long> appIds;

	public List<Long> getAppIds() {
		return appIds;
	}

	public void setAppIds(List<Long> appIds) {
		this.appIds = appIds;
	}

	private List<Long> roleIds;

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	
}
