package com.hn658.${projectName}.dictionary.entity;


import java.util.Date;

import com.hn658.framework.shared.entity.BaseEntity;

/**
 * 数据字典分类Entity
 * @author ztjie
 * @version V1.0 2013-4-15
 */
public class DictionaryTypeEntity extends BaseEntity {

	private static final long serialVersionUID = -4131974876095821933L;
	// 分类名称
	private String typeName;
	// 分类别名
	private String typeAlias;
	// 上级分类编码
	private Long parentCode;
	// 是否为子节点
	private Boolean isLeaf;
	// 是否有效
	private Boolean isDeleted;

    private Date createDate;

    private Date modifyDate;
    // 版本号
    private Long versionNo;

    //关联信息
    private String refCode;

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeAlias() {
		return typeAlias;
	}
	public void setTypeAlias(String typeAlias) {
		this.typeAlias = typeAlias;
	}

    public Long getParentCode() {
        return parentCode;
    }

    public void setParentCode(Long parentCode) {
        this.parentCode = parentCode;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }
}
