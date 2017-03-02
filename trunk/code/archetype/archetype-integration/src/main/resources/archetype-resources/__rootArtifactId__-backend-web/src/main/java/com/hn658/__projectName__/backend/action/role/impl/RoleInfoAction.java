package com.hn658.${projectName}.backend.action.role.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hn658.${projectName}.service.IRoleInfoService;
import com.hn658.${projectName}.backend.action.role.IRoleInfoAction;
import com.hn658.${projectName}.backend.action.role.vo.RoleInfoRequest;
import com.hn658.${projectName}.backend.action.role.vo.RoleInfoResponse;
import com.hn658.${projectName}.common.entity.RoleInfoEO;
import com.hn658.framework.common.AbstractBaseService;
import com.hn658.framework.common.IServiceResponse;
import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.shared.exception.BusinessException;

@Controller("roleInfoAction")
@Path("role")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
public class RoleInfoAction extends AbstractBaseService implements
		IRoleInfoAction {

	@Autowired
	private IRoleInfoService roleInfoService;;

	@Path("queryRoleList")
	@Consumes("application/x-www-form-urlencoded; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@POST
	public IServiceResponse queryRoleList(
			@FormParam(value = "roleNameLike") String roleNameLike,
			@FormParam(value = "limit") int limit,
			@FormParam(value = "start") int start) {
		RoleInfoResponse response = new RoleInfoResponse();
		try {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("roleNameLike", roleNameLike);
			GenericPage<RoleInfoEO> page = roleInfoService.queryRoleInfo(queryMap, limit, start, "create_time", true);
			response.setRoleInfoList(page.getData());
			response.setTotalCount(page.getTotalCount());
			this.success(response);
		} catch (BusinessException e) {
			this.error(response, e);
		}
		return response;
	}

	@Path("saveRole")
	@POST
	public IServiceResponse saveRole(RoleInfoRequest request) {
		RoleInfoResponse response = new RoleInfoResponse();
		try {
			roleInfoService.save(request.getRoleInfo(), request.getFunctionIds());
			this.success(response);
		} catch (BusinessException e) {
			this.error(response, e);
		}
		return response;
	}

	@Path("updateRole")
	@POST
	public IServiceResponse updateRole(RoleInfoRequest request) {
		RoleInfoResponse response = new RoleInfoResponse();
		try {
			roleInfoService.update(request.getRoleInfo(), request.getFunctionIds());
			this.success(response);
		} catch (BusinessException e) {
			this.error(response, e);
		}
		return response;
	}

	@Path("deleteRole")
	@POST
	public IServiceResponse deleteRole(RoleInfoRequest request) {
		RoleInfoResponse response = new RoleInfoResponse();
		try {
			roleInfoService.delete(request.getId());
			this.success(response);
		} catch (BusinessException e) {
			this.error(response, e);
		}
		return response;
	}

}
