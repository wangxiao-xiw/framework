package com.hn658.framework.shared.exception;

import com.hn658.framework.shared.enums.CommonResponseCodes;



public final class UnknowException extends SystemException {
    
    private static final long serialVersionUID = -7547624267762545457L;
    
    public static final String ERROR_CODE = CommonResponseCodes.UnKnownError.getCode();
    
    public UnknowException() {
    	super(ERROR_CODE);
    }
    public UnknowException(String message, Throwable cause) {
    	super(ERROR_CODE, cause);
		super.setErrorMsg(message);
        this.setStackTrace(cause.getStackTrace());
    }
   
}
