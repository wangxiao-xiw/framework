package com.hn658.${projectName}.service;

public interface IAuthentication {
	/**
	 * 验证用户登录信息
	 * @param loginAccount
	 * @param userDBPassword
	 * @param requestPassword
	 * @return
	 */
	public boolean authenticate(String loginAccount, String userDBPassword, String requestPassword);
	
}
