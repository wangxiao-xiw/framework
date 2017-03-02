package com.hn658.${projectName}.backend.action.function.vo;

import java.util.List;

import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.framework.common.AbstractServiceResponse;

public class FunctionResponse extends AbstractServiceResponse {

	private FunctionEO function;
	
	private TreeNode<FunctionEO, TreeNode> node;

	public TreeNode<FunctionEO, TreeNode> getNode() {
		return node;
	}

	public void setNode(TreeNode<FunctionEO, TreeNode> node) {
		this.node = node;
	}

	@SuppressWarnings("rawtypes")
	private List<TreeNode> nodes;

	private List<FunctionEO> functionList;

	private Long totalCount;

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

	public List<FunctionEO> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<FunctionEO> functionList) {
		this.functionList = functionList;
	}

	public FunctionEO getFunction() {
		return function;
	}

	public void setFunction(FunctionEO function) {
		this.function = function;
	}

}
