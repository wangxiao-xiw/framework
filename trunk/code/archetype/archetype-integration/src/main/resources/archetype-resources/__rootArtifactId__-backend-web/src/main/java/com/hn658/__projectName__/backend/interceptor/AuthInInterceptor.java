package com.hn658.${projectName}.backend.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.hn658.base.util.auth.AuthCookieEditor;
import com.hn658.framework.common.interceptor.AbstractInterceptor;
import com.hn658.framework.logging.enums.LogCategory;
import com.hn658.framework.security.SecurityAccessor;
import com.hn658.framework.security.cache.ISecurityCacheProvider;

public class AuthInInterceptor extends AbstractInterceptor  implements InitializingBean{

	@Autowired
	private ISecurityCacheProvider securityCacheProvider;

	public AuthInInterceptor() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handle(Message message) {
		String path = (String) message.get(Message.PATH_INFO);
		path = path.substring(path.indexOf("/")+1);
		path = path.substring(path.indexOf("/"));
		if (logger.isDebugEnabled()) {
			logger.debug(LogCategory.SYSTEM, String.format("当前请求的URL地址为%s", path));
		}

		// 开始校验
		// 获取当前的http请求
		HttpServletRequest request = (HttpServletRequest) message
				.get(AbstractHTTPDestination.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) message
				.get(AbstractHTTPDestination.HTTP_RESPONSE);
		// 当前服务session中没有user对象,从cookie中初始化user到session中
		Cookie cookie = AuthCookieEditor.getCookie(request);
		if(cookie!=null){
			AuthCookieEditor.cookieToSession(cookie, request.getSession());
			// 设置当前请求上下文信息
			this.initContext(message, request, response);
			// 重新生成cookie或修改cookie中时间戳
			AuthCookieEditor.saveCookie(request, response);
		}
		//进行权限检查
		SecurityAccessor.checkURLAccessSecurity(path, true);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		SecurityAccessor.init(securityCacheProvider);
	}
}
