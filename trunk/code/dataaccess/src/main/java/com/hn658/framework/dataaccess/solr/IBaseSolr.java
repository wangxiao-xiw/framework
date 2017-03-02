package com.hn658.framework.dataaccess.solr;

import com.hn658.framework.dataaccess.pagination.GenericPage;
import org.springframework.data.solr.core.query.PartialUpdate;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zdl on 15/11/2.
 */
public interface IBaseSolr<T extends BaseSolrEntity> {
    /**
     * 保存实体对象
     * @param bean
     */
    void saveDocument(T bean);

    /**
     * 批量保存实体对象
     * @param beanList
     */
    void saveDocumentList(List<T> beanList);

    /**
     * 根据索引index删除
     * @param index
     */
    void deleteByIndex(String index);

    /**
     * 根据索引index批量删除
     * @param indexList
     */
    void deleteByIndexList(List<String> indexList);

    /**
     * 全部删除
     */
    void deleteByAll();

    /**
     * 根据查询条件删除
     * 条件之间是and的关系
     * @param searchCriterias
     */
    void deleteQuery(List<SearchCriteria> searchCriterias);

    /**
     * 根据索引index获取对象
     * @param index
     * @return
     */
    T queryByIndex(String index);

    /**
     * 根据查询条件获取对象
     * @param searchCriterias
     * @return
     */
    T queryObject(List<SearchCriteria> searchCriterias);

    /**
     * 排序查询符合条件的集合
     * @param searchCriterias
     * @param sortBy
     * @param isDesc
     * @return
     */
    List<T> queryObjectList(List<SearchCriteria> searchCriterias,String sortBy,boolean isDesc);

    /**
     * 查询符合条件的集合
     * @param searchCriterias
     * @return
     */
    List<T> queryObjectList(List<SearchCriteria> searchCriterias);

    /**
     * 分页查询
     * @param searchCriterias 查询条件
     * @param pageSize 页宽
     * @param start    开始行
     * @return
     */
    GenericPage<T> queryPage(List<SearchCriteria> searchCriterias,int pageSize,int start);

    /**
     * 分页查,排序查询
     * @param searchCriterias 查询条件
     * @param pageSize 页宽(每页几条记录)
     * @param start    开始行序号
     * @param sortBy   根据什么字段排序
     * @param isDesc   是否升序
     * @return
     */
    GenericPage<T> queryPage(List<SearchCriteria> searchCriterias,int pageSize,int start,String sortBy,boolean isDesc);

    /**
     * 局部字段更新
     * @param partialUpdate
     */
    public void partialUpdate(PartialUpdate partialUpdate);
}
