package com.hn658.framework.cache.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.hn658.framework.cache.test.entity.UserInfo;


/**
 * Created by baihai on 2015/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserCacheTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UserCacheTest.class);


//    @Autowired
    private UserRedisCache userCache;
//    @Autowired
//    private UserLocalCache userLocalCache;

    private static String[] fn = null;
    private static void contextInitialized() {
        fn = new String[] {"applicationContext.xml"};
    }


    @Test
    public void TestCache(){
        contextInitialized();
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(fn);
        String key = "redisTest3";
        String key3 = "redisTest3";
        UserInfo user1 = new UserInfo();
        user1.setCreateTime(new Date());
        user1.setId(2L);
        user1.setPassword("123456");
        user1.setUsername("aijian");
        LOGGER.info("redisCache测试put操作,值:{}",user1.toString());
        userCache.set(key,user1);
//
//        LOGGER.info("redisCache测试get操作,key:{}",key);
//        UserInfo userInfo =  userCache.get(key);
//
//        LOGGER.info("redisCache测试失效操作,key:{}",key);
//        userCache.invalid(key);
//
//        LOGGER.info("redisCache测试失效操作,key:{}",key);
//        userCache.invalidMulti(key,key3);
//
////        userCache.getRelationDB("redisTest3",123L);
//
//        //模板测试
//          userCache.TestTemple();
//        //回调测试
//         userCache.get("test2");
//
//        userCache.getRelationDB(key);

//        CacheManager.getInstance().getCache(UserRedisCache.CACHEID).set("rediscachemanager","123456");



//
//        userCache = (UserRedisCache) ctx.getBean("userRedisCache", UserRedisCache.class);
        userCache.TestTemple();

    }


    @Test
    public void keyTest(){
        //测试key生成器
    }

    @Test
    public void testLocalCache(){
//        String key = "redisTest3";
//        String key3 = "redisTest3";
//        UserInfo user1 = new UserInfo();
//        user1.setCreateTime(new Date());
//        user1.setId(2L);
//        user1.setPassword("123456");
//        user1.setUsername("aijian");
////        LOGGER.info("redisCache测试put操作,值:{}",user1.toString());
////        userLocalCache.set(key,user1);
////
////        LOGGER.info("redisCache测试get操作,key:{}",key);
////        UserInfo userInfo =  userLocalCache.get(key);
////
////        LOGGER.info("redisCache测试失效操作,key:{}",key);
////        userLocalCache.invalid(key);
////
////        LOGGER.info("redisCache测试失效操作,key:{}",key);
////        userLocalCache.invalidMulti(key,key3);
////
//
//        //模板测试
//        userLocalCache.TestTemple(user1);
//        //回调测试
////        userLocalCache.get("test2");

    }


    public void setUserCache(UserRedisCache userCache) {
        this.userCache = userCache;
    }
}
