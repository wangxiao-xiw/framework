package com.hn658.${projectName}.service.impl;

import org.springframework.stereotype.Component;

import com.hn658.${projectName}.service.IAuthentication;
import com.hn658.framework.common.AbstractBusinessObject;
import com.hn658.framework.security.cryptography.Base64;
import com.hn658.framework.security.cryptography.Digests;

@Component("authenticationImpl")
public class AuthenticationImpl extends AbstractBusinessObject implements IAuthentication{

	@Override
	public boolean authenticate(String loginAccount, String userDBPassword,
			String requestPassword) {
		// 先对请求密码做散列，用户登录名做散列Salt值
		// 然后使用Base64编码
		String passwordWithCrypt = Base64.encodeBase64Binrary(
				Digests.sha1(requestPassword.getBytes(), loginAccount.getBytes()));
		return passwordWithCrypt.compareToIgnoreCase(userDBPassword) == 0;
	}
	
	public static void main(String[] args) {
		String str = Base64.encodeBase64Binrary(Digests.sha1("admin.base@hn658_2015".getBytes(),"admin.base@admin.com".getBytes()));
		System.out.println(str);
	}

}
