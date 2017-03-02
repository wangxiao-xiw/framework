package com.hn658.${projectName}.dictionary.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hn658.${projectName}.dictionary.dao.db.IDictionaryDataDao;
import com.hn658.${projectName}.dictionary.dao.db.IDictionaryTypeDao;
import com.hn658.${projectName}.dictionary.dao.redis.IDictionaryRedisDao;
import com.hn658.${projectName}.dictionary.dto.DictionaryDTO;
import com.hn658.${projectName}.dictionary.entity.DictionaryDataEntity;
import com.hn658.${projectName}.dictionary.entity.DictionaryTypeEntity;
import com.hn658.${projectName}.dictionary.exception.DictionaryException;
import com.hn658.${projectName}.dictionary.service.IDictionaryService;
import com.hn658.framework.dataaccess.pagination.GenericPage;

/**
 * Created by baihai on 2015/7/31.
 */
@Service
public class DictionaryServiceImpl implements IDictionaryService{


    @Autowired
    private IDictionaryTypeDao dictionaryTypeDao;
    @Autowired
    private IDictionaryDataDao dictionaryDataDao;
    @Autowired
    private IDictionaryRedisDao dicionaryRedisDao;
    
    @Override
    public DictionaryTypeEntity getDictionaryTypeById(Long id){
        DictionaryTypeEntity type =null;
        if(id!=null){
            type= dictionaryTypeDao.selectById(id);
        }
        return type;
    }
    
    @Override
    public List<DictionaryTypeEntity> getDirectChildrenTypes(String parentId){
        if(StringUtils.isBlank(parentId)) {
            throw new DictionaryException(DictionaryException.DictionaryHasNoParentException);
        }
        Long parentIdl = Long.parseLong(parentId);
        return dictionaryTypeDao.getLowerDictionaryTypes(parentIdl);
    }
    
    @Override
    public DictionaryTypeEntity getDictionaryTypeByAlias(String alias){
    	Map<String,Object> query=new HashMap<String,Object>();
    	query.put("isLeaf", false);
    	query.put("isDeleted", false);
    	query.put("typeAlias", alias);
    	List<DictionaryTypeEntity> list=dictionaryTypeDao.selectByMap(query);
    	DictionaryTypeEntity dictionary=null;
    	if(list!=null&&!list.isEmpty()){
    		dictionary=list.get(0);
    	}
    	return dictionary;
    }
    
    @Override
    public GenericPage<DictionaryTypeEntity> getChildrenTypesPageByParentId(Map<String,Object>queryParam,int pageSize, int startIndex,String orderBy, Boolean isAsc){
    	if(queryParam==null){
            queryParam =  new HashMap<String, Object>();
        }
    	queryParam.put("isLeaf", true);
    	queryParam.put("isDeleted", false);
    	return dictionaryTypeDao.selectByMap(queryParam, pageSize, startIndex, orderBy, isAsc);
    }

    @Override
    public List<DictionaryTypeEntity> getIsLeafTypes(){
        return dictionaryTypeDao.getIsLeafTypes();
    }


    @Transactional
    @Override
    public void addDictionaryType(DictionaryTypeEntity entity){
        entity.setIsDeleted(false);
        this.validateDictionaryType(entity);

        entity.setIsLeaf(false);
        entity.setId(entity.getParentCode());
        this.modifyIsLeafOfDictionaryType(entity);

        entity.setIsLeaf(true);
        entity.setCreateDate(new Date());
        entity.setModifyDate(new Date());
        entity.setVersionNo(new Date().getTime());
        dictionaryTypeDao.insert(entity);
    }


    private boolean validateDictionaryType(DictionaryTypeEntity entity){
        if(StringUtils.isBlank(entity.getTypeName()) || StringUtils.isBlank(entity.getTypeAlias())) {
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
        }
        if(entity.getParentCode()==null) {
            throw new DictionaryException(DictionaryException.DictionaryHasNoParentException);
        }
        int count = dictionaryTypeDao.countDictType(entity);
        if (count > 0) {
            throw new DictionaryException(DictionaryException.DictionaryHasSameTypeOrAliasException);
        }

        return true;
    }


    @Transactional
    @Override
    public void modifyIsLeafOfDictionaryType(DictionaryTypeEntity entity) {
        if (entity == null) {
            throw new DictionaryException(DictionaryException.DictionaryEntityIsNullException);
        }
        if (entity.getId()==null) {
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
        }
        entity.setModifyDate(new Date());
        dictionaryTypeDao.updateIsLeafOfDictionaryType(entity);
    }


    @Override
    public Boolean hasDictionaryData(DictionaryTypeEntity entity){
        if (entity == null) {
            throw new DictionaryException(DictionaryException.DictionaryEntityIsNullException);
        }
        if (StringUtils.isBlank(entity.getTypeAlias())) {
            return false;
        }

        Map<String, Object> queryParam = new HashMap<String, Object>();
        queryParam.put("dictType",entity.getTypeAlias());
        int count = dictionaryDataDao.countByMap(queryParam);

        if(count != 0 ) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public void modifyDictionaryType(DictionaryTypeEntity entity){
        if(entity == null) {
            throw new DictionaryException(DictionaryException.DictionaryEntityIsNullException);
        }
        if (entity.getId()==null) {
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
        }
        if(StringUtils.isBlank(entity.getTypeName()) || StringUtils.isBlank(entity.getTypeAlias())) {
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
        }

        entity.setModifyDate(new Date());
        dictionaryTypeDao.updateDictionaryType(entity);
    }


    @Transactional
    @Override
    public void abolishDictionaryType(DictionaryTypeEntity entity) {
        if(entity == null) {
            throw new DictionaryException(DictionaryException.DictionaryEntityIsNullException);
        }
        if (entity.getId()==null) {
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
        }
        DictionaryTypeEntity typeEntity=dictionaryTypeDao.selectById(entity.getId());
        if(typeEntity == null) {
            throw new DictionaryException(DictionaryException.DictionaryEntityIsNullException);
        }
        
        dictionaryTypeDao.deleteDictionaryTypeById(typeEntity.getId(), new Date());
        DictionaryDataEntity deleteDictionary=new DictionaryDataEntity();
        deleteDictionary.setDictType(typeEntity.getTypeAlias());
        deleteDictionary.setIsDeleted(true);
        updata(deleteDictionary);
        
        Boolean hasLowerType = this.hasLowerDictionaryType(entity.getParentCode());
        if (!hasLowerType) {
            entity.setId(entity.getParentCode());
            entity.setIsLeaf(true);
            this.modifyIsLeafOfDictionaryType(entity);
        }
    }


    @Override
    public Boolean hasLowerDictionaryType(Long parentId) {
        if (parentId==null) {
            throw new DictionaryException(DictionaryException.DictionaryHasNoParentException);
        }

        List<DictionaryTypeEntity> list = dictionaryTypeDao.getLowerDictionaryTypes(parentId);
        if(list != null && list.size() != 0 ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getCountByMap(HashMap<String,Object> queryParam){
        if(queryParam==null){
            queryParam =  new HashMap<String, Object>();
        }
        queryParam.put("isDeleted",false);
        return dictionaryDataDao.countByMap(queryParam);
    }

    @Override
    public GenericPage<DictionaryDataEntity> selectByMapPage(Map<String, Object> queryParam, int pageSize, int startIndex, String orderBy, boolean isAsc){
        if(queryParam==null){
            queryParam =  new HashMap<String, Object>();
        }
        queryParam.put("isDeleted",false);
        return  dictionaryDataDao.selectByMap(queryParam,pageSize,startIndex,orderBy,isAsc);
    }
    
    @Override
    public List<DictionaryDataEntity> selectByMapDataList(Map<String,Object>queryParam,String orderBy,Boolean isAsc){
    	return dictionaryDataDao.selectByMap(queryParam, orderBy, isAsc);
    }
    
    @Override
    public List<DictionaryDTO>  selectByMapList(Map<String, Object> queryParam,String orderBy,boolean isAsc){
        if(queryParam==null){
            queryParam =  new HashMap<String, Object>();
        }
        queryParam.put("isDeleted",false);
        List<DictionaryDTO> entityList=dictionaryDataDao.selectListByType(queryParam, orderBy, isAsc);
        return entityList;
    }
    
	/**
	 * <p>查询</p>
	 * 通过type得到数据项
	 * 如果rides没有，去db查;并放入redis中
	 * 如果rides&db中都没有，返回空
	 * @param type
	 * @return 
	 */
	@Override
	public String getDictionaryByType(String type){
		String jsonData=dicionaryRedisDao.getDictionary(type);
		//redis中没有
		if(StringUtils.isEmpty(jsonData)){
			jsonData = this.updateDateFormDb(type);
		}
		return jsonData;
	}
    
    @Override
    @Transactional
    public String updateDateFormDb(String type){
		HashMap<String,Object> queryParam=new HashMap<String,Object>();
		queryParam.put("dictType", type);
		queryParam.put("isDeleted", false);
		List<DictionaryDTO> list=selectByMapList(queryParam,"value_order",true);
	    return	dicionaryRedisDao.setDictionary(type, list);    
    }

    @Override
      public void deletedDictionaryData(Long id){
    	DictionaryDataEntity entity=dictionaryDataDao.selectById(id);
    	if(entity!=null){
    		DictionaryDataEntity dictionaryData=new DictionaryDataEntity();
    		dictionaryData.setId(id);
    		dictionaryData.setIsDeleted(true);
    		dictionaryData.setModifyTime(new Date());
    		dictionaryDataDao.update(dictionaryData);
            updateDateFormDb(entity.getDictType());
            updateTypeVersionNo(entity.getDictType());
        }

    }


    @Override
    public void updata(DictionaryDataEntity entity){
    	if(StringUtils.isBlank(entity.getDictType())) {
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
        }
    	entity.setModifyTime(new Date());
        dictionaryDataDao.update(entity);
        updateDateFormDb(entity.getDictType());
        updateTypeVersionNo(entity.getDictType());
    }

    @Override
    @Transactional
    public DictionaryDataEntity insertDictionaryData(DictionaryDataEntity entity){
    	if(StringUtils.isBlank(entity.getDictType())) {
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
        }
    	Date time = new Date();
    	entity.setCreateTime(time);
    	entity.setModifyTime(time);
        dictionaryDataDao.insert(entity);
        updateDateFormDb(entity.getDictType());
        updateTypeVersionNo(entity.getDictType());
        return entity;
    }
    
    
    

    @Override
    public DictionaryDataEntity getDictionaryDataById(Long id){
        return dictionaryDataDao.selectById(id);
    }

    @Override
    public void updateTypeVersionNo(String type) {
       if(StringUtils.isBlank(type)){
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
       }
//       DictionaryTypeEntity typeEntity = dictionaryTypeDao.selectUniqueByProp("typeAlias",type);
       Map<String,Object> queryParam = new HashMap<String, Object>();
       queryParam.put("typeAlias",type);
       queryParam.put("isDeleted", false);
       List<DictionaryTypeEntity> ret =	dictionaryTypeDao.selectByMap(queryParam);
       if(ret==null || ret.isEmpty()){
    	   throw new DictionaryException(DictionaryException.DictionaryEntityIsNullException);
       }
       
       DictionaryTypeEntity typeEntity = ret.get(0);
       if(typeEntity==null){
           throw new DictionaryException(DictionaryException.DictionaryEntityIsNullException);
       }
        typeEntity.setVersionNo(new Date().getTime());
        dictionaryTypeDao.updateDictionaryType(typeEntity);
    }

    @Override
    public Boolean isNewestTypeVersion(String versionRather){
          Boolean isNewest = false;
          if(StringUtils.isNotBlank(versionRather)){
                Long versionRatherNo = Long.parseLong(versionRather);

                Long lastVersionNo = getNewestTypeVersionNo();
                if(lastVersionNo!=null&&versionRatherNo>lastVersionNo){
                    isNewest = true;
                }
          }
        return isNewest;
    }

     @Override
     public Long getNewestTypeVersionNo(){
         return dictionaryTypeDao.getLastVersionNo();
     }

     @Override
     public Map<String,String> getAllDictData(){
         Map<String,String> allDictData = new HashMap<String,String>();
         //查询所有子节点分类
        List<DictionaryTypeEntity> typeEntities =  getIsLeafTypes();
        if(typeEntities!=null&&typeEntities.size()>0){
            for (DictionaryTypeEntity type : typeEntities){
                allDictData.put(type.getTypeAlias(),getDictionaryByType(type.getTypeAlias()));
            }
        }
         return allDictData;
     }

    @Override
    public DictionaryDataEntity getDataByValueCodeAndType(String typeAlias,String valueCode) {
        if(StringUtils.isEmpty(valueCode)||StringUtils.isEmpty(typeAlias)){
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
        }
        Map<String,Object> queryParm = new HashMap<String, Object>();
        queryParm.put("dictType",typeAlias);
        queryParm.put("valueCode",valueCode);
        queryParm.put("isDeleted",false);
        DictionaryDataEntity dataEntity = dictionaryDataDao.selectOneByStatement(queryParm);
        return dataEntity;
    }

    @Override
    public DictionaryDataEntity getDataByValueNameAndType(String typeAlias,String valueName) {
        if(StringUtils.isEmpty(valueName)||StringUtils.isEmpty(typeAlias)){
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
        }
        Map<String,Object> queryParm = new HashMap<String, Object>();
        queryParm.put("dictType",typeAlias);
        queryParm.put("valueName",valueName);
        queryParm.put("isDeleted",false);
        DictionaryDataEntity dataEntity = dictionaryDataDao.selectOneByStatement(queryParm);
        return dataEntity;
    }


    @Override
    public Boolean isHadDataByValueCode(String typeAlias,String valueCode){
        Boolean isHadData = false;
        DictionaryDataEntity dataEntity = getDataByValueCodeAndType(typeAlias, valueCode);
        if(dataEntity!=null){
            isHadData = true;
        }
        return isHadData;
    }

    @Override
    public DictionaryTypeEntity getTypeByTypeAlias(String typeAlias) {
        return dictionaryTypeDao.selectUniqueByProp("typeAlias",typeAlias);
    }

    @Override
    public Map<String,String>  selectDataByType(String dictType){
        if(StringUtils.isEmpty(dictType)){
            throw new DictionaryException(DictionaryException.DictionaryCoreDataIsBlankException);
        }
        Map<String,Object> queryParm = new HashMap<String, Object>();
        queryParm.put("isDeleted",false);
        queryParm.put("dictType",dictType);
        List<String> dataTypeList = dictionaryTypeDao.getIsLeafNodesByType(queryParm);

        Map<String,String> allDictData = new HashMap<String,String>();
        //查询所有子节点分类
        if(dataTypeList!=null&&dataTypeList.size()>0){
            for (String type : dataTypeList){
                allDictData.put(type,getDictionaryByType(type));
            }
        }
        return allDictData;
    }

}
