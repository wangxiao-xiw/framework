package com.hn658.${projectName}.backend.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hn658.framework.cache.message.MessageCache;
import com.hn658.framework.logging.Logger;
import com.hn658.framework.logging.LoggerFactory;
import com.hn658.framework.shared.utils.JsonMapper;

public abstract class AbstractServlet extends HttpServlet {

	private static final long serialVersionUID = -3281656069386617400L;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected JsonMapper mapper = JsonMapper.nonEmptyMapper();
	
	protected ApplicationContext ac;

	protected MessageCache messageCache;
	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ac = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		messageCache = ac.getBean(MessageCache.class);
	}
}
