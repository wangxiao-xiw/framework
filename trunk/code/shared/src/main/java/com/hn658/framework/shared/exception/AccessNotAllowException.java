package com.hn658.framework.shared.exception;

import com.hn658.framework.shared.enums.CommonResponseCodes;


/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:访问拒绝异常</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2015-10-16 ztjie 新增
* </div>  
********************************************
 */
public class AccessNotAllowException extends BaseException {

	private static final long serialVersionUID = -5710513168282003818L;
	
	public static final String ERROR_CODE = CommonResponseCodes.AccessNotAllow.getCode();
	
	public AccessNotAllowException() {
		super(ERROR_CODE);
	}

	public AccessNotAllowException(String message, Throwable cause) {
		super(ERROR_CODE, cause);
		super.setErrorMsg(message);
	}

}
