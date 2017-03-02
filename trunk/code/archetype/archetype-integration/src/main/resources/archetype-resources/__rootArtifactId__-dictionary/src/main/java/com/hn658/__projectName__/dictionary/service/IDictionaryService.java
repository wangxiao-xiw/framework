package com.hn658.${projectName}.dictionary.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hn658.${projectName}.dictionary.dto.DictionaryDTO;
import com.hn658.${projectName}.dictionary.entity.DictionaryDataEntity;
import com.hn658.${projectName}.dictionary.entity.DictionaryTypeEntity;
import com.hn658.framework.dataaccess.pagination.GenericPage;

/**
 * Created by baihai on 2015/7/31.
 */
public interface IDictionaryService {

    public List<DictionaryTypeEntity> getDirectChildrenTypes(String parentId);

    public void addDictionaryType(DictionaryTypeEntity entity);

    public void modifyIsLeafOfDictionaryType(DictionaryTypeEntity entity);

    public Boolean hasDictionaryData(DictionaryTypeEntity entity);

    public void modifyDictionaryType(DictionaryTypeEntity entity);

    public Boolean hasLowerDictionaryType(Long parentId);

    public void abolishDictionaryType(DictionaryTypeEntity entity);
   
    /**
     * 通过id得到类型
     * @param id
     * @return
     */
    public DictionaryTypeEntity getDictionaryTypeById(Long id);
    
    /**
     * 通过别名查询类型
     * @param alias
     * @return
     */
    public DictionaryTypeEntity getDictionaryTypeByAlias(String alias);
    
    /**
     * 通过父id得到子类型集合（分页）
     * @param queryParam
     * @param pageSize
     * @param startIndex
     * @param orderBy
     * @param isAsc
     * @return
     */
    public GenericPage<DictionaryTypeEntity> getChildrenTypesPageByParentId(Map<String,Object>queryParam,int pageSize, int startIndex,String orderBy, Boolean isAsc);
    /**
     * 无分页查询记录数
     * @param queryParam
     * @return
     */
    public int getCountByMap(HashMap<String,Object> queryParam);

    /**
     * 分页查询数据项
     * @param queryParam
     * @param pageSize
     * @param startIndex
     * @param orderBy
     * @param isAsc
     * @return
     */
    public GenericPage<DictionaryDataEntity> selectByMapPage(Map<String, Object> queryParam, int pageSize, int startIndex, String orderBy, boolean isAsc);

    /**
     * 废除数据项
     * @param id
     */
    public void deletedDictionaryData(Long id);

    /**
     * 更新数据项
     * @param entity
     */
    public void updata(DictionaryDataEntity entity);

    /**
     * 新增数据项
     * @param entity
     * @return
     */
    public DictionaryDataEntity insertDictionaryData(DictionaryDataEntity entity);

    /**
     * 根据id得到数据项
     * @param id
     * @return
     */
    public DictionaryDataEntity getDictionaryDataById(Long id);

    /**
     * 获取Entiy 集合
     * @param queryParam
     * @param orderBy
     * @param isAsc
     * @return
     */
    public List<DictionaryDataEntity> selectByMapDataList(Map<String,Object>queryParam,String orderBy,Boolean isAsc);
    
    /**
     * 获取DTO 集合
     * @param queryParam
     * @param orderBy
     * @param isAsc
     * @return
     */
    public List<DictionaryDTO>  selectByMapList(Map<String, Object> queryParam,String orderBy,boolean isAsc);

    /**
     * 更新分类版本号
     */
    public void updateTypeVersionNo(String type);

    /**
     * 比较是否是最新版本
     * @param versionRather
     * @return
     */
    public Boolean isNewestTypeVersion(String versionRather);

    /**
     * 获取最新版本号
     * @return
     */
    public Long getNewestTypeVersionNo();

    /**
     * 获取所有子节点
     * @return
     */
    public List<DictionaryTypeEntity> getIsLeafTypes();
    
	/**
	 * 通过type得到数据项
	 * @param type
	 * @return
	 */
	public String getDictionaryByType(String type);
	
	public String updateDateFormDb(String type);

    /**
     * 获取所有数据字典数据
     * @return
     */
    public Map<String,String> getAllDictData();


    /**
     * 通过值code获取字典数据
     */
    public DictionaryDataEntity getDataByValueCodeAndType(String typeAlias,String valueCode);

    public Boolean isHadDataByValueCode(String typeAlias,String valueCode);

    public DictionaryDataEntity getDataByValueNameAndType(String typeAlias,String valueName);

    public DictionaryTypeEntity getTypeByTypeAlias(String typeAlias);

    public Map<String,String>  selectDataByType(String dictType);

}
