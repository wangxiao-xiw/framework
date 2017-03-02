package com.hn658.${projectName}.common.entity;

import java.util.Date;

import com.hn658.framework.shared.entity.BaseEntity;
import com.hn658.framework.shared.entity.IFunction;

/**
 * 
 * 权限功能表对象实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-4-8 下午5:38:36,content: </p>
 * @author ztjie
 * @date 2013-4-9 13:40:00
 * @since
 * @version
 */
public class FunctionEO extends BaseEntity implements IFunction {
	
	private static final long serialVersionUID = 8204215052602820708L;
	
	//父功能
	private Long parentId;

	//功能名称
	private String functionName;

	//功能入口URI
	private String uri;

	//功能层次
	private Integer functionLevel;

	//是否有效
	private Boolean validFlag;
	
	//显示顺序
	private Integer displayOrder;
	
	//是否叶子节点
	private Boolean leaf;
	
	//功能类型
	private Integer functionType;
	
	//功能描述
	private String functionDesc;
	
	//创建时间
	private Date createTime;

	//最后修改时间
	private Date lastUpdateTime;

	//是否删除
	private Boolean isDeleted;

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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getFunctionType() {
		return functionType;
	}

	public void setFunctionType(Integer functionType) {
		this.functionType = functionType;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Integer getFunctionLevel() {
		return functionLevel;
	}

	public void setFunctionLevel(Integer functionLevel) {
		this.functionLevel = functionLevel;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String getUri() {
		return this.uri;
	}

	@Override
	public String getFunctionCode() {
		return this.getId().toString();
	}

	@Override
	public Boolean getValidFlag() {
		return validFlag;
	}

	@Override
	public String getName() {
		return this.functionName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public void setValidFlag(Boolean validFlag) {
		this.validFlag = validFlag;
	}

}
