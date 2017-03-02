package com.hn658.${projectName}.dictionary.dao.db;

import java.util.List;
import java.util.Map;

import com.hn658.${projectName}.dictionary.dto.DictionaryDTO;
import com.hn658.${projectName}.dictionary.entity.DictionaryDataEntity;
import com.hn658.framework.dataaccess.mybatis.IMyBatisBaseDAO;

public interface IDictionaryDataDao extends IMyBatisBaseDAO<DictionaryDataEntity, Long>{
	
	/**
	 * 根据条件得出只有key、value的数据项
	 * @param queryParam
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	public List<DictionaryDTO> selectListByType( Map<String, Object> queryParam, String orderBy, boolean isAsc);
	
	/**
	 * 更新对象
	 */
	public int update(DictionaryDataEntity entity);

    public DictionaryDataEntity selectOneByStatement(Map<String, Object> queryParam);

    /**
     * 根据类型查询所有的字典数据
     * @param queryParam
     * @return
     */
    public List<DictionaryDataEntity> selectDataByDictType(Map<String, Object> queryParam);
}
