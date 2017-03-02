package com.hn658.${projectName}.dictionary.dao.db;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hn658.${projectName}.dictionary.entity.DictionaryTypeEntity;
import com.hn658.framework.dataaccess.mybatis.IMyBatisBaseDAO;

/**
 * Created by baihai on 2015/7/31.
 */
public interface IDictionaryTypeDao extends IMyBatisBaseDAO<DictionaryTypeEntity, Long> {

    public List<DictionaryTypeEntity> getLowerDictionaryTypes(Long parentId);

    public int countDictType(DictionaryTypeEntity entity);

    public void updateIsLeafOfDictionaryType(DictionaryTypeEntity entity);

    public void updateDictionaryType(DictionaryTypeEntity entity);

    public void deleteDictionaryTypeById(Long id,   Date modifyDate);

    public Long getLastVersionNo();

    public List<DictionaryTypeEntity> getIsLeafTypes();

    public List<String> getIsLeafNodesByType(Map<String, Object> map);
}
