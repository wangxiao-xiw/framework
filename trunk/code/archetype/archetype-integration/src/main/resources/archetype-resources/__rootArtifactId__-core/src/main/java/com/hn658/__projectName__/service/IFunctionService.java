package com.hn658.${projectName}.service;

import java.util.List;
import java.util.Map;

import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.exceptions.FunctionException;
import com.hn658.${projectName}.common.exceptions.RoleException;
import com.hn658.framework.dataaccess.pagination.GenericPage;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:功能查询业务逻辑</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-08-30 钟庭杰    新增
* </div>  
********************************************
 */
public interface IFunctionService {
	
	/**
	 * 
	 * <p>分页查询功能信息列表</p> 
	 * @author ztjie
	 * @date 2015-11-5 下午4:33:47
	 * @param queryMap
	 * @param limit
	 * @param start
	 * @param sortBy
	 * @param isAsc
	 * @return
	 * @see
	 */
	public GenericPage<FunctionEO> queryFunction(Map<String, Object> queryMap,
			int limit, int start, String sortBy, boolean isAsc);

	/**
	 * 
	 * <p>通过功能URL得到功能对象,先从缓存中获取，缓存中不存在，则从数据库获取</p> 
	 * @author ztjie
	 * @date 2015-11-5 下午4:37:47
	 * @param uri
	 * @return
	 * @throws FunctionException
	 * @see
	 */
	public FunctionEO queryFunctionByUri(String uri) throws FunctionException;
	
	/**
	 * 新增功能
	 * </p>下面几种情况将抛出FunctionException：
	 * <pre>
	 * *功能编码、名称、类型、父功能为空
	 * *权限类型与父功能不匹配
	 * *功能编码已经存在
	 * </pre>
	 * @author ztjie
	 * @date 2013-4-20 下午3:24:18
	 * @param entity
	 * @return
	 * @throws FunctionException 
	 * @see com.deppon.dpap.module.authorization.server.service.IFunctionService#insertFunction(com.FunctionEO.dpap.module.authorization.shared.domain.FunctionEntity)
	 */
	FunctionEO save(FunctionEO entity) throws FunctionException;
	
	/**
	 * 通过ID获得当前ID所对应的所有的子节点
	 * @param id
	 * @return
	 */
	List<FunctionEO>  queryAllChildFunctionById(Long id) throws FunctionException;
	
	/**
	 * 通过角色ID查询功能对象集合
	 * @param roleId
	 * @return
	 */
	List<FunctionEO> queryAllFunctionByRoleId(Long roleId) throws RoleException;
	
	
	/**
	 * 通过角色CODE查询功能CODE集合
	 * @param roleId
	 * @return
	 */
	List<Long> queryAllFunctionIdByRoleId(Long roleId) throws RoleException;
	
	/**
	 * <p>修改权限功能</p>
	 * <pre>
	 * 下面几种情况将抛出FunctionException：
	 * *功能编码、名称、类型、父功能为空
	 * *权限类型与父功能不匹配
	 * *功能已经被删除
	 * </pre>
	 * @author ztjie
	 * @date 2013-4-20 下午3:32:49
	 * @param entity
	 * @return
	 * @throws FunctionException 
	 */
	FunctionEO updateFunction(FunctionEO entity) throws FunctionException;
	
	/**
	 * 删除此功能，即设置active为'N'，如果有子节点则不能删除
	 * @author ztjie
	 * @date 2013-4-18 下午5:20:20
	 * @param functionCode
	 * @see
	 */
	void deleteFunction(Long id) throws FunctionException;

	/**
	 * 查询功能URI是否存在
	 * @author ztjie
	 * @date 2013-4-18 上午11:53:09
	 * @param functionCode
	 * @return true，存在； false，不存在
	 * @see
	 */
	boolean queryFunctionURIExited(String functionURI);

	/**
	 * 
	 * <p>通过角色ID，获得权限Uri</p> 
	 * @author ztjie
	 * @date 2015-12-4 下午12:09:15
	 * @param roleId
	 * @return
	 * @throws RoleException
	 * @see
	 */
	List<String> queryAllFunctionUriByRoleId(Long roleId) throws RoleException;

	/**
	 * 
	 * <p>获得ROOT权限节点对象</p> 
	 * @author ztjie
	 * @date 2015-12-4 下午5:21:26
	 * @return
	 * @see
	 */
	FunctionEO queryRootFunction();

	/**
	 * 通过用户ID，查询用户权限信息
	 * @param uid
	 * @return
	 */
	public List<FunctionEO> queryUserFunction(Long uid);


	/**
	 * 查询所有权限树信息
	 * @return
	 */
	TreeNode<FunctionEO, TreeNode> queryAllTreeNode();

	/**
	 * 返回无按钮的权限树信息
	 * @return
	 */
	TreeNode<FunctionEO, TreeNode> queryNoButtonTreeNode();

	/**
	 * 返回可选择的权限树信息
	 * @param selectedIds 已经选中的节点ID
	 * @return
	 */
	TreeNode<FunctionEO, TreeNode> queryCanCheckTreeNode(List<Long> selectedIds);

	/**
	 * 返回已经选择的权限树信息
	 * @param selectedIds
	 * @return
	 */
	TreeNode<FunctionEO, TreeNode> querySelectedTreeNode(List<Long> selectedIds);

	void flushCache(Long functionId);
}
