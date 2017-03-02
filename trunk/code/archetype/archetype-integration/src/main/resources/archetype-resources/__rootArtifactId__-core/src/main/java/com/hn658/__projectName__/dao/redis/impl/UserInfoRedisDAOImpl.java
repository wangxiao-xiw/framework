package com.hn658.${projectName}.dao.redis.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.hn658.${projectName}.dao.redis.IUserInfoRedisDAO;
import com.hn658.${projectName}.common.entity.TreeNode;
import com.hn658.${projectName}.common.entity.UserInfoEO;
import com.hn658.${projectName}.common.utils.RedisKeyHelper;
import com.hn658.framework.dataaccess.redis.AbstractRedisDAO;
import com.hn658.framework.shared.utils.JsonMapper;

@Repository
public class UserInfoRedisDAOImpl extends AbstractRedisDAO<UserInfoEO> implements IUserInfoRedisDAO{
	
	JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	
	@Autowired 
	@Qualifier("redisTemplate")
	@Override
	public void setTemplate(StringRedisTemplate template) {
		super.template = template;
	};

	@Override
	public UserInfoEO findUserByUid(Long uid) {
		// 从redis中获取对应uid的UserInfo
        BoundHashOperations<String, String, String> userOps = template
                .boundHashOps(RedisKeyHelper.backendUserId(uid.toString()));

        return userOps.entries().isEmpty() != true ? mapper.fromHash(userOps.entries()) : null;
	}

	@Override
	public UserInfoEO findUserByLoginAccount(String loginAccount) {
		// 先获取loginAccount对应的Uid
		Long uid = findUidByLoginAccount(loginAccount);
		if(uid==null){
			return null;
		}

		// 从Redis中获取对应uid的UserInfo
		return this.findUserByUid(uid);
	}

	@Override
	public void saveUser(UserInfoEO userInfo) {
		if(userInfo == null){
			return;
		}
		// 将userInfo保存至Redis
        BoundHashOperations<String, String, String> userOps = template
                .boundHashOps(RedisKeyHelper.backendUserId(userInfo.getId().toString()));
		
        if (StringUtils.isNotBlank(userInfo.getId().toString())) {
            userOps.put("id", userInfo.getId().toString());
            // 设置帐号名对应的uid
            valueOps.set(RedisKeyHelper.backendUserAccount(StringUtils.lowerCase(userInfo.getLoginAccount())),
            		userInfo.getId().toString());
        }
		if(StringUtils.isNotEmpty(userInfo.getLoginAccount())){
			userOps.put("loginAccount", userInfo.getLoginAccount());
		}
		if(StringUtils.isNotEmpty(userInfo.getPassword())){
			userOps.put("password", userInfo.getPassword());
		}
		if(StringUtils.isNotEmpty(userInfo.getRealName())){
			userOps.put("realName", userInfo.getRealName());
		}
		if(StringUtils.isNotEmpty(userInfo.getNickName())){
			userOps.put("nickName", userInfo.getNickName());
		}
		if(StringUtils.isNotEmpty(userInfo.getSex())){
			userOps.put("sex", userInfo.getSex());
		}
		if(StringUtils.isNotEmpty(userInfo.getQq())){
			userOps.put("qq", userInfo.getQq());
		}
		if(StringUtils.isNotEmpty(userInfo.getWeiXin())){
			userOps.put("weiXin", userInfo.getWeiXin());
		}
		if(StringUtils.isNotEmpty(userInfo.getPhoneNumber())){
			userOps.put("phoneNumber", userInfo.getPhoneNumber());
		}
		if(StringUtils.isNotEmpty(userInfo.getSign())){
			userOps.put("sign", userInfo.getSign());
		}
		if(StringUtils.isNotEmpty(userInfo.getAvatarUrl())){
			userOps.put("avatarUrl", userInfo.getAvatarUrl());
		}
		if(userInfo.getLastUpdateTime() != null){
			userOps.put("lastUpdateTime", String.valueOf(userInfo.getLastUpdateTime().getTime()));
		}
		if(userInfo.getCreateTime() != null){
			userOps.put("createTime", String.valueOf(userInfo.getCreateTime().getTime()));
		}
		if(userInfo.getIsDeleted() != null){
			userOps.put("isDeleted", userInfo.getIsDeleted().toString());
		}
	}
	
	@Override
	public String getAuthkeyByUid(Long uid) {
		return valueOps.get(RedisKeyHelper.backendAccountAuth(uid.toString()));
	}
	
	@Override
	public void setUserAuthkey(Long uid, String authkey) {
		// 删除原先的authkey
		String origAuthkey = valueOps.get(RedisKeyHelper.backendUserAuth(uid.toString()));
		if(StringUtils.isNotEmpty(origAuthkey)){
			template.delete(RedisKeyHelper.backendUserAuthKey(origAuthkey));
		}
		
		// 创建authkey
		valueOps.set(RedisKeyHelper.backendUserAuth(uid.toString()), authkey);
		valueOps.set(RedisKeyHelper.backendUserAuthKey(authkey), uid.toString());
	}

	@Override
	public void removeUserAuthkey(Long uid) {
		String authkey = valueOps.get(RedisKeyHelper.backendUserAuth(uid.toString()));
		
		template.delete(RedisKeyHelper.backendUserAuth(uid.toString()));
		template.delete(RedisKeyHelper.backendUserAuthKey(authkey));
	}
	
	@Override
	public Long getUidByAuthkey(String authkey) {
		String uid = valueOps.get(RedisKeyHelper.backendUserAuthKey(authkey));
		if(StringUtils.isNotBlank(uid)){
			return new Long(uid);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据帐号名查找uid
	 * @param loginAccount
	 * @return
	 */
	private Long findUidByLoginAccount(String loginAccount) {
		String uid = valueOps.get(RedisKeyHelper.backendUserAccount(StringUtils.lowerCase(loginAccount)));
		if(StringUtils.isNotBlank(uid)){
			return new Long(uid);
		}else{
			return null;
		}
	}

	@Override
	public void deleteUser(Long uid) {
		// 删除用户信息
		this.template.delete(RedisKeyHelper.backendUserId(uid.toString()));
		
		// 删除用户session
		this.removeUserAuthkey(uid);
	}

	@Override
	public Set<String> findUserAccessUris(Long uid) {
		if(uid==null){
			return null;
		}
		String userAccessUris = valueOps.get(RedisKeyHelper.backendUserAccessUris(uid));
		Set<String> accessUris = jsonMapper.fromJson(userAccessUris, Set.class);
        return accessUris;
	}

	@Override
	public void saveUserAccessUris(Long uid, Set<String> accessUris) {
		String userAccessUris = jsonMapper.toJson(accessUris);
		valueOps.set(RedisKeyHelper.backendUserAccessUris(uid), userAccessUris);
	}
	
	@Override
	public Set<Long> findUserAccessIds(Long uid) {
		if(uid==null){
			return null;
		}
		String userAccessIds = valueOps.get(RedisKeyHelper.backendUserAccessIds(uid));
		Set<Long> accessIds = jsonMapper.fromJson(userAccessIds, Set.class);
        return accessIds;
	}

	@Override
	public void saveUserAccessIds(Long uid, Set<Long> accessIds) {
		String userAccessIds = jsonMapper.toJson(accessIds);
		valueOps.set(RedisKeyHelper.backendUserAccessIds(uid), userAccessIds);
	}

	@Override
	public List<TreeNode> findUserAccessTreeNodes(Long uid) {
		if(uid==null){
			return null;
		}
		String userAccessTreeNodes = valueOps.get(RedisKeyHelper.backendUserAccessTreeNodes(uid));
		List<TreeNode> accessUserAccessTreeNodes = jsonMapper.fromJson(userAccessTreeNodes, List.class);
        return accessUserAccessTreeNodes;
	}
	
	@Override
	public void saveUserAccessTreeNodes(Long uid, LinkedHashSet<TreeNode> treeNodes) {
		String userAccessTreeNodes = jsonMapper.toJson(treeNodes);
		valueOps.set(RedisKeyHelper.backendUserAccessTreeNodes(uid), userAccessTreeNodes);
	}
	
	@Override
	public void destroyUserCache(Long uid) {
		this.template.delete(RedisKeyHelper.backendUserAccessUris(uid));
		this.template.delete(RedisKeyHelper.backendUserAccessIds(uid));
		this.template.delete(RedisKeyHelper.backendUserAccessTreeNodes(uid));
	}
}
