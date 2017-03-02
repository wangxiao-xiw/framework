package com.hn658.${projectName}.backend.action.user.vo;

import java.util.List;

import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.entity.UserInfoEO;
import com.hn658.framework.common.AbstractServiceResponse;

public class CurrentUserResponse extends AbstractServiceResponse{

	private UserInfoEO userInfo;
	
	private List<TreeNode> treeNodes;

	public List<TreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<TreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}

	public UserInfoEO getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoEO userInfo) {
		this.userInfo = userInfo;
	}
	
}
