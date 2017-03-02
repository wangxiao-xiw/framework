package com.hn658.${projectName}.dictionary.entity;


import java.util.Date;

import com.hn658.framework.shared.entity.BaseEntity;

/**
 * 数据字典数据Entity
 * @author ztjie
 * @version V1.0 2013-4-15
 */
public class DictionaryDataEntity extends BaseEntity {

	private static final long serialVersionUID = -5261389566487182872L;
	// 分类名称
	private String dictType;
	// 数据排序序号
	private String valueOrder;
	// 数据value
	private String valueName;
	// 数据key
	private String valueCode;
	// 语言
	private String language;
	// 是否有效
	private Boolean isDeleted;
	// 备注
	private String noteInfo;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date modifyTime;
    // 关联code
    private String refCode;
	
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	
	public String getValueOrder() {
		return valueOrder;
	}
	public void setValueOrder(String valueOrder) {
		this.valueOrder = valueOrder;
	}
	
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	
	public String getValueCode() {
		return valueCode;
	}
	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getNoteInfo() {
		return noteInfo;
	}
	public void setNoteInfo(String noteInfo) {
		this.noteInfo = noteInfo;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }
}
