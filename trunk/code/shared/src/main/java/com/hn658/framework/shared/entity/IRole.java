package com.hn658.framework.shared.entity;

import java.util.Collection;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:角色接口定义</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2015-10-16 ztjie 新增
* </div>  
********************************************
 */
public interface IRole  extends IEntity{
	
	/**
	 * 
	 * <p>获取角色对应的功能权限Url</p> 
	 * @author ztjie
	 * @date 2015-10-16 上午11:03:41
	 * @return
	 * @see
	 */
	public Collection<String> getFunctionUrls();
	
	/**
	 * 
	 * <p>获取角色对应的功能权限Id</p> 
	 * @author ztjie
	 * @date 2015-10-16 上午11:03:41
	 * @return
	 * @see
	 */
	public Collection<String> getFunctionIds();

}
