package com.hn658.framework.excel.exception;

/**
 * 
 *<p>描述：</p>
 *<p>表示当前调用的方法是不支持的
 *</p>
 *@ClassName: NotSupportException 
 *@author davidcun
 *@date 2014年8月4日 下午3:54:27
 */
public class NotSupportException extends RuntimeException {

	private static final long serialVersionUID = -360773212835305465L;

	public NotSupportException() {
		super();
	}

	public NotSupportException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotSupportException(String message) {
		super(message);
	}

	public NotSupportException(Throwable cause) {
		super(cause);
	}
}
