package com.hn658.framework.logging.exception;

import com.hn658.framework.shared.exception.SystemException;


/**
 * 
 * Sender异常
 * @author ztjie
 * @date 2014-7-21 下午3:19:46
 * @since
 * @version
 */
public class SenderException extends SystemException {

    private static final long serialVersionUID = 8873955629722026248L;

    public SenderException(final String msg) {
        super(msg);
    }

    public SenderException(final String message, final Throwable cause) {
        super(message, cause);
    }

}