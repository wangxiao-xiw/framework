package com.hn658.framework.shared.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:WebApplicationContext持有类</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1  2011-5-13  ztjie  新增
* </div>  
********************************************
 */
@Component
public class WebApplicationContextHolder implements ApplicationContextAware {
	
	private static volatile ApplicationContext context;
	
	private static final Object lock = new Object();
	
	/**
	 * 注入WebApplicationContext
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 * setApplicationContext
	 * @param applicationContext
	 * @throws BeansException
	 * @since:0.9
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
	    synchronized (lock) {
	        context = applicationContext;
        }
	}
	
	public static WebApplicationContext getWebApplicationContext() {
		return (WebApplicationContext)context;
	}

}
