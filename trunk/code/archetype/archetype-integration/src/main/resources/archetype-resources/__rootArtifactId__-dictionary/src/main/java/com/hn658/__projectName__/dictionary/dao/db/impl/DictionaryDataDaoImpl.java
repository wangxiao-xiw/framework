package com.hn658.${projectName}.dictionary.dao.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hn658.${projectName}.dictionary.dao.db.IDictionaryDataDao;
import com.hn658.${projectName}.dictionary.dto.DictionaryDTO;
import com.hn658.${projectName}.dictionary.entity.DictionaryDataEntity;
import com.hn658.${projectName}.dictionary.exception.DictionaryException;
import com.hn658.framework.dataaccess.mybatis.AbstractMyBatisDAO;

@Repository
public class DictionaryDataDaoImpl  extends AbstractMyBatisDAO<DictionaryDataEntity, Long> implements IDictionaryDataDao {
	
	
	public List<DictionaryDTO> selectListByType( Map<String, Object> queryParam, String orderBy, boolean isAsc) {
		if(null == queryParam){
			queryParam = new HashMap<String, Object>();
		}
		queryParam.put("ORDERBY", orderBy);
		if(isAsc){
			queryParam.put("ORDER", "ASC");
		}else{
			queryParam.put("ORDER", "DESC");
		}
		return this.getSqlSession().selectList(this.mapperNamespace + ".selectListByType", queryParam);
	}
	
	public int update(DictionaryDataEntity entity){
		if(entity==null){
			throw new DictionaryException(DictionaryException.DictionaryEntityIsNullException);
		}
		return this.getSqlSession().update(this.mapperNamespace + ".updateById", entity);
	}


    @Override
    public DictionaryDataEntity selectOneByStatement(Map<String, Object> queryParam) {
        return this.getSqlSession().selectOne(this.mapperNamespace + ".selectOneByStatement", queryParam);
    }


    @Override
    public List<DictionaryDataEntity> selectDataByDictType(Map<String, Object> queryParam) {
        return this.getSqlSession().selectList(this.mapperNamespace + ".selectDataByType", queryParam);
    }
}
