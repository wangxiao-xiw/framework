package com.hn658.${projectName}.backend.interceptor;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

import com.hn658.framework.common.interceptor.AbstractInterceptor;

public class AuthOutInterceptor extends AbstractInterceptor {

	public AuthOutInterceptor() {
		super(Phase.SETUP_ENDING);
	}

	@Override
	public void handle(Message message) {
		//清理上下文
		this.cleanContext();
	}
}
