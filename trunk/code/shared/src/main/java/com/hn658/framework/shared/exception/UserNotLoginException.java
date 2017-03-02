package com.hn658.framework.shared.exception;

import com.hn658.framework.shared.enums.CommonResponseCodes;


public class UserNotLoginException extends BaseException {

	private static final long serialVersionUID = -8447576671797891073L;

	public static final String ERROR_CODE = CommonResponseCodes.UserNotLogin.getCode();

	public UserNotLoginException() {
		super(ERROR_CODE);
	}

	public UserNotLoginException(String message, Throwable cause) {
		super(ERROR_CODE, cause);
		super.setErrorMsg(message);
	}
}
