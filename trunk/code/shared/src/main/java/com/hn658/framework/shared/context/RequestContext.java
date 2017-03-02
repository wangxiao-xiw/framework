package com.hn658.framework.shared.context;             

import com.hn658.framework.shared.utils.UUIDUtils;

/**
 * @Classname:RequestContext
 * @Description:请求的上下文信息
 * @author ztjie
 * @date 2011-4-6  下午06:21:56
 * 
 */
public final class RequestContext {
  
    private static ThreadLocal<RequestContext> context = new ThreadLocal<RequestContext>(){
        @Override
        protected RequestContext initialValue(){
            return new RequestContext();
        }
    };
	
	private String requestURL;
	
	private String requestId;
	
	private String ip;
	
	public String getRequestId() {
		return requestId;
	}

	private RequestContext(){
	    this.requestId = UUIDUtils.getUUID().toString();
	}
	
	public String getRequestURL() {
		return requestURL;
	}

	public String getIp() {
        return this.ip;
    }

	/**
	 * 
	 * @return
	 */
	public static RequestContext getCurrentContext() {
		return context.get();

	}

	/**
	 * 
	 * <p>设置请求上下文</p> 
	 * @author ztjie
	 * @date 2015-10-19 上午9:53:17
	 * @param requestURL 请求URL
	 * @see
	 */
	public static void setCurrentContext(String requestURL) {
	    setCurrentContext(requestURL,null);
	}
	
	/**
	 * 
	 * <p>设置请求上下文</p> 
	 * @author ztjie
	 * @date 2015-10-19 上午9:51:41
	 * @param requestURL 请求URL
	 * @param ip 请求IP
	 * @see
	 */
	public static void setCurrentContext(String requestURL,String ip) {
	    RequestContext requestContext = getCurrentContext();
        requestContext.requestURL = requestURL;
        requestContext.ip = ip;
	}
	
	/**
	 * 清楚ThreadLocal
	 * clean
	 * @return void
	 * @since:
	 */
	public static void clean(){	
	    context.remove();
	}
	
}
