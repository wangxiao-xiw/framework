package com.hn658.framework.logging.logSender;

import java.util.List;
/**
 * 
 * 发日志接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-3-29 下午2:22:41,content:TODO </p>
 * @author ztjie
 * @date 2013-3-29 下午2:22:41
 * @since
 * @version
 */
public interface ILogSender {

	void send(List<Object> msg);
}
