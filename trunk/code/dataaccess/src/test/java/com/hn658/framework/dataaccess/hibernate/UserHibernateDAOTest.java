/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		UserHibernateDAOTest
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.hibernate
 *	项目名称：	chaos-dataaccess 
 *	作	者：		Shawn
 *	创建时间：	2012-5-14
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-5-14 下午6:19:52
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.hibernate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

import com.hn658.framework.dataaccess.hibernate.dao.IUserHibernateDAO;
import com.hn658.framework.dataaccess.hibernate.entity.UserHibernateEO;
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
@ActiveProfiles("hibernate")
public class UserHibernateDAOTest {
	@Autowired
	IUserHibernateDAO userHibernateDAO;
	
	@Test
	public void queryCount(){
		int userCount = userHibernateDAO.count();
		Assert.assertEquals(6, userCount);
	}
	
	/*@Test
	public void selectAll(){
		List<UserHibernateEO> userList = userHibernateDAO.selectAll("tel", false);
		
		Assert.assertEquals("68981228888", userList.get(0).getTel());
		Assert.assertEquals("58981228888", userList.get(1).getTel());
		Assert.assertEquals("48981228888", userList.get(2).getTel());
		Assert.assertEquals("38981228888", userList.get(3).getTel());
		Assert.assertEquals("28981228888", userList.get(4).getTel());
		Assert.assertEquals("18981228888", userList.get(5).getTel());
	}*/
	
	@Test
	public void selectByHql(){
		String hql = "from UserHibernateEO u where u.loginName = ?";
		List<UserHibernateEO> userList = userHibernateDAO.selectByHql(hql,"Shawn3");
		
		Assert.assertEquals("Shawn3", userList.get(0).getLoginName());
		Assert.assertEquals("杨扬3", userList.get(0).getRealName());
		Assert.assertEquals("Shawn.Young3@WZITech.com", userList.get(0).getEmail());
		Assert.assertEquals("38981228888", userList.get(0).getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), userList.get(0).getCreatedDateTime());
		Assert.assertEquals(false, userList.get(0).isDeleted());
	}
	
	@Test
	public void selectWithPage(){
		GenericPage<UserHibernateEO> pageList= userHibernateDAO.select(4, 5, "tel", false);
		List<UserHibernateEO> userList = pageList.getData();
		
		Assert.assertEquals(2, pageList.getTotalPageCount());
		Assert.assertEquals(6, pageList.getTotalCount());
		Assert.assertEquals(4, pageList.getPageSize());
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
	public void queryUser(){
		UserHibernateEO user = userHibernateDAO.selectById(1L);
		Assert.assertEquals("Shawn1", user.getLoginName());
		Assert.assertEquals("杨扬1", user.getRealName());
		Assert.assertEquals("Shawn.Young1@WZITech.com", user.getEmail());
		Assert.assertEquals("18981228888", user.getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), user.getCreatedDateTime());
		Assert.assertEquals(false, user.isDeleted());
	}
	
	@Test
	public void selectUniqueByProp(){
		UserHibernateEO user = userHibernateDAO.selectUniqueByProp("loginName", "Shawn1");
		Assert.assertNotNull(user);
		Assert.assertEquals("Shawn1", user.getLoginName());
		Assert.assertEquals("杨扬1", user.getRealName());
		Assert.assertEquals("Shawn.Young1@WZITech.com", user.getEmail());
		Assert.assertEquals("18981228888", user.getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), user.getCreatedDateTime());
		Assert.assertEquals(false, user.isDeleted());
	}
	
	@Test
	public void selectByProp(){
		UserHibernateEO user = new UserHibernateEO();
		user.setLoginName("Shawn1");
		user.setRealName("测试");
		user.setEmail("dev@wzitech.com");
		user.setTel("1506666666");
		user.setCreatedDateTime(Timestamp.valueOf("2012-05-14 18:18:18"));
		user.setDeleted(false);
		
		userHibernateDAO.save(user);
		
		List<UserHibernateEO> userList = new ArrayList<UserHibernateEO>();
		userList = userHibernateDAO.selectByProp("loginName", "Shawn1", "tel",true);
		
		Assert.assertEquals("Shawn1", userList.get(1).getLoginName());
		Assert.assertEquals("杨扬1", userList.get(1).getRealName());
		Assert.assertEquals("Shawn.Young1@WZITech.com", userList.get(1).getEmail());
		Assert.assertEquals("18981228888", userList.get(1).getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-13 18:18:18"), userList.get(1).getCreatedDateTime());
		Assert.assertEquals(false, userList.get(1).isDeleted());
		
		Assert.assertEquals("Shawn1", userList.get(0).getLoginName());
		Assert.assertEquals("测试", userList.get(0).getRealName());
		Assert.assertEquals("dev@wzitech.com", userList.get(0).getEmail());
		Assert.assertEquals("1506666666", userList.get(0).getTel());
		Assert.assertEquals(Timestamp.valueOf("2012-05-14 18:18:18"), userList.get(0).getCreatedDateTime());
		Assert.assertEquals(false, userList.get(0).isDeleted());
	}
	
	@Test
	public void saveUser(){
		UserHibernateEO userEO = new UserHibernateEO();
		userEO.setLoginName("Dev");
		userEO.setRealName("sun");
		userEO.setEmail("sun@wzitech.com");
		userEO.setTel("1506666666");
		userEO.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		userEO.setDeleted(false);
		
		userHibernateDAO.save(userEO);
		
		UserHibernateEO savedUser = userHibernateDAO.selectUniqueByProp("email", "sun@wzitech.com");
		Assert.assertEquals("Dev", savedUser.getLoginName());
	}
	
	@Test
	public void batchInsert(){
		List<UserHibernateEO> userList = new ArrayList<UserHibernateEO>();
		UserHibernateEO user1 = new UserHibernateEO();
		user1.setLoginName("Dev1");
		user1.setRealName("测试1");
		user1.setEmail("dev@wzitech1.com");
		user1.setTel("15088888888");
		user1.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		user1.setDeleted(false);
		UserHibernateEO user2 = new UserHibernateEO();
		user2.setLoginName("Dev2");
		user2.setRealName("测试2");
		user2.setEmail("dev@wzitech2.com");
		user2.setTel("15077777777");
		user2.setCreatedDateTime(new Timestamp(System.currentTimeMillis()));
		user2.setDeleted(false);
		userList.add(user1);
		userList.add(user2);
		
		userHibernateDAO.batchInsert(userList);
		UserHibernateEO savedUser = userHibernateDAO.selectUniqueByProp("tel", "15088888888");
		Assert.assertEquals("Dev1", savedUser.getLoginName());
		savedUser = userHibernateDAO.selectUniqueByProp("realName", "测试2");
		Assert.assertEquals("Dev2", savedUser.getLoginName());
	}
	
	/*@Test
	public void update(){
		UserHibernateEO user = userHibernateDAO.selectById(6L);
		user.setLoginName("Sun6");
		
		userHibernateDAO.update(user);
		user = userHibernateDAO.selectById(6L);
		Assert.assertEquals("Sun6", user.getLoginName());
	}*/
	
	@Test
	public void batchUpdate(){
		UserHibernateEO user1 = userHibernateDAO.selectById(1L);
		user1.setEmail("sunchengfei1@163.com");
		
		UserHibernateEO user2 = userHibernateDAO.selectById(2L);
		user2.setRealName("孙成飞2");
		
		List<UserHibernateEO> listUser = new ArrayList<UserHibernateEO>();
		listUser.add(user1);
		listUser.add(user2);
		
		userHibernateDAO.batchUpdate(listUser);
		
		user1 = userHibernateDAO.selectById(1L);
		user2 = userHibernateDAO.selectById(2L);
		Assert.assertEquals("sunchengfei1@163.com", user1.getEmail());
		Assert.assertEquals("孙成飞2", user2.getRealName());
	}
	
	@Test
	public void deleteUser(){
		UserHibernateEO user = userHibernateDAO.selectById(6L);
		userHibernateDAO.delete(user);
		
		UserHibernateEO deletedUser = userHibernateDAO.selectById(6L);
		Assert.assertNull(deletedUser);
	}
	
	@Test
	public void deleteById(){
		userHibernateDAO.deleteById(5L);
		
		UserHibernateEO deletedUser = userHibernateDAO.selectById(5L);
		Assert.assertNull(deletedUser);
	}
	
	@Test
	public void batchDelete(){
		UserHibernateEO user1 = userHibernateDAO.selectById(3L);
		UserHibernateEO user2 = userHibernateDAO.selectById(4L);
		
		List<UserHibernateEO> userList = new ArrayList<UserHibernateEO>();
		userList.add(user1);
		userList.add(user2);
		
		userHibernateDAO.batchDelete(userList);
		
		user1 = userHibernateDAO.selectById(3L);
		user2 = userHibernateDAO.selectById(4L);
		
		Assert.assertNull(user1);
		Assert.assertNull(user2);
	}
}
