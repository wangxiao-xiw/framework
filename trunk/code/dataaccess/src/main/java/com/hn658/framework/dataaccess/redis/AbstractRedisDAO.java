/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		AbstractRedisDAO
 *	包	名：		com.wzitech.chaos.framework.server.dataaccess.redis
 *	项目名称：	chaos-dataaccess
 *	作	者：		Shawn
 *	创建时间：	2012-7-10
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-7-10 下午11:56:55
 * 				
 ************************************************************************************/
package com.hn658.framework.dataaccess.redis;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.JacksonHashMapper;

import com.hn658.framework.shared.entity.BaseEntity;
import com.hn658.framework.shared.utils.GenericsUtils;

/**
 * @author Shawn
 *
 */
public abstract class AbstractRedisDAO<T extends BaseEntity> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Class<T> entityClass;
	
	/**
	 * 字符类型模板
	 */
	protected StringRedisTemplate template;

	/**
	 * 字符类型操作
	 */
	protected ValueOperations<String, String> valueOps;
	
	/**
	 * set类型操作
	 */
	protected SetOperations<String, String> setOps;
	
	/**
	 * zSet类型操作
	 */
	protected ZSetOperations<String, String> zSetOps;
	
	/**
	 * List类型操作
	 */
	protected ListOperations<String, String> listOps;
	
	protected HashMapper<T, String, String> mapper;
	
	@SuppressWarnings("unchecked")
	public AbstractRedisDAO(){
		// 通过范型反射，取得在子类中定义的class.
		this.entityClass = GenericsUtils.getSuperClassGenricType(getClass());
		
		this.mapper = new DecoratingStringHashMapper<T>(
				new JacksonHashMapper<T>(this.entityClass));	
	}
	
	@PostConstruct
	private void afterInitialization(){
		this.valueOps = this.template.opsForValue();
		this.setOps = this.template.opsForSet();
		this.zSetOps = this.template.opsForZSet();
		this.listOps = this.template.opsForList();
	}
	
	/**
	 * @return the template
	 */
	public StringRedisTemplate getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(StringRedisTemplate template) {
		this.template = template;
	}
}
