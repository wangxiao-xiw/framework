package com.hn658.framework.shared.entity;


/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:用户接口定义</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2015-10-19 ztjie 新增 
* </div>  
********************************************
 */
public interface IUser extends IEntity {
	
	/**
	 * 
	 * <p>得到用户的登录帐号</p> 
	 * @author ztjie
	 * @date 2014-3-2 下午2:05:08
	 * @return
	 * @see
	 */
	public String getLoginAccount();
}
