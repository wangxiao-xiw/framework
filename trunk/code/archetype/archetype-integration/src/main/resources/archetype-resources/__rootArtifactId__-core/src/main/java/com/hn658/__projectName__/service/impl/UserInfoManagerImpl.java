package com.hn658.${projectName}.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hn658.${projectName}.dao.redis.IUserInfoRedisDAO;
import com.hn658.${projectName}.service.IFunctionService;
import com.hn658.${projectName}.service.IUserInfoManager;
import com.hn658.${projectName}.common.entity.FunctionEO;
import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.enums.FunctionType;
import com.hn658.${projectName}.common.exceptions.RoleException;
import com.hn658.${projectName}.common.exceptions.UserException;
import com.hn658.framework.common.AbstractBusinessObject;
import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.logging.enums.LogCategory;
import com.hn658.framework.shared.exception.BusinessException;
import com.hn658.user.itf.ItfUserService;
import com.hn658.user.itf.vo.QueryUserInfoDTO;
import com.hn658.user.itf.vo.UserInfoDTO;
import com.hn658.user.itf.vo.UserInfoResponse;

@Component
public class UserInfoManagerImpl extends AbstractBusinessObject implements
		IUserInfoManager {

	@Autowired
	private ItfUserService itfUserService;
	
	@Autowired
	private IUserInfoRedisDAO userInfoRedisDAO;
	
	@Autowired
	private IUserRoleDBDAO userRoleDBDAO;
	
	@Autowired
	private IFunctionService functionService;

	@Override
	public UserInfoDTO findUserInfoById(Long userId) throws BusinessException {
		UserInfoResponse response = itfUserService.findUserById(userId);
		UserInfoDTO userInfo = response.getUserInfo();
		return userInfo;
	}

	@Override
	public UserInfoDTO findUserInfoByLoginAccount(String loginAccount)
			throws BusinessException {
		UserInfoResponse response = itfUserService.findUserByLoginAccount(loginAccount);
		UserInfoDTO userInfo = response.getUserInfo();
		return userInfo;
	}

	@Override
	public List<UserInfoDTO> queryEnableUser() {
		UserInfoResponse response = itfUserService.queryEnableUser();
		List<UserInfoDTO> userInfoList = response.getUserInfoList();
		return userInfoList;
	}

	@Override
	public GenericPage<UserInfoDTO> queryUser(QueryUserInfoDTO queryInfo,
			int limit, int start, String sortBy, boolean isAsc) {
		UserInfoResponse response = itfUserService.queryUser(queryInfo,limit, start, sortBy, isAsc);
		GenericPage<UserInfoDTO> genericPage = new GenericPage<UserInfoDTO>(start, response.getTotalCount(), limit, response.getUserInfoList());
		return genericPage;
	}

	@Override
	public GenericPage<UserInfoDTO> queryAppUser(Map<String,Object> queryMap,String appid,
			int limit, int start, String sortBy, boolean isAsc) {
		UserInfoResponse response = itfUserService.queryAppUser(queryMap,appid,limit, start, sortBy, isAsc);
		GenericPage<UserInfoDTO> genericPage = new GenericPage<UserInfoDTO>(start, response.getTotalCount(), limit, response.getUserInfoList());
		return genericPage;
	}
	
	@Override
	public UserInfoDTO validateCookie(String appKey, String cookie) {
		UserInfoResponse response = itfUserService.validateCookie(appKey, cookie);
		UserInfoDTO userInfo = response.getUserInfo();
		return userInfo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<TreeNode> queryUserTreeNodes(Long uid) {
		List<TreeNode> userTreeNodes = userInfoRedisDAO.findUserAccessTreeNodes(uid);

		if (null == userTreeNodes) {
			logger.debug(LogCategory.SYSTEM, "在redis中未找到{}相关信息，尝试在数据库中查找。", uid);
			loadUserCacheInfo(uid);
			userTreeNodes = userInfoRedisDAO.findUserAccessTreeNodes(uid);
		} else {
			logger.debug(LogCategory.SYSTEM, "成功的在redis中找到{}相关信息。", uid);
		}
		return userTreeNodes;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void loadUserCacheInfo(Long uid) throws UserException {
		if (uid == null) {
			throw new UserException(UserException.UserIdEmptyWrong);
		}
		List<FunctionEO> functions = functionService.queryUserFunction(uid);
		//用户权限URL
		Set<String> functionUris = new HashSet<String>();
		//用户权限ID
		Set<Long> functionIds = new HashSet<Long>();
		//用户菜单树
		LinkedHashSet<TreeNode> treeNodes = new LinkedHashSet<TreeNode>();
		
		Map<Long, TreeNode> treeNodeMap = new HashMap<Long, TreeNode>();
		FunctionEO root = functionService.queryRootFunction();
		TreeNode<FunctionEO, TreeNode> rootNode = new TreeNode<FunctionEO, TreeNode>();
		rootNode.setId(root.getId());
		rootNode.setText(root.getFunctionName());
		rootNode.setExpanded(true);
		treeNodeMap.put(root.getId(), rootNode);
		
		for(FunctionEO fun : functions){
			functionUris.add(fun.getUri());
			functionIds.add(fun.getId());
			if(fun.getFunctionType().intValue()==FunctionType.BUTTON.getType().intValue()){
				continue;
			}
			TreeNode<FunctionEO, TreeNode> treeNode = treeNodeMap.get(fun.getId());
			if(treeNode==null){
				treeNode = new TreeNode<FunctionEO, TreeNode>();
				treeNode.setId(fun.getId());
				treeNode.setText(fun.getFunctionName());
				treeNode.setIndex(fun.getDisplayOrder());
				treeNode.setParentId(fun.getParentId());
				treeNode.setExpanded(true);
				if (FunctionType.MENU.getType()==(fun.getFunctionType())) {
					treeNode.setLeaf(true);
					treeNode.setUri(fun.getUri());
				}else{
					treeNode.setLeaf(false);
				}
			}
			LinkedHashSet<TreeNode> childrenNode = null;
			TreeNode parentNode = treeNodeMap.get(fun.getParentId());
			if(parentNode==null){
				for(FunctionEO parentFun : functions){
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
			}
			if(parentNode!=null){
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
		treeNodes = rootNode.getChildren();
		
		userInfoRedisDAO.saveUserAccessUris(uid, functionUris);
		userInfoRedisDAO.saveUserAccessIds(uid, functionIds);
		userInfoRedisDAO.saveUserAccessTreeNodes(uid, treeNodes);
	}
	
	@Override
	public List<RoleInfoEO> queryAuthedRoles(Long uid) {
		if(uid==null){
			throw new RoleException(RoleException.ROLE_ID_NULL);
		}
		List<RoleInfoEO> selectAuthedRoles = userRoleDBDAO.selectAuthedRoles(uid);
	
		return selectAuthedRoles;
	}
	

	@Override
	public List<RoleInfoEO> queryUnAuthedRoles(Long uid) {
		return userRoleDBDAO.selectUnAuthedRoles(uid);
	}
	
	@Override
	@Transactional
	public void authUserRoles(Long uid, List<Long> roleIds) {
		if(uid==null){
			throw new UserException(UserException.UserIdEmptyWrong);
		}
		if(roleIds==null){
			throw new UserException(UserException.UserRoleIdsEmptyWrong);
		}
		userRoleDBDAO.deleteByUid(uid);
		for(Long roleId : roleIds){
			userRoleDBDAO.insertUserRole(uid, roleId);			
		}
		userInfoRedisDAO.destroyUserCache(uid);
		
	}
}