package com.hn658.${projectName}.backend.action.dictionary.vo;

import java.util.List;

import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.dictionary.dto.DictionaryDTO;
import com.hn658.${projectName}.dictionary.entity.DictionaryDataEntity;
import com.hn658.${projectName}.dictionary.entity.DictionaryTypeEntity;
import com.hn658.framework.common.AbstractServiceResponse;

/**
 * Created by baihai on 2015/7/31.
 */
public class DictionaryResponse extends AbstractServiceResponse {


    @SuppressWarnings("rawtypes")
	private List<TreeNode> nodes;

    private Boolean hasData;
    
    private List<DictionaryTypeEntity> taskTypeList;

	private List<DictionaryDataEntity> dictionaryDataList;

	private Long totalCount;

    private List<DictionaryDTO> dictionaryEntityList;

    private String jsonData;

	
	public List<DictionaryDataEntity> getDictionaryDataList() {
		return dictionaryDataList;
	}

	public void setDictionaryDataList(List<DictionaryDataEntity> dictionaryDataList) {
		this.dictionaryDataList = dictionaryDataList;
	}


    public Boolean getHasData() {
        return hasData;
    }

    public void setHasData(Boolean hasData) {
        this.hasData = hasData;
    }
   
    
    @SuppressWarnings("rawtypes")
	public List<TreeNode> getNodes() {
        return nodes;
    }

    @SuppressWarnings("rawtypes")
	public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

    public List<DictionaryDTO> getDictionaryEntityList() {
		return dictionaryEntityList;
	}

	public void setDictionaryEntityList(List<DictionaryDTO> dictionaryEntityList) {
		this.dictionaryEntityList = dictionaryEntityList;
	}

	public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

	public List<DictionaryTypeEntity> getTaskTypeList() {
		return taskTypeList;
	}

	public void setTaskTypeList(List<DictionaryTypeEntity> taskTypeList) {
		this.taskTypeList = taskTypeList;
	}



}
