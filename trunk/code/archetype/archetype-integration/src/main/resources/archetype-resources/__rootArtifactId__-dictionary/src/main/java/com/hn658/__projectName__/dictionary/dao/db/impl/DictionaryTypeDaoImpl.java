package com.hn658.${projectName}.dictionary.dao.db.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hn658.${projectName}.dictionary.dao.db.IDictionaryTypeDao;
import com.hn658.${projectName}.dictionary.entity.DictionaryTypeEntity;
import com.hn658.framework.dataaccess.mybatis.AbstractMyBatisDAO;

/**
 * Created by baihai on 2015/7/31.
 */
@Repository
public class DictionaryTypeDaoImpl extends AbstractMyBatisDAO<DictionaryTypeEntity, Long> implements IDictionaryTypeDao{

    @Override
    public List<DictionaryTypeEntity> getLowerDictionaryTypes(Long parentId) {
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("isDeleted", false);
        map.put("parentCode", parentId);
        return getSqlSession().selectList(this.mapperNamespace + ".getLowerDictionaryTypes", map);
    }

    @Override
    public List<DictionaryTypeEntity> getIsLeafTypes() {
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("isDeleted", false);
        map.put("isLeaf", true);
        return getSqlSession().selectList(this.mapperNamespace + ".getIsLeafTypes", map);
    }

    @Override
    public List<String> getIsLeafNodesByType(Map<String, Object> map) {
        return getSqlSession().selectList(this.mapperNamespace + ".selectLeafNodeByType", map);
    }


    @Override
    public int countDictType(DictionaryTypeEntity entity) {
        return (Integer) this.getSqlSession().selectOne(this.mapperNamespace + ".countDictType", entity);
    }


    @Override
    public void updateIsLeafOfDictionaryType(DictionaryTypeEntity entity) {
        getSqlSession().update(this.mapperNamespace + ".updateIsLeafOfDictionaryType", entity);
    }

    @Override
    public void updateDictionaryType(DictionaryTypeEntity entity) {
        getSqlSession().update(this.mapperNamespace + ".updateDictionaryType", entity);
    }


    @Override
    public void deleteDictionaryTypeById(Long id,  Date modifyDate) {
        Map<String, Object> map = new HashMap<String, Object>(4);
        map.put("isDeleted", true);
        map.put("id", id);
        map.put("modifyDate", modifyDate);
        getSqlSession().update(this.mapperNamespace + ".deleteDictionaryTypeById", map);
    }


    @Override
    public Long getLastVersionNo() {
        return (Long) this.getSqlSession().selectOne(this.mapperNamespace + ".getLastVersionNo");
    }





}
