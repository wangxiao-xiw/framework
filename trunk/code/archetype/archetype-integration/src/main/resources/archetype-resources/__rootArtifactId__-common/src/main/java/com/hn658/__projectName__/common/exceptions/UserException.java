package com.hn658.${projectName}.common.exceptions;

import com.hn658.framework.shared.exception.BusinessException;

public class UserException extends BusinessException {

	private static final long serialVersionUID = 4858857865102000835L;

	public static final String UserAppIdsEmptyWrong = "base.user.UserAppIdsEmptyWrong";
	public static final String UserRoleIdsEmptyWrong = "base.user.UserRoleIdsEmptyWrong";
	
	public static String UserEmptyWrong = "base.user.UserEmptyWrong";
	public static String RealNameEmpty = "base.user.RealNameEmpty";
	public static String NickNameEmpty = "base.user.NickNameEmpty";
	public static String ExitedNickName = "base.user.ExitedNickName";
	public static String UserTypeEmpty = "base.user.UserTypeEmpty";
	public static String AccountEmptyWrong = "base.user.AccountEmptyWrong";
	public static String ExitedAccount = "base.user.ExitedAccount";
	public static String UserQqEmpty = "base.user.UserQqEmpty";
	public static String UserWeiXinEmpty = "base.user.UserWeiXinEmpty";
	public static String UserPhoneNumberEmpty = "base.user.UserPhoneNumberEmpty";
	public static String UserPasswordEmpty = "base.user.UserPasswordEmpty";
	public static String OldPasswordWrong = "base.user.OldPasswordWrong";
	public static String UserIdEmptyWrong = "base.user.UserIdEmptyWrong";
	public static String UserNotExitedWrong = "base.user.UserNotExitedWrong";
	public static String UserIsEnableWrong = "base.user.UserIsEnableWrong";
	public static String UserIsDisableWrong = "base.user.UserIsDisableWrong";
	
	public UserException(String errorCode, String... args) {
		super(errorCode, args);
	}

	public UserException(String errorCode, Throwable t) {
		super(errorCode, t);
	}

}
