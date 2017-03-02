package com.hn658.${projectName}.backend.action.function.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hn658.${projectName}.service.IFunctionService;
import com.hn658.${projectName}.service.IRoleInfoService;
import com.hn658.${projectName}.backend.action.function.IFunctionAction;
import com.hn658.${projectName}.backend.action.function.vo.FunctionRequest;
import com.hn658.${projectName}.backend.action.function.vo.FunctionResponse;
import com.hn658.${projectName}.common.constants.*;
import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.exceptions.FunctionException;
import com.hn658.framework.common.AbstractBaseService;
import com.hn658.framework.common.IServiceResponse;
import com.hn658.framework.common.ResponseStatus;
import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.exception.BusinessException;

@Controller("functionAction")
@Path("function")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
public class FunctionAction extends AbstractBaseService implements
		IFunctionAction {

	@Autowired
	private IFunctionService functionService;
	
	@Autowired
	private IRoleInfoService roleInfoService;
	
	@SuppressWarnings("rawtypes")
	@Path("queryAllChildFunctionById")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@POST
	public IServiceResponse queryAllChildFunctionById(@FormParam(value = "id") String id) {
		FunctionResponse response = new FunctionResponse();
		try {
			if (StringUtils.isBlank(id)){
				throw new FunctionException(FunctionException.FUNCTION_PARENT_ID_NULL);
			}
			Long parentId = Long.valueOf(id);
			List<FunctionEO> functions = functionService.queryAllChildFunctionById(parentId);
			List<TreeNode> nodes = getTreeNodes(functions);
			response.setNodes(nodes);
			this.success(response);
		} catch (BusinessException e) {
			this.error(response, e);
		}
		return response;
	}
	
	@SuppressWarnings("rawtypes")
	@Path("loadFunctionChooseAllTree")
	@POST
	public IServiceResponse loadFunctionChooseAllTree(FunctionRequest request) {
		FunctionResponse response = new FunctionResponse();
		try {
			TreeNode node = roleInfoService.queryCheckedTreeNode(request.getRoleId(), request.getCheckable());
			response.setNode(node);
			this.success(response);
		} catch (BusinessException e) {
			this.error(response, e);
		}
		return response;
	}

	@Path("queryFunctionList")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@POST
	public IServiceResponse queryFunctionList(
			@FormParam(value = "parentId") String parentId,
			@FormParam(value = "limit") int limit,
			@FormParam(value = "start") int start) {
		FunctionResponse response = new FunctionResponse();
		if (StringUtils.isBlank(parentId)){
			throw new FunctionException(FunctionException.FUNCTION_PARENT_ID_NULL);
		}
		try {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("parentId", Long.valueOf(parentId));
			queryMap.put("isDeleted", ${ConstantClassName}.NO);
			GenericPage<FunctionEO> page = functionService.queryFunction(queryMap, limit, start, "display_order", true);
			response.setFunctionList(page.getData());
			response.setTotalCount(page.getTotalCount());
			this.success(response);
		} catch (BusinessException e) {
			this.error(response, e);
		}
		return response;
	}

	@Path("addFunction")
	@POST
	public IServiceResponse addFunction(FunctionRequest request) {
		FunctionResponse response = new FunctionResponse();
		try {
			functionService.save(request.getFunction());
			this.success(response);
		} catch (BusinessException e) {
			this.error(response, e);
		}
		return response;
	}

	@Path("modifyFunction")
	@POST
	public IServiceResponse modifyFunction(FunctionRequest request) {
		FunctionResponse response = new FunctionResponse();
		try {
			functionService.updateFunction(request.getFunction());
			this.success(response);
		} catch (BusinessException e) {
			this.error(response, e);
		}
		return response;
	}

	@Path("deleteFunction")
	@POST
	public IServiceResponse deleteFunction(FunctionRequest request) {
		FunctionResponse response = new FunctionResponse();
		try {
			functionService.deleteFunction(request.getId());
			this.success(response);
		} catch (BusinessException e) {
			this.error(response, e);
			if (e.getErrorMsg() == e.getErrorCode()) {
				response.setResponseStatus(new ResponseStatus(e.getErrorCode(),
						e.getErrorMsg()));
			}
		}
		return response;
	}

	@SuppressWarnings("rawtypes")
	private List<TreeNode> getTreeNodes(List<FunctionEO> list) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for (FunctionEO entity : list) {
			TreeNode<FunctionEO, TreeNode> treeNode = new TreeNode<FunctionEO, TreeNode>();
			treeNode.setId(entity.getId());
			treeNode.setText(entity.getName());
			treeNode.setLeaf(entity.getLeaf());
			treeNode.setParentId(entity.getParentId());
			treeNode.setChildren(null);
			treeNode.setEntity(entity);
			nodes.add(treeNode);
		}
		return nodes;
	}
}
