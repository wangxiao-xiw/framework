package com.hn658.${projectName}.common.exceptions;

import com.hn658.framework.shared.exception.SystemException;


public class FilterException extends SystemException {

	private static final long serialVersionUID = 4858857865102000835L;

	public static final String NoDeviceId = "hn658.filter.NoDeviceId";
	public static final String NoAppId = "hn658.filter.NoAppId";
	public static final String NoToken = "hn658.filter.NoToken";
	public static final String InvalidToken = "hn658.filter.InvalidToken";
	public static final String NoUid = "hn658.filter.NoUid";
	public static final String NoAuthKey = "1031";
	public static final String NoSign = "hn658.filter.NoSign";
	public static final String NoTimestamp = "hn658.filter.NoTimestamp";
	public static final String InvalidTimestamp = "hn658.filter.InvalidTimestamp";
	public static final String InvalidTimestampFormat = "hn658.filter.InvalidTimestampFormat";
	public static final String InvalidIdentityKey = "hn658.filter.InvalidIdentityKey";
	public static final String SignError = "hn658.filter.SignError";
	public static final String SignException = "hn658.filter.SignException";
	public static final String InValidSignature = "hn658.filter.InValidSignature";
	public static final String WrongDeviceType = "hn658.filter.WrongDeviceType";
	public static final String WrongOperationType = "hn658.filter.WrongOperationType";

	public FilterException(String errorCode, String... args) {
		super(errorCode, args);
	}

	public FilterException(String errorCode, Throwable t) {
		super(errorCode, t);
	}

}
