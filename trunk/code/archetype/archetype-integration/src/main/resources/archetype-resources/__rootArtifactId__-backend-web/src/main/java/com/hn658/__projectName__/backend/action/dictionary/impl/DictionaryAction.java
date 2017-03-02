package com.hn658.${projectName}.backend.action.dictionary.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hn658.${projectName}.backend.action.dictionary.IDictionaryAction;
import com.hn658.${projectName}.backend.action.dictionary.vo.DictionaryRequest;
import com.hn658.${projectName}.backend.action.dictionary.vo.DictionaryResponse;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.dictionary.entity.DictionaryDataEntity;
import com.hn658.${projectName}.dictionary.entity.DictionaryTypeEntity;
import com.hn658.${projectName}.dictionary.service.IDictionaryService;
import com.hn658.framework.common.AbstractBaseService;
import com.hn658.framework.common.IServiceResponse;
import com.hn658.framework.common.ResponseStatus;
import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.exception.BusinessException;

/**
 * 数据字典
 * Created by baihai on 2015/7/31.
 */

@Controller("dictionaryAction")
@Path("dictionary")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
public class DictionaryAction extends AbstractBaseService implements IDictionaryAction{

    @Autowired
    private IDictionaryService dictionaryManager;

    @SuppressWarnings("rawtypes")
	@Path("queryDictionaryList")
    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
    @Produces("application/json; charset=UTF-8")
    @POST
    public IServiceResponse queryFeedbackList(
            @FormParam(value="code") String parentCode
          ) {
        DictionaryResponse response = new DictionaryResponse();
        try {
             List<DictionaryTypeEntity> dictionaryTypeEntities =  dictionaryManager.getDirectChildrenTypes(parentCode);
             List<TreeNode> nodes = getTreeNodes(dictionaryTypeEntities);
            response.setNodes(nodes);
            this.success(response);
        } catch (BusinessException e) {
            this.error(response, e);
            if (e.getErrorMsg()==e.getErrorCode()) {
                response.setResponseStatus(new ResponseStatus(e.getErrorCode(), e.getErrorMsg()));
            }
        }
        return response;
    }
    
    /**
     * 是否含有数据
     * @return
     */
    @Path("addConsumeBond")
    @POST
    public IServiceResponse hasDictionaryData(DictionaryRequest dictionaryRequest){
        DictionaryResponse response = new DictionaryResponse();
        try {
            DictionaryTypeEntity dictionaryTypeEntity = new DictionaryTypeEntity();
            dictionaryTypeEntity.setTypeAlias(dictionaryRequest.getType());

            Boolean hasData = dictionaryManager.hasDictionaryData(dictionaryTypeEntity);
            response.setHasData(hasData);
            this.success(response);
        } catch (BusinessException e) {
            this.error(response, e);
            if (e.getErrorMsg()==e.getErrorCode()) {
                response.setResponseStatus(new ResponseStatus(e.getErrorCode(), e.getErrorMsg()));
            }
        }
        return response;
    }


    @Path("addDictionaryType")
    @POST
    public IServiceResponse addDictionaryType(DictionaryRequest dictionaryRequest) {
        DictionaryResponse response = new DictionaryResponse();
        try {
            dictionaryManager.addDictionaryType(dictionaryRequest.getDictionaryTypeEntity());
            this.success(response);
        } catch (BusinessException e) {
            this.error(response, e);
            if (e.getErrorMsg()==e.getErrorCode()) {
                response.setResponseStatus(new ResponseStatus(e.getErrorCode(), e.getErrorMsg()));
            }
        }
        return response;
    }
   
    /**
     * 修改数据字典分类
     */
    @Path("modifyDictionaryType")
    @POST
    public IServiceResponse modifyDictionaryType(DictionaryRequest dictionaryRequest) {
        DictionaryResponse response = new DictionaryResponse();
        try {
            dictionaryManager.modifyDictionaryType(dictionaryRequest.getDictionaryTypeEntity());
            this.success(response);
        } catch (BusinessException e) {
            this.error(response, e);
            if (e.getErrorMsg()==e.getErrorCode()) {
                response.setResponseStatus(new ResponseStatus(e.getErrorCode(), e.getErrorMsg()));
            }
        }
        return response;
    }

    @Path("abolishDictionaryType")
    @POST
    public IServiceResponse abolishDictionaryType(DictionaryRequest dictionaryRequest) {
        DictionaryResponse response = new DictionaryResponse();
        try {
            dictionaryManager.abolishDictionaryType(dictionaryRequest.getDictionaryTypeEntity());
            this.success(response);
        } catch (BusinessException e) {
            this.error(response, e);
            if (e.getErrorMsg()==e.getErrorCode()) {
                response.setResponseStatus(new ResponseStatus(e.getErrorCode(), e.getErrorMsg()));
            }
        }
        return response;
    }


    @Path("loadDictionaryByType")
    @POST
    public IServiceResponse loadDictionaryByType(DictionaryRequest dictionaryRequest) {
        DictionaryResponse response = new DictionaryResponse();
        try {
            if(dictionaryRequest!=null&&StringUtils.isNotBlank(dictionaryRequest.getType())){
                  String jsonData =  dictionaryManager.getDictionaryByType(dictionaryRequest.getType());
                  response.setJsonData(jsonData);
            }
            this.success(response);
        } catch (BusinessException e) {
            this.error(response, e);
            if (e.getErrorMsg()==e.getErrorCode()) {
                response.setResponseStatus(new ResponseStatus(e.getErrorCode(), e.getErrorMsg()));
            }
        }
        return response;
    }


    @SuppressWarnings("rawtypes")
	private List<TreeNode> getTreeNodes(List<DictionaryTypeEntity> list){
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        for (DictionaryTypeEntity entity : list) {
            TreeNode<DictionaryTypeEntity,TreeNode> treeNode = new TreeNode<DictionaryTypeEntity, TreeNode>();
            treeNode.setId(entity.getId());
            treeNode.setText(entity.getTypeName());
            treeNode.setLeaf(entity.getIsLeaf());
            treeNode.setParentId(entity.getParentCode());
            treeNode.setEntity(entity);
            treeNode.setChildren(null);
            //treeNode.setTypeAlias(entity.getTypeAlias());
            nodes.add(treeNode);
        }
        return nodes;
    }

    
    @Path("queryDictionaryData")
	 @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	 @Produces("application/json; charset=UTF-8")
	 @POST
	 public IServiceResponse queryDictionaryData(
			 @FormParam(value="code") String code,
			 @FormParam(value="limit") int limit, 
			 @FormParam(value="start") int start ){
		 DictionaryResponse response = new DictionaryResponse();
		 try{
			 HashMap<String,Object> queryParam=new HashMap<String,Object>();
			 if(StringUtils.isNotBlank(code)){
                 queryParam.put("dictType", code);
             }
			 GenericPage<DictionaryDataEntity> page=dictionaryManager.selectByMapPage(queryParam, limit, start, "id", false);
			 response.setDictionaryDataList(page.getData());
			 response.setTotalCount(page.getTotalCount());
			 this.success(response);
		 } catch (BusinessException e) {
		     this.error(response, e);
		     if (e.getErrorMsg()==e.getErrorCode()) {
		         response.setResponseStatus(new ResponseStatus(e.getErrorCode(), e.getErrorMsg()));
		     }
		 }
		 return response;
	 }
	
    @Path("addDictionaryData")
    @POST
    public IServiceResponse addDictionaryData(DictionaryRequest dictionaryRequest) {
        DictionaryResponse response = new DictionaryResponse();
        try {
            dictionaryManager.insertDictionaryData(dictionaryRequest.getDictionaryDataEntity());
            this.success(response);
        } catch (BusinessException e) {
            this.error(response, e);
            if (e.getErrorMsg()==e.getErrorCode()) {
                response.setResponseStatus(new ResponseStatus(e.getErrorCode(), e.getErrorMsg()));
            }
        }
        return response;
    }

    @Path("modifyDictionaryData")
    @POST
    public IServiceResponse modifyDictionaryData(DictionaryRequest dictionaryRequest) {
        DictionaryResponse response = new DictionaryResponse();
        try {
            dictionaryManager.updata(dictionaryRequest.getDictionaryDataEntity());
            this.success(response);
        } catch (BusinessException e) {
            this.error(response, e);
            if (e.getErrorMsg()==e.getErrorCode()) {
                response.setResponseStatus(new ResponseStatus(e.getErrorCode(), e.getErrorMsg()));
            }
        }
        return response;
    }

     @Path("abolishDictionaryData")
	 @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	 @Produces("application/json; charset=UTF-8")
	 @POST
	 public IServiceResponse abolishDictionaryData(
			 @FormParam(value="code") String code
			){
		 DictionaryResponse response = new DictionaryResponse();
		 try{
			 Long id = Long.parseLong(code);
			 dictionaryManager.deletedDictionaryData(id);
			 this.success(response);
		 } catch (BusinessException e) {
		     this.error(response, e);
		     if (e.getErrorMsg()==e.getErrorCode()) {
		         response.setResponseStatus(new ResponseStatus(e.getErrorCode(), e.getErrorMsg()));
		     }
		 }
		 return response;
	 }
}





