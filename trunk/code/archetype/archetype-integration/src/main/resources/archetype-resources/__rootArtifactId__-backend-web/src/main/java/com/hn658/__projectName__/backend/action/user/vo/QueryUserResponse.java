package com.hn658.${projectName}.backend.action.user.vo;

import java.util.List;

import com.hn658.${projectName}.common.entity.UserInfoEO;
import com.hn658.framework.common.AbstractServiceResponse;
import com.hn658.user.itf.vo.UserInfoDTO;

public class QueryUserResponse extends AbstractServiceResponse {

	private List<UserInfoDTO> userList;
	private UserInfoEO userInfo;
	private long totalCount;

	public List<UserInfoDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserInfoDTO> userList) {
		this.userList = userList;
	}

	public UserInfoEO getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoEO userInfo) {
		this.userInfo = userInfo;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
