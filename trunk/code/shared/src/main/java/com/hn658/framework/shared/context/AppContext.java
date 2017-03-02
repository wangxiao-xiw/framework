package com.hn658.framework.shared.context;


/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:应用上下文</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1  2015-10-16  ztjie  新增
* 
* </div>  
********************************************
 */
public abstract class AppContext {
	/**
	 * 应用名
	 */
	private final String appName;
	
	/**
	 * 静态资源地址
	 */
	private final String staticServerAddress;
	
	/**
	 * 上下文路径
	 * 
	 * @author ningyu
	 */
	private final String contextPath;
	
	/**
	 * 单例示例
	 */
	private static AppContext context;

	public String getAppName() {
		return appName;
	}
	
	public String getStaticServerAddress() {
		return staticServerAddress;
	}
	
	public String getContextPath() {
        return contextPath;
    }
	
	public static AppContext getAppContext() {
        return context;
    }

    public AppContext(String appName, String staticServerAddress, String contextPath) {
		this.appName = appName;
		this.staticServerAddress = staticServerAddress;
		this.contextPath = contextPath;
	}
	
	public static synchronized void initAppContext(String appName, String staticServerAddress, String contextPath) {
		if (context == null) {
			context = new AppContext(appName, staticServerAddress, contextPath) {};
		}
	}
}
