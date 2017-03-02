package com.hn658.${projectName}.dictionary.dao.redis.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.hn658.${projectName}.common.utils.RedisKeyHelper;
import com.hn658.${projectName}.dictionary.dao.redis.IDictionaryRedisDao;
import com.hn658.${projectName}.dictionary.dto.DictionaryDTO;
import com.hn658.framework.dataaccess.redis.AbstractRedisDAO;
import com.hn658.framework.shared.entity.BaseEntity;
import com.hn658.framework.shared.utils.JsonMapper;

@Repository
public class DictionaryRedisDaoImpl  extends AbstractRedisDAO<BaseEntity> implements IDictionaryRedisDao {
	
	@Autowired
	@Qualifier("redisTemplate")
	@Override
	public void setTemplate(StringRedisTemplate template) {
		super.template = template;
	}
	
	private JsonMapper jsonMapper = new JsonMapper();
	
	/**
	 * <p>修改</p>
	 * 为type类型设置最新数据
	 * @param type
	 * @param list 新的数据集合（List格式）
	 */
	@Override
	public String setDictionary(String type,List<DictionaryDTO> list){
		String json  = jsonMapper.toJson(list);
		valueOps.set(RedisKeyHelper.dictionaryKey(type), json);
		return json;
	}
	
	/**
	 * <p>删除</p>
	 * @param type
	 */
	@Override
	public void deleteDictionary(String type){
		template.delete(RedisKeyHelper.dictionaryKey(type));
	}
	
	

	@Override
	public String getDictionary(String type){
		return valueOps.get(RedisKeyHelper.dictionaryKey(type));
	}
	
	
	
	
	
}
