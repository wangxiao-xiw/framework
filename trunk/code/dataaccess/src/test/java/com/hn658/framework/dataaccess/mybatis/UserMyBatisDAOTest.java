/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		UserMyBatisDAOTest
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.mybatis
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-5-14
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-5-14 下午6:19:31
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.mybatis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.hn658.framework.dataaccess.mybatis.dao.IUserMyBatisDAO;
import com.hn658.framework.dataaccess.mybatis.entity.UserMyBatisEO;
import com.hn658.framework.dataaccess.pagination.GenericPage;

/**
 * @author Shawn
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:/META-INF/kb5173-frmwk-da-test-context.xml" })
@ActiveProfiles("mybatis")
public class UserMyBatisDAOTest {
	@Autowired
	IUserMyBatisDAO userMyBatisDAO;
	
	/*@Test
	public void queryCount(){
		int userCount = userMyBatisDAO.count();
		Assert.assertEquals(6, userCount);
	}*/
	
	@Test
	public void countByMap(){
		UserMyBatisEO saveUser = new UserMyBatisEO();
		saveUser.setLoginName("sun");
		saveUser.setRealName("测试");
		saveUser.setEmail("sun@WZITech.com");
		saveUser.setTel("28981228888");
		saveUser.setCreatedDateTime(Timestamp.valueOf("2012-05-13 18:18:18"));
		saveUser.setDeleted(false);
		
		userMyBatisDAO.insert(saveUser);
		saveUser = userMyBatisDAO.selectUniqueByProp("email", "sun@WZITech.com");
		Assert.assertNotNull(saveUser);
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("tel", "28981228888");
		int count = userMyBatisDAO.countByMap(map1);
		Assert.assertEquals(2, count);
	}
	
	@Test
	public void queryUser(){
		UserMyBatisEO user = userMyBatisDAO.selectById(1L);
		Assert.assertEquals("Shawn1", user.getLoginName());
		Assert.assertEquals("杨扬1", user.getRealName());
		Assert.assertEquals("Shawn.Young1@WZITech.com", user.getEmail());
		Assert.assertEquals("18981228888", user.getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), user.getCreatedDateTime());
		Assert.assertEquals(false, user.isDeleted());
	}
	
	@Test
	public void selectUniqueByProp(){
		UserMyBatisEO user = userMyBatisDAO.selectUniqueByProp("email", "Shawn.Young1@WZITech.com");
		
		Assert.assertEquals("Shawn1", user.getLoginName());
		Assert.assertEquals("杨扬1", user.getRealName());
		Assert.assertEquals("Shawn.Young1@WZITech.com", user.getEmail());
		Assert.assertEquals("18981228888", user.getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), user.getCreatedDateTime());
		Assert.assertEquals(false, user.isDeleted());
	}
	
	@Test
	public void selectByProp(){
		UserMyBatisEO saveUser = new UserMyBatisEO();
		saveUser.setLoginName("Dev");
		saveUser.setRealName("测试");
		saveUser.setEmail("Shawn.Young1@WZITech.com");
		saveUser.setTel("9506666666");
		saveUser.setCreatedDateTime(Timestamp.valueOf("2012-05-13 18:18:18"));
		saveUser.setDeleted(false);
		
		userMyBatisDAO.insert(saveUser);
		saveUser = userMyBatisDAO.selectUniqueByProp("loginName", "Dev");
		Assert.assertNotNull(saveUser);
		
		List<UserMyBatisEO> userList = userMyBatisDAO.selectByProp("email", "Shawn.Young1@WZITech.com");
		Assert.assertEquals(2, userList.size());
		
		GenericPage<UserMyBatisEO> pageList = userMyBatisDAO.selectByProp("email", "Shawn.Young1@WZITech.com" ,1,1,"tel",false);
		userList = pageList.getData();
		
		Assert.assertEquals(2, pageList.getTotalPageCount());
		Assert.assertEquals(2, pageList.getTotalCount());
		Assert.assertEquals(1, pageList.getPageSize());
		Assert.assertEquals(2, pageList.getCurrentPageNo());
		Assert.assertEquals(false, pageList.isHasNextPage());
		Assert.assertEquals(true, pageList.isHasPreviousPage());
		
		Assert.assertEquals(1, userList.size());
		Assert.assertEquals("Shawn1", userList.get(0).getLoginName());
		Assert.assertEquals("杨扬1", userList.get(0).getRealName());
		Assert.assertEquals("Shawn.Young1@WZITech.com", userList.get(0).getEmail());
		Assert.assertEquals("18981228888", userList.get(0).getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), userList.get(0).getCreatedDateTime());
		Assert.assertEquals(false, userList.get(0).isDeleted());
	}
	
	@Test
	public void selectIds(){
		UserMyBatisEO saveUser = new UserMyBatisEO();
		saveUser.setLoginName("Shawn4");
		saveUser.setRealName("测试");
		saveUser.setEmail("Shawn.Sun1@WZITech.com");
		saveUser.setTel("9506666666");
		saveUser.setCreatedDateTime(Timestamp.valueOf("2012-05-13 18:18:18"));
		saveUser.setDeleted(false);
		
		userMyBatisDAO.insert(saveUser);
		saveUser = userMyBatisDAO.selectUniqueByProp("email", "Shawn.Sun1@WZITech.com");
		Assert.assertNotNull(saveUser);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginName", "Shawn4");
		GenericPage<Long> pageList = userMyBatisDAO.selectIds(map,1,1,"tel",false);
		List<Long> idList = pageList.getData();
		
		Assert.assertEquals(4L, ((Number)idList.get(0)).longValue());
	}
	
	@Test
	public void selectByIds(){
		List<Long> intList = new ArrayList<Long>();
		intList.add((long)2);
		List<UserMyBatisEO> userList = userMyBatisDAO.selectByIds(intList);
		
		Assert.assertEquals(1, userList.size());
		Assert.assertEquals("Shawn2", userList.get(0).getLoginName());
		Assert.assertEquals("杨扬2", userList.get(0).getRealName());
		Assert.assertEquals("Shawn.Young2@WZITech.com", userList.get(0).getEmail());
		Assert.assertEquals("28981228888", userList.get(0).getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), userList.get(0).getCreatedDateTime());
		Assert.assertEquals(false, userList.get(0).isDeleted());
	}
	
	@Test
	public void selectByStatement(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginName", "Shawn3");
		
		GenericPage<UserMyBatisEO> pageList = userMyBatisDAO.selectByStatement("selectByMap", map, 1,0,"tel", false);
		List<UserMyBatisEO> userList = pageList.getData();
		
		Assert.assertEquals(1, pageList.getTotalPageCount());
		Assert.assertEquals(1, pageList.getTotalCount());
		Assert.assertEquals(1, pageList.getPageSize());
		Assert.assertEquals(1, pageList.getCurrentPageNo());
		Assert.assertEquals(false, pageList.isHasNextPage());
		Assert.assertEquals(false, pageList.isHasPreviousPage());
		
		Assert.assertEquals(1, userList.size());
		Assert.assertEquals("Shawn3", userList.get(0).getLoginName());
		Assert.assertEquals("杨扬3", userList.get(0).getRealName());
		Assert.assertEquals("Shawn.Young3@WZITech.com", userList.get(0).getEmail());
		Assert.assertEquals("38981228888", userList.get(0).getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), userList.get(0).getCreatedDateTime());
		Assert.assertEquals(false, userList.get(0).isDeleted());
	}
	
	@Test
	public void selectByNativeSql(){
		String sql = "SELECT * FROM TBL_EGM_USER_TEST WHERE LOGIN_NAME = 'Shawn3'";
		List<UserMyBatisEO> userList = userMyBatisDAO.selectByNativeSql(sql);
		
		Assert.assertEquals(1, userList.size());
		Assert.assertEquals("Shawn3", userList.get(0).getLoginName());
		Assert.assertEquals("杨扬3", userList.get(0).getRealName());
		Assert.assertEquals("Shawn.Young3@WZITech.com", userList.get(0).getEmail());
		Assert.assertEquals("38981228888", userList.get(0).getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), userList.get(0).getCreatedDateTime());
		Assert.assertEquals(false, userList.get(0).isDeleted());
	}
	
	@Test
	public void saveUser(){
		UserMyBatisEO user = new UserMyBatisEO();
		user.setLoginName("Dev");
		user.setRealName("测试");
		user.setEmail("dev@wzitech.com");
		user.setTel("1506666666");
		user.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		user.setDeleted(false);
		
		userMyBatisDAO.insert(user);
		
		UserMyBatisEO savedUser = userMyBatisDAO.selectUniqueByProp("email", "dev@wzitech.com");
		Assert.assertEquals("Dev", savedUser.getLoginName());
	}
	
	@Test
	public void batchInsertUser(){
		List<UserMyBatisEO> userList = new ArrayList<UserMyBatisEO>();
		UserMyBatisEO user1 = new UserMyBatisEO();
		user1.setLoginName("Dev1");
		user1.setRealName("测试1");
		user1.setEmail("dev@wzitech1.com");
		user1.setTel("15088888888");
		user1.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		user1.setDeleted(false);
		UserMyBatisEO user2 = new UserMyBatisEO();
		user2.setLoginName("Dev2");
		user2.setRealName("测试2");
		user2.setEmail("dev@wzitech2.com");
		user2.setTel("15077777777");
		user2.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		user2.setDeleted(false);
		userList.add(user1);
		userList.add(user2);
		
		userMyBatisDAO.batchInsert(userList);
		UserMyBatisEO savedUser = userMyBatisDAO.selectUniqueByProp("tel", "15088888888");
		Assert.assertEquals("Dev1", savedUser.getLoginName());
		savedUser = userMyBatisDAO.selectUniqueByProp("realName", "测试2");
		Assert.assertEquals("Dev2", savedUser.getLoginName());
	}
	
	@Test
	public void batchUpdate(){
		UserMyBatisEO user = userMyBatisDAO.selectById(1L);
		Assert.assertEquals("Shawn1", user.getLoginName());
		
		user.setLoginName("cheng");
		userMyBatisDAO.update(user);
		
		user = userMyBatisDAO.selectById(1L);
		Assert.assertEquals("cheng", user.getLoginName());
		
		user = userMyBatisDAO.selectById(2L);
		Assert.assertEquals("Shawn2", user.getLoginName());
	}
	
	@Test
	public void batchUpdateByIds(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", "sun@wzitech.com");
		map.put("tel", "13011112222");
		
		List<Long> idList = new ArrayList<Long>();
		idList.add((long)4);
		idList.add((long)5);
		
		userMyBatisDAO.batchUpdateByIds(idList, map);
		
		UserMyBatisEO updateEo = userMyBatisDAO.selectById(4L);
		Assert.assertEquals("Shawn4", updateEo.getLoginName());
		Assert.assertEquals("杨扬4", updateEo.getRealName());
		Assert.assertEquals("sun@wzitech.com", updateEo.getEmail());
		Assert.assertEquals("13011112222", updateEo.getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), updateEo.getCreatedDateTime());
		Assert.assertEquals(false, updateEo.isDeleted());
		
		updateEo = userMyBatisDAO.selectById(5L);
		Assert.assertEquals("Shawn5", updateEo.getLoginName());
		Assert.assertEquals("杨扬5", updateEo.getRealName());
		Assert.assertEquals("sun@wzitech.com", updateEo.getEmail());
		Assert.assertEquals("13011112222", updateEo.getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), updateEo.getCreatedDateTime());
		Assert.assertEquals(false, updateEo.isDeleted());
	}
	
	@Test
	public void deleteUser(){
		UserMyBatisEO user = userMyBatisDAO.selectById(6L);
		userMyBatisDAO.delete(user);
		
		UserMyBatisEO deletedUser = userMyBatisDAO.selectById(6L);
		Assert.assertNull(deletedUser);
		
	}
	
}
