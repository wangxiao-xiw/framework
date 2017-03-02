package com.hn658.${projectName}.dictionary.dao.redis;

import java.util.List;

import com.hn658.${projectName}.dictionary.dto.DictionaryDTO;

public interface IDictionaryRedisDao {

	/**
	 * 为type类型设置数据
	 * @param type
	 * @param dictionary
	 */
	public String setDictionary(String type,List<DictionaryDTO> list);
	
	public void deleteDictionary(String type);
	
	public String getDictionary(String type);
}
