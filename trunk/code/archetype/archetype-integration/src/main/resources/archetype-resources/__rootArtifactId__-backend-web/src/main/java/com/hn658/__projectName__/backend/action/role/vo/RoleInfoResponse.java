package com.hn658.${projectName}.backend.action.role.vo;

import java.util.List;

import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.framework.common.AbstractServiceResponse;

public class RoleInfoResponse extends AbstractServiceResponse {

	private RoleInfoEO roleInfo;

	private List<RoleInfoEO> roleInfoList;

	private Long totalCount;

	public RoleInfoEO getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RoleInfoEO roleInfo) {
		this.roleInfo = roleInfo;
	}

	public List<RoleInfoEO> getRoleInfoList() {
		return roleInfoList;
	}

	public void setRoleInfoList(List<RoleInfoEO> roleInfoList) {
		this.roleInfoList = roleInfoList;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
