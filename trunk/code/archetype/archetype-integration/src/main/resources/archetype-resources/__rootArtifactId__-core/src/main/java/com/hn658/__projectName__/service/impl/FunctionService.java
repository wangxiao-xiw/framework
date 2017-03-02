package com.hn658.${projectName}.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hn658.${projectName}.dao.db.IFunctionDBDAO;
import com.hn658.${projectName}.dao.redis.IFunctionRedisDAO;
import com.hn658.${projectName}.dao.redis.IUserInfoRedisDAO;
import com.hn658.${projectName}.service.IFunctionService;
import com.hn658.${projectName}.common.constants.*;
import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.enums.FunctionType;
import com.hn658.${projectName}.common.exceptions.FunctionException;
import com.hn658.${projectName}.common.exceptions.RoleException;
import com.hn658.framework.common.AbstractBusinessObject;
import com.hn658.framework.dataaccess.pagination.GenericPage;

/**
 * 
 * 功能查询业务逻辑
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-4-9 上午10:53:30,content: </p>
 * @author ztjie
 * @date 2013-4-9 上午10:53:30
 * @since
 * @version
 */
@Service
public class FunctionService extends AbstractBusinessObject implements IFunctionService {

	@Autowired
	private IFunctionDBDAO functionDao;
	
	@Autowired
	private IFunctionRedisDAO functionRedisDao;
	
	@Autowired
	private IUserInfoRedisDAO userInfoRedisDAO;
	
	/**
	 * 
	 * <p>分页查询所有的功能信息</p> 
	 * @author ztjie
	 * @date 2015-11-5 下午5:16:15
	 * @param queryMap
	 * @param limit
	 * @param start
	 * @param sortBy
	 * @param isAsc
	 * @return 
	 */
	@Override
	public GenericPage<FunctionEO> queryFunction(Map<String, Object> queryMap,
			int limit, int start, String sortBy, boolean isAsc) {
		return functionDao.selectByMap(queryMap, limit, start, sortBy, isAsc);
	}
	
	/**
	 * 获得ROOT权限节点对象
	 * @author ztjie
	 * @date 2013-4-9 下午2:06:03
	 * @param functionCode
	 * @return
	 * @throws FunctionException 
	 */
	@Override
	public FunctionEO queryRootFunction() {
		FunctionEO fun = functionRedisDao.findRootFunction();
		if(fun==null){
			fun = functionDao.selectRootFunction();
			functionRedisDao.saveRoot(fun);
		}
		return fun;
	}
	
	/**
	 * 通过功能URL得到功能对象,先从缓存中获取，缓存中不存在，则从数据库获取
	 * @author ztjie
	 * @date 2013-4-9 下午2:06:03
	 * @param functionCode
	 * @return
	 * @throws FunctionException 
	 */
	@Override
	public FunctionEO queryFunctionByUri(String uri) throws FunctionException {
		if (StringUtils.isBlank(uri)) {
			throw new FunctionException(FunctionException.FUNCTION_URI_NULL);
		}
		FunctionEO fun = functionRedisDao.findFunctionByUri(uri);
		if(fun==null){
			fun = functionDao.selectUnDeletedUniqueByProp("uri", uri);
			functionRedisDao.save(fun);
		}
		return fun;
	}
	
	/**
	 * 返回无按钮的权限树信息
	 * @return
	 */
	@Override
	public TreeNode<FunctionEO, TreeNode> queryNoButtonTreeNode() {
		TreeNode<FunctionEO, TreeNode> rootNode = queryAllTreeNode();
		rootNode = this.iteratorNoButtonTreeNode(rootNode);
		return rootNode;
	}
	
	private TreeNode<FunctionEO, TreeNode> iteratorNoButtonTreeNode(TreeNode<FunctionEO, TreeNode> treeNode){
		if(treeNode==null){
			return treeNode;
		}else{
			if(treeNode.getEntity().getFunctionType()==FunctionType.MENU.getType()){
				treeNode.setChildren(null);
				treeNode.setLeaf(true);
			}else{
				if(treeNode.getChildren()!=null&&treeNode.getChildren().size()>0){
					for(TreeNode<FunctionEO, TreeNode> childNode : treeNode.getChildren()){
						this.iteratorNoButtonTreeNode(childNode);
					}					
				}
			}
		}
		return treeNode;
	}
	
	/**
	 * 返回已经选择的权限树信息
	 * @return
	 */
	@Override
	public TreeNode<FunctionEO, TreeNode> querySelectedTreeNode(List<Long> selectedIds) {
		TreeNode<FunctionEO, TreeNode> rootNode = queryAllTreeNode();
		rootNode = this.iteratorSelectedTreeNode(rootNode, selectedIds);
		return rootNode;
	}
	
	private TreeNode<FunctionEO, TreeNode> iteratorSelectedTreeNode(TreeNode<FunctionEO, TreeNode> treeNode, List<Long> selectedIds){
		if(treeNode==null){
			return treeNode;
		}else{
			if(treeNode.getChildren()!=null&&treeNode.getChildren().size()>0){
				LinkedHashSet<TreeNode> selectTreeNode = new LinkedHashSet<TreeNode>();
				for(TreeNode<FunctionEO, TreeNode> childNode : treeNode.getChildren()){
					if(selectedIds!=null&&selectedIds.contains(childNode.getId())){
						selectTreeNode.add(childNode);
					}
					this.iteratorSelectedTreeNode(childNode, selectedIds);
				}
				treeNode.setChildren(selectTreeNode);
			}
		}
		return treeNode;
	}
	
	/**
	 * 返回可选择的权限树信息
	 * @return
	 */
	@Override
	public TreeNode<FunctionEO, TreeNode> queryCanCheckTreeNode(List<Long> selectedIds) {
		TreeNode<FunctionEO, TreeNode> rootNode = queryAllTreeNode();
		rootNode = this.iteratorCanCheckTreeNode(rootNode, selectedIds);
		return rootNode;
	}
	
	private TreeNode<FunctionEO, TreeNode> iteratorCanCheckTreeNode(TreeNode<FunctionEO, TreeNode> treeNode, List<Long> selectedIds){
		if(treeNode==null){
			return treeNode;
		}else{
			if(treeNode.getChildren()!=null&&treeNode.getChildren().size()>0){
				for(TreeNode<FunctionEO, TreeNode> childNode : treeNode.getChildren()){
					if(selectedIds!=null&&selectedIds.contains(childNode.getId())){
						childNode.setChecked(true);
					}else{
						childNode.setChecked(false);
					}
					this.iteratorCanCheckTreeNode(childNode, selectedIds);
				}					
			}
		}
		return treeNode;
	}
	
	/**
	 * 查询所有权限节点，并生成节点结构
	 * @author ztjie
	 * @date 2013-4-9 下午2:06:03
	 * @param functionCode
	 * @return
	 */
	@Override
	public TreeNode<FunctionEO, TreeNode> queryAllTreeNode() {
		TreeNode<FunctionEO, TreeNode> rootNode = functionRedisDao.findAllTreeNode();
		if(rootNode!=null){
			return rootNode;
		}
		FunctionEO root = this.queryRootFunction();
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("isDeleted", ${ConstantClassName}.NO);
		List<FunctionEO> allFuns = functionDao.selectByMap(queryParam);
		//用户菜单树
		LinkedHashSet<TreeNode> treeNodes = new LinkedHashSet<TreeNode>();
		Map<Long, TreeNode> treeNodeMap = new HashMap<Long, TreeNode>();
		rootNode = new TreeNode<FunctionEO, TreeNode>();
		rootNode.setId(root.getId());
		rootNode.setText(root.getFunctionName());
		treeNodeMap.put(root.getId(), rootNode);
		for(FunctionEO fun : allFuns){
			if(fun.getParentId()==null){
				continue;
			}
			TreeNode<FunctionEO, TreeNode> treeNode = treeNodeMap.get(fun.getId());
			if(treeNode==null){
				treeNode = new TreeNode<FunctionEO, TreeNode>();
				treeNode.setId(fun.getId());
				treeNode.setText(fun.getFunctionName());
				treeNode.setIndex(fun.getDisplayOrder());
				treeNode.setParentId(fun.getParentId());
				treeNode.setLeaf(true);
			}
			LinkedHashSet<TreeNode> childrenNode = null;
			TreeNode parentNode = treeNodeMap.get(fun.getParentId());
			if(parentNode==null){
				for(FunctionEO parentFun : allFuns){
					if(parentFun.getId().longValue()==fun.getParentId().longValue()){
						parentNode = new TreeNode<FunctionEO, TreeNode>();
						parentNode.setId(parentFun.getId());
						parentNode.setText(parentFun.getFunctionName());
						parentNode.setIndex(parentFun.getDisplayOrder());
						parentNode.setParentId(parentFun.getParentId());
						childrenNode = new LinkedHashSet<TreeNode>();
						parentNode.setChildren(childrenNode);
						break;
					}
				}
			}else{
				childrenNode = parentNode.getChildren();
				if(childrenNode==null){
					childrenNode = new LinkedHashSet<TreeNode>();
					parentNode.setChildren(childrenNode);
				}
				childrenNode.add(treeNode);
				treeNodeMap.put(fun.getId(), treeNode);
				parentNode.setLeaf(false);
				parentNode.setExpanded(true);
				treeNodeMap.put(fun.getParentId(), parentNode);				
			}
		}
		functionRedisDao.saveAllTreeNode(rootNode);
		return rootNode;
	}

	/**
	 * 验证传入的权限实体必填字段是否为空，为空则抛出异常
	 * @author ztjie
	 * @date 2013-4-18 上午11:36:26
	 * @param function
	 * @return 返回父节点对象
	 * @throws FunctionException
	 * @see
	 */
	private FunctionEO checkFunctionEntity(FunctionEO function) throws FunctionException {
		if (StringUtils.isBlank(function.getFunctionName())) {
			throw new FunctionException(FunctionException.FUNCTION_NAME_NULL);
		}
		if (function.getFunctionType()==null) {
			throw new FunctionException(FunctionException.FUNCTION_TYPE_NULL);
		}
		if (function.getParentId()==null) {
			throw new FunctionException(FunctionException.FUNCTION_PARENT_NULL);
		}
		FunctionEO parent = this.functionDao.selectById(function.getParentId());
		if (parent == null) {
			throw new FunctionException(FunctionException.FUNCTION_PARENT_NULL);
		}
		// 检查权限类型是否与父功能匹配
		FunctionType.getTypeByCode(function.getFunctionType()).checkParentType(parent);
		
		return parent;
	}

	/**
	 * 通过角色ID查询功能对象集合
	 * 
	 * @param roleId
	 * @return
	 */
	@Override
	public List<FunctionEO> queryAllFunctionByRoleId(Long roleId)
			throws RoleException {
		if (roleId == null) {
			throw new RoleException(RoleException.ROLE_ID_NULL);
		}
		List<FunctionEO> functions = functionDao.selectAllByRoleId(roleId);
		return functions;
	}

	@Override
	public List<Long> queryAllFunctionIdByRoleId(Long roleId)
			throws RoleException {
		List<FunctionEO> functions = this.queryAllFunctionByRoleId(roleId);
		List<Long> functionIds = new ArrayList<Long>();
		for(FunctionEO function : functions){
			functionIds.add(function.getId());
		}
		return functionIds;
	}
	
	@Override
	public List<String> queryAllFunctionUriByRoleId(Long roleId)
			throws RoleException {
		List<FunctionEO> functions = this.queryAllFunctionByRoleId(roleId);
		List<String> functionUris = new ArrayList<String>();
		for(FunctionEO function : functions){
			functionUris.add(function.getUri());
		}
		return functionUris;
	}

	/**
	 * 通过ID获得当前ID所对应的所有的子节点
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<FunctionEO> queryAllChildFunctionById(Long parentId)
			throws FunctionException {
		if (parentId == null) {
			throw new FunctionException(FunctionException.FUNCTION_PARENT_ID_NULL);
		}
		Map<String,Object> map = new HashMap<String,Object>(2);
		map.put("isDeleted", ${ConstantClassName}.NO);
		map.put("parentId", parentId);
		List<FunctionEO> functions = (List<FunctionEO>) functionDao.selectByMap(map, "display_order", true);
		return functions;
	}

	/**
	 * 设置检查权限、层级、设置路径，并根据类型和层级设置样式
	 * @author ztjie
	 * @date 2013-4-18 下午4:16:41
	 * @param function
	 * @see
	 */
	private void dealFunction(FunctionEO function, FunctionEO parent) {
		// 设置是否检查权限
		if (!function.getValidFlag()) {
			function.setValidFlag(${ConstantClassName}.NO);
		}else{
			function.setValidFlag(${ConstantClassName}.YES);
		}
		
		// 设置层级
		Integer level = parent.getFunctionLevel() + 1;
		function.setFunctionLevel(level);
	}

	@Transactional()
	@Override
	public FunctionEO save(FunctionEO function) throws FunctionException {
		FunctionEO parent = this.checkFunctionEntity(function);
		
		if (StringUtils.isNotBlank(function.getUri())) {
			if (this.queryFunctionURIExited(function.getUri())) {
				throw new FunctionException(FunctionException.FUNCTION_URI_EXITED);
			}
		}
		
		function.setIsDeleted(${ConstantClassName}.NO);
		function.setCreateTime(new Date());
		function.setLastUpdateTime(new Date());

		// 如果是菜单，则设置成叶节点
		if (FunctionType.MENU.getType()==function.getFunctionType()) {
			function.setLeaf(${ConstantClassName}.YES);
		} else {
			function.setLeaf(${ConstantClassName}.NO);
		}
		
		this.dealFunction(function, parent);
		this.functionDao.insert(function);
		this.flushCache(function.getId());
		return function;
	}
	
	/**
	 * 修改权限功能
	 * @see com.deppon.dpap.module.authorization.server.service.IFunctionService#updateFunction(com.FunctionEO.dpap.module.authorization.shared.domain.FunctionEntity)
	 */
	@Transactional()
	@Override
	public FunctionEO updateFunction(FunctionEO entity) throws FunctionException {
		FunctionEO newparent = this.checkFunctionEntity(entity);
		entity.setParentId(newparent.getId());
		
		FunctionEO oldEntity = this.functionDao.selectById(entity.getId());
		if (oldEntity == null) {
			throw new FunctionException(FunctionException.FUNCTION_NOT_EXITED);
		}
		// uri不为空且跟不等于老uri
		if (StringUtils.isNotBlank(entity.getUri()) 
				&& (!entity.getUri().equalsIgnoreCase(oldEntity.getUri()))) {
			if (this.queryFunctionURIExited(entity.getUri())) {
				throw new FunctionException(FunctionException.FUNCTION_URI_EXITED);
			}
		}
		Long functionId=oldEntity.getId();
		
		entity.setId(oldEntity.getId());
		entity.setLastUpdateTime(new Date());
		this.dealFunction(entity, newparent);
		this.functionDao.update(entity);
		
		this.flushCache(functionId);
		return entity;
	}
	
	/**
	 * 删除此功能,如果存在子节点，抛出异常
	 * @author ztjie
	 * @date 2013-4-18 下午5:22:22
	 * @param functionCode
	 * @throws FunctionException 
	 * @see com.deppon.dpap.module.authorization.server.service.IFunctionService#deleteFunction(java.lang.String)
	 */
	@Transactional()
	@Override
	public void deleteFunction(Long id) throws FunctionException {
		if (id == null) {
			throw new FunctionException(FunctionException.FUNCTION_ID_NULL);
		}
		FunctionEO entity = new FunctionEO();
		entity.setId(id);
		entity.setLastUpdateTime(new Date());
		entity.setIsDeleted(true);
		this.functionDao.update(entity);
		this.flushCache(id);
	}
	
	@Override
	public boolean queryFunctionURIExited(String uri) {
		Map<String,Object> map = new HashMap<String,Object>(2);
		map.put("isDeleted", ${ConstantClassName}.NO);
		map.put("uri", uri);
		int countCode = this.functionDao.countByMap(map);
		if (countCode > 0) {
			return true;
		}
		return false;
	}
	@Override
	public void flushCache(Long functionId){
		functionRedisDao.delete(functionId.toString());
		List<Long> userIds = functionDao.selectUserIdByFunctionId(functionId);
		for(Long uid:userIds){
			userInfoRedisDAO.destroyUserCache(uid);
		}
	}

	@Override
	public List<FunctionEO> queryUserFunction(Long uid) {
		return functionDao.selectUserFunction(uid);
	}
}