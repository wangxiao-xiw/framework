package com.hn658.framework.shared.exception;

import com.hn658.framework.shared.enums.CommonResponseCodes;


public class AccessItfException extends BaseException {

	private static final long serialVersionUID = -8447576671797891073L;

	public static final String ERROR_CODE = CommonResponseCodes.AccessItfError.getCode();

	public AccessItfException() {
		super(ERROR_CODE);
	}

	public AccessItfException(String message, Throwable cause) {
		super(ERROR_CODE, cause);
		super.setErrorMsg(message);
	}
}
