package com.hn658.framework.shared.entity;


/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:功能接口定义</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2015-10-16 ztjie 新增
* </div>  
********************************************
 */
public interface IFunction extends IEntity{
	
	/**
	 * 
	 * @Title:getUri
	 * @Description:用户功能菜单的href
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getUri();
	
	/**
	 * 
	 * @Title:getFunctionCode
	 * @Description:功能菜单的的代码号：code
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getFunctionCode();
	
	/**
	 * 功能是否被启用
	 * getValidFlag
	 * @return true,启用；false,不启用
	 * @since:0.6
	 */
	public Boolean getValidFlag();
	
	/**
	 * 获取名称
	 * @author ztjie
	 * @date 2013-3-7 下午2:00:13
	 * @return
	 * @see
	 */
	public String getName();

}
