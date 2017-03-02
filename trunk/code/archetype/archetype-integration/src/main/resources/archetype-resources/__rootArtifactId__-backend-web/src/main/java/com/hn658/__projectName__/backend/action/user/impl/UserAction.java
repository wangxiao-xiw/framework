package com.hn658.${projectName}.backend.action.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hn658.${projectName}.service.IUserInfoManager;
import com.hn658.${projectName}.backend.action.role.vo.RoleInfoResponse;
import com.hn658.${projectName}.backend.action.user.IUserAction;
import com.hn658.${projectName}.backend.action.user.vo.CurrentUserResponse;
import com.hn658.${projectName}.backend.action.user.vo.QueryUserResponse;
import com.hn658.${projectName}.backend.action.user.vo.UserRequest;
import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.entity.UserInfoEO;
import com.hn658.${projectName}.common.vo.CommonResponse;
import com.hn658.framework.common.AbstractBaseService;
import com.hn658.framework.common.IServiceResponse;
import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.context.UserContext;
import com.hn658.framework.shared.exception.BusinessException;
import com.hn658.user.itf.vo.UserInfoDTO;


@Controller("userAction")
@Path("user")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
public class UserAction extends AbstractBaseService implements IUserAction {

	@Autowired
	private IUserInfoManager userInfoManager;
	
	private static final String appId = "b45cebdf032d4f03b3a3f09066ca9f83";
	
	/**
	 * 查询用户列表
	 * 
	 * @return
	 */
	@Path("queryUser")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@POST
	public IServiceResponse queryUser(
			@FormParam("loginAccount") String loginAccount,
			@FormParam("nickName") String nickName,
			@FormParam("realName") String realName,
			@FormParam(value="limit") int limit, 
			@FormParam(value="start") int start) {

		Map<String,Object> queryMap = new HashMap<String,Object>();
		QueryUserResponse resp = new QueryUserResponse();

		if (StringUtils.isNotBlank(loginAccount)) {
			queryMap.put("loginAccount", loginAccount.trim());
		}
		if (StringUtils.isNotBlank(nickName)) {
			queryMap.put("nickName", nickName.trim());
		}
		if (StringUtils.isNotBlank(realName)) {
			queryMap.put("realName", realName.trim());
		}
		
		queryMap.put("isDeleted", false);
		
		GenericPage<UserInfoDTO> genericPage = userInfoManager.queryAppUser(queryMap,appId, limit, start, "create_time", true);
		resp.setUserList(genericPage.getData());
		resp.setTotalCount(genericPage.getTotalCount());
		this.success(resp);

		return resp;
	}
	
	@Path("currentUserInfo")
	@Consumes("text/html; charset=UTF-8")
	@GET
	public IServiceResponse currentUserInfo() {
		UserInfoEO userInfo = (UserInfoEO)UserContext.getCurrentUser();
		CurrentUserResponse cur = new CurrentUserResponse();
		cur.setUserInfo(userInfo);
		List<TreeNode> treeNodes = userInfoManager.queryUserTreeNodes(userInfo.getId());
		cur.setTreeNodes(treeNodes);
		this.success(cur);
		return cur;
	}
	
    
	@Path("queryAuthedRoles")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@POST
	public IServiceResponse queryAuthedRoles(@FormParam(value="uid") Long uid) {
		RoleInfoResponse response = new RoleInfoResponse();
		try {
			List<RoleInfoEO> roleInfoList = userInfoManager.queryAuthedRoles(uid);
			response.setRoleInfoList(roleInfoList);
			this.success(response);
        } catch (BusinessException e) {
            this.error(response, e);
        }
		return response;
	}
	
	   @Path("queryUnAuthedRoles")
	    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	    @Produces("application/json; charset=UTF-8")
	    @POST
	    public IServiceResponse queryUnAuthedRoles(@FormParam(value="uid") Long uid) {
	    	RoleInfoResponse response = new RoleInfoResponse();
	        try {
	            List<RoleInfoEO> roleInfoList = userInfoManager.queryUnAuthedRoles(uid);
	            response.setRoleInfoList(roleInfoList);
	            this.success(response);
	        } catch (BusinessException e) {
	            this.error(response, e);
	        }
	        return response;
	    }
	   
	   @Path("authUserRoles")
		@POST
		public IServiceResponse authUserRoles(UserRequest request) {
	    	CommonResponse response = new CommonResponse();
	        try {
	        	userInfoManager.authUserRoles(request.getId(), request.getRoleIds());
	            this.success(response);
	        } catch (BusinessException e) {
	            this.error(response, e);
	        }
	        return response;
		}
}
