package com.hn658.${projectName}.backend.action.dictionary.vo;

import com.hn658.${projectName}.dictionary.entity.DictionaryDataEntity;
import com.hn658.${projectName}.dictionary.entity.DictionaryTypeEntity;
import com.hn658.framework.common.AbstractServiceRequest;

/**
 * Created by baihai on 2015/7/31.
 */
public class DictionaryRequest extends AbstractServiceRequest {


    private String type;
    
    private String taskTypeDtaId;
    
    private DictionaryTypeEntity dictionaryTypeEntity;

    private DictionaryDataEntity dictionaryDataEntity;

    private String valueCode; //关键子
    
	private String taskTypeId;
    
	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaskTypeDtaId() {
		return taskTypeDtaId;
	}

	public void setTaskTypeDtaId(String taskTypeDtaId) {
		this.taskTypeDtaId = taskTypeDtaId;
	}

	public DictionaryTypeEntity getDictionaryTypeEntity() {
        return dictionaryTypeEntity;
    }

    public void setDictionaryTypeEntity(DictionaryTypeEntity dictionaryTypeEntity) {
        this.dictionaryTypeEntity = dictionaryTypeEntity;
    }

    public DictionaryDataEntity getDictionaryDataEntity() {
        return dictionaryDataEntity;
    }

    public void setDictionaryDataEntity(DictionaryDataEntity dictionaryDataEntity) {
        this.dictionaryDataEntity = dictionaryDataEntity;
    }

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public String getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
}
