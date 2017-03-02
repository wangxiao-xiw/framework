package com.hn658.framework.scheduling;
/**
 * 
 * 提供网格作业异常处理类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:平台开发小组,date:2013-1-22 下午12:58:56,content:TODO </p>
 * @author 平台开发小组
 * @date 2013-1-22 下午12:58:56
 * @since
 * @version
 */
public class GridJobException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GridJobException() {
        super();
    }

    public GridJobException(String message, Throwable cause) {
        super(message, cause);
    }

    public GridJobException(String message) {
        super(message);
    }

    public GridJobException(Throwable cause) {
        super(cause);
    }

}
