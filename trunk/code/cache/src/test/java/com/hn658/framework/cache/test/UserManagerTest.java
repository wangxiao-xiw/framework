package com.hn658.framework.cache.test;

import com.hn658.framework.cache.local.LocallyCacheManager;
import com.hn658.framework.cache.redis.RedisCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Created by baihai on 2015/10/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserManagerTest {



    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private LocallyCacheManager locallyCacheManager;

    @Test
    public void TestCache(){
        redisCacheManager.getCache(UserRedisCache.CACHEID).set("redismanager1404redis","zzz");
        locallyCacheManager.getCache(UserLocalCache.CACHEID).set("redismanager1404local","zzz");

    }

}
