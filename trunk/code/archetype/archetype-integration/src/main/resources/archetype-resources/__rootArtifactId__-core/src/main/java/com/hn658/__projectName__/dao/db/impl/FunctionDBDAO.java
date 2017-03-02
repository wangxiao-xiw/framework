package com.hn658.${projectName}.dao.db.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hn658.${projectName}.dao.db.IFunctionDBDAO;
import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.framework.dataaccess.mybatis.AbstractMyBatisDAO;

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
@Repository
public class FunctionDBDAO extends AbstractMyBatisDAO<FunctionEO, Long>  implements IFunctionDBDAO {

	@Override
	public List<FunctionEO> selectAllByRoleId(Long roleId) {
		return this.getSqlSession().selectList(this.getMapperNamespace() + ".selectAllByRoleId", roleId);
	}

	@Override
	public List<FunctionEO> selectUserFunction(Long uid) {
		return this.getSqlSession().selectList(this.getMapperNamespace()+".selectUserFunction",uid);
	}
	
	@Override
	public FunctionEO selectRootFunction() {
		return this.getSqlSession().selectOne(this.getMapperNamespace()+".selectRootFunction");
	}
	
	@Override
	public List<Long> selectUserIdByFunctionId(Long FunctionId) {
		return getSqlSession().selectList(this.getMapperNamespace()+".selectUserIdByFunctionId",FunctionId);
	}
}