package com.hn658.framework.cache.test;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.hn658.framework.cache.CacheSupport;
import com.hn658.framework.cache.base.CacheCallBack;
import com.hn658.framework.cache.storage.ICacheStorage;
import com.hn658.framework.cache.test.entity.UserInfo;

/**
 * Created by baihai on 2015/10/26.
 */
@Repository
public class UserRedisCache extends CacheSupport<UserInfo> {

	public static final String CACHEID = "user:cache:id";

	// @Autowired
	// @Qualifier("redisStorage")
	@Override
	public void setStorage(ICacheStorage storage) {
		super.storage = storage;
	};

	public UserInfo doGet(Object s) {
		UserInfo user = new UserInfo();
		return user;
	}

	/**
	 * 关联DB查询用户信息
	 */
	public void getRelationDB(final String loginAccount) {
		getRelationDB(CacheHepper.userAccount(loginAccount),
				new CacheCallBack<UserInfo>() {
					@Override
					public UserInfo doGet() {
						UserInfo userInfo = null /* localCache.get(loginAccount) */;
						userInfo = new UserInfo(123L, "iajian", "shasha",
								new Date());
						return userInfo;
					}
				});

	}

	/**
	 * 模板测试
	 */
	public void TestTemple() {
		setOps.add("setTest1", "我是set");
	}

	@Override
	public String getCacheId() {
		return CacheConstants.USERREDIS_CACHEID;
	}
}
