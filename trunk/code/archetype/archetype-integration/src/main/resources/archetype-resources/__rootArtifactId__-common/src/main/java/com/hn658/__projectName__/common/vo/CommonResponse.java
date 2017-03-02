package com.hn658.${projectName}.common.vo;

import java.util.List;

import com.hn658.framework.common.AbstractServiceResponse;

public class CommonResponse extends AbstractServiceResponse {
	
	List<String> errorMsgs;//错误信息

	public CommonResponse(List<String> errorMsgs) {
		super();
		this.errorMsgs = errorMsgs;
	}

	public CommonResponse() {
		super();
	}

	public List<String> getErrorMsgs() {
		return errorMsgs;
	}

	public void setErrorMsgs(List<String> errorMsgs) {
		this.errorMsgs = errorMsgs;
	}

}
