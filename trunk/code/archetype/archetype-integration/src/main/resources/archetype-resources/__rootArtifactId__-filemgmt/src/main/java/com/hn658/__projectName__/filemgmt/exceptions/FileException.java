package com.hn658.${projectName}.filemgmt.exceptions;

import com.hn658.framework.shared.exception.BusinessException;

public class FileException extends BusinessException {

	private static final long serialVersionUID = 4858857865102000835L;
	
	public static final String UploadFileError = "base.message.file.UploadFileError";
	
	public FileException(String errorCode, String... args) {
		super(errorCode, args);
	}

	public FileException(String errorCode, Throwable t) {
		super(errorCode, t);
	}

}
