package com.hn658.${projectName}.${moduleName}.interceptor;

import java.util.List;

import com.hn658.framework.common.interceptor.AbstractInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 忽略AccessToken认证URL拦截器
 * 
 * @author ztjie
 * 
 */
public class IgnoreUrlInterceptor extends AbstractInterceptor {

	/**
	 * 日志输出
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(IgnoreUrlInterceptor.class);

	/**
	 * 忽略认证url列表
	 */
	private List<String> ignoreUrlsList;

	public IgnoreUrlInterceptor() {
		super(Phase.RECEIVE);
	}

	// 处理拦截器消息
	public void handle(Message message) {
		
		//清空当前线程上一次访问的 Session 数据（由于是第一个拦截器）
		//CurrentUserContext.clean();

        // 获取当前请求的路径
        String path = (String) message.get(Message.PATH_INFO);
        logger.debug("当前拦截的URL为 {}", path);

        // 如果存在忽视URL列表
        if (null != ignoreUrlsList && !ignoreUrlsList.isEmpty()) {
            // 如果当前访问路径在ignoreUrlsList中并且请求头不带签名信息则跳过检查（注意Url为小写）
            // 如果请求头中带有签名信息则继续进行签名及认证检查
            for(String ignoreUrl : ignoreUrlsList){
                if(path.toLowerCase().contains(ignoreUrl.toLowerCase())){
                    logger.debug("当前请求URL {}在IgnoreUrls列表中,跳过后续拦截器.", path);
                    // 从拦截器链中AccessTokenInterceptor拦截器之后开始(AccessTokenInterceptor是最后一个自定义拦截器)
                    //message.getInterceptorChain().doInterceptStartingAfter(message, AccessTokenInterceptor.class.getName());
                    return;
                }
            }
        }

	}

	public List<String> getIgnoreUrlsList() {
		return ignoreUrlsList;
	}

	public void setIgnoreUrlsList(List<String> ignoreUrlsList) {
		this.ignoreUrlsList = ignoreUrlsList;
	}


}