package com.hn658.${projectName}.dao.redis;

import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.${projectName}.common.entity.TreeNode;

/**
 * 功能Redis DAO接口
 * @author ztjie
 *
 */
public interface IFunctionRedisDAO {
	/**
	 * 通过id查找功能
	 * @param id
	 * @return
	 */
	public FunctionEO findFunctionById(String id);
	
	/**
	 * 保存功能信息至Redis
	 * @param function
	 */
	public void save(FunctionEO functionInfo);
	
	/**
	 * 删除功能
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 
	 * <p>通过权限Uri，得到权限信息</p> 
	 * @author ztjie
	 * @date 2015-11-5 下午3:50:02
	 * @param functionUri
	 * @return
	 * @see
	 */
	public FunctionEO findFunctionByUri(String functionUri);

	/**
	 * 
	 * <p>获得ROOT权限节点对象</p>
	 * @author ztjie
	 * @date 2015-12-4 下午5:23:03
	 * @return
	 * @see
	 */
	public FunctionEO findRootFunction();

	public void saveRoot(FunctionEO fun);

	public TreeNode<FunctionEO, TreeNode> findAllTreeNode();

	public void saveAllTreeNode(TreeNode<FunctionEO, TreeNode> rootNode);
	
}
