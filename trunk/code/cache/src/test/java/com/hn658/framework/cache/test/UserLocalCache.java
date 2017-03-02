package com.hn658.framework.cache.test;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.hn658.framework.cache.CacheSupport;
import com.hn658.framework.cache.storage.ICacheStorage;
import com.hn658.framework.cache.test.entity.UserInfo;

/**
 * Created by baihai on 2015/10/27.
 */
@Repository
public class UserLocalCache extends CacheSupport<UserInfo> {

	public static final String CACHEID = "user:cache:id";

	@Override
	public void setStorage(ICacheStorage<String, UserInfo> storage) {
		super.storage = storage;
	}

	public UserInfo doGet(Object s) {
		Long id = (Long) s;
		UserInfo user = new UserInfo();
		user.setUsername("shabi");
		user.setPassword("123456");
		user.setId(id);
		user.setCreateTime(new Date());
		return user;
	}

	/**
	 * 模板测试
	 */
	public void TestTemple(UserInfo userInfo) {
		String key = "local_expire";
		// int size = map.size();
		map.put("local_expire", userInfo, 20);
		map.ttl(key);
	}

	@Override
	public String getCacheId() {
		return CacheConstants.USERLOCAL_CACHEID;
	}
}
