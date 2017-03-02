package com.hn658.${projectName}.service.cache;

import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hn658.${projectName}.service.IUserInfoManager;
import com.hn658.${projectName}.common.entity.UserInfoEO;
import com.hn658.framework.security.cache.ISecurityCacheProvider;
import com.hn658.framework.shared.entity.IFunction;
import com.hn658.framework.shared.entity.IRole;
import com.hn658.framework.shared.entity.IUser;
import com.hn658.user.itf.vo.UserInfoDTO;

@Component
public class SecurityCacheProvider implements ISecurityCacheProvider {
	
	//@Autowired
	//private IFunctionService functionService;
	
	@Autowired
	private IUserInfoManager userService;

	@Override
	public IFunction getFunction(String accessURL) {
		//return functionService.queryFunctionByUri(accessURL);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRole getRole(String roleCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUser getUser(Long uid) {
		UserInfoDTO userDto = userService.findUserInfoById(Long.valueOf(uid));
		UserInfoEO userInfo = new UserInfoEO();
		BeanUtils.copyProperties(userDto, userInfo);
		return userInfo;
	}

	@Override
	public Set<Long> queryFunctionIds(Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> queryFunctionUris(Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> queryAccessUris(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Long> queryRoleIds(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}

}
