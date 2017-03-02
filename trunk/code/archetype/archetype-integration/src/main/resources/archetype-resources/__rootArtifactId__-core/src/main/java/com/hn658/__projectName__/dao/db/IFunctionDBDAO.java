package com.hn658.${projectName}.dao.db;

import java.util.List;

import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.framework.dataaccess.mybatis.IMyBatisBaseDAO;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:功能数据访问</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-08-30 钟庭杰    新增
* </div>  
********************************************
 */
public interface IFunctionDBDAO extends IMyBatisBaseDAO<FunctionEO, Long> {

	public List<FunctionEO> selectAllByRoleId(Long roleId);
	
	/**
	 * 根据用户id查询权限列表
	 */

	public List<FunctionEO> selectUserFunction(Long uid);

	/**
	 * 
	 * <p>查找根权限</p> 
	 * @author ztjie
	 * @date 2015-12-4 下午5:24:31
	 * @return
	 * @see
	 */
	public FunctionEO selectRootFunction();
	
	/**
	 * 根据权限id查询用户id
	 * @param functionId
	 * @return
	 */
	public List<Long> selectUserIdByFunctionId(Long functionId);
}
