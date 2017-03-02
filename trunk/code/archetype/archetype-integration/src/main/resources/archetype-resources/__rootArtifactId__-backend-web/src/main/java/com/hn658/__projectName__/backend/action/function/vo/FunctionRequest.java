package com.hn658.${projectName}.backend.action.function.vo;

import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.framework.common.AbstractServiceRequest;

public class FunctionRequest extends AbstractServiceRequest {
	
	private Long id;
	
	private Boolean checkable;
	
	public Boolean getCheckable() {
		return checkable;
	}

	public void setCheckable(Boolean checkable) {
		this.checkable = checkable;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	private Long roleId;
	
	private FunctionEO function;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FunctionEO getFunction() {
		return function;
	}

	public void setFunction(FunctionEO function) {
		this.function = function;
	}
	
}
