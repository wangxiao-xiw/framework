package com.hn658.framework.logging.exception;

import com.hn658.framework.shared.exception.SystemException;

/**
 * 
 * 日志缓冲状态异常
 * @author ztjie
 * @date 2014-7-21 下午3:19:16
 * @since
 * @version
 */
public class BufferedStateException extends SystemException{
	/**
     * 
     */
    private static final long serialVersionUID = 5002210385764063432L;

    public BufferedStateException(String str) {
		super(str);
	}
}
