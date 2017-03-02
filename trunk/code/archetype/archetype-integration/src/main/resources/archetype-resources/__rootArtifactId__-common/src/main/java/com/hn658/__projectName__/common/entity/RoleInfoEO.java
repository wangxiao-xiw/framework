package com.hn658.${projectName}.common.entity;

import java.util.Collection;
import java.util.Date;

import com.hn658.framework.shared.entity.BaseEntity;
import com.hn658.framework.shared.entity.IRole;

/**
 * 角色对象实体
 * @author Administrator
 *
 */
public class RoleInfoEO extends BaseEntity implements IRole{
	
	private static final long serialVersionUID = -3091688011204362825L;
	
	//角色名称
	private String roleName;

	//角色描述
	private String roleDesc;
	
	//创建时间
	private Date createTime;

	//最后修改时间
	private Date lastUpdateTime;

	//是否删除
	private Boolean isDeleted;
	
	//功能对象url
	private Collection<String> functionUrls;
	
	//功能对象ID
	private Collection<String> functionIds;
	
	public String getRoleName() {
		return this.roleName;
	}
		 	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
		
	public String getRoleDesc() {
		return this.roleDesc;
	}
		 	
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Override
	public Collection<String> getFunctionUrls() {
		return functionUrls;
	}

	public void setFunctionUrls(Collection<String> functionUrls) {
		this.functionUrls = functionUrls;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public Collection<String> getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(Collection<String> functionIds) {
		this.functionIds = functionIds;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
