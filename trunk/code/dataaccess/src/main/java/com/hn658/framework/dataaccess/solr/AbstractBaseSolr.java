package com.hn658.framework.dataaccess.solr;

import com.hn658.framework.dataaccess.pagination.GenericPage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by zdl on 15/11/2.
 */
public abstract class AbstractBaseSolr<T extends BaseSolrEntity> implements IBaseSolr<T>{

    @Autowired
    public SolrTemplate solrTemplate;

    protected Class<T> entityClass;

    public AbstractBaseSolr(){
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    @Override
    public void saveDocument(T bean) {
        solrTemplate.saveBean(bean);
        solrTemplate.commit();
    }
    @Override
    public void saveDocumentList(List<T> beanList ){
        solrTemplate.saveBeans(beanList);
        solrTemplate.commit();
    }

    @Override
    public void deleteByIndex(String index){
        solrTemplate.deleteById(index);
        solrTemplate.commit();
    }

    @Override
    public void deleteByIndexList(List<String> indexList){
        solrTemplate.deleteById(indexList);
        solrTemplate.commit();
    }

    @Override
    public void deleteByAll(){
        solrTemplate.delete(new SimpleQuery(new SimpleStringCriteria("*:*")));
        solrTemplate.commit();
    }

    @Override
    public void deleteQuery(List<SearchCriteria> searchCriterias){
        if(searchCriterias !=null && !searchCriterias.isEmpty()){
            SolrDataQuery query = new SimpleQuery(getCriteria(searchCriterias));
            solrTemplate.delete(query);
            solrTemplate.commit();
        }
    }

    @Override
    public T queryByIndex(String index){
        Criteria criteria = new Criteria(T.INDEX_ID).is(index);
        Query query = new SimpleQuery(criteria);
        return solrTemplate.queryForObject(query,entityClass);
    }

    @Override
    public T queryObject(List<SearchCriteria> searchCriterias){
        if(searchCriterias == null || searchCriterias.isEmpty()){
            return null;
        }
        Query query = new SimpleQuery(getCriteria(searchCriterias));
        return solrTemplate.queryForObject(query,entityClass);
    }

    @Override
    public List<T> queryObjectList(List<SearchCriteria> searchCriterias){
        if(searchCriterias == null || searchCriterias.isEmpty()){
            return null;
        }
        Query query = new SimpleQuery(getCriteria(searchCriterias));
        Long count = solrTemplate.count(query);
        if(count == null || count.equals(0L)){
            return null;
        }
        GenericPage<T> genericPage = queryPage(searchCriterias,count.intValue(),0,null,false);
        return genericPage.getData();
    }

    @Override
    public List<T> queryObjectList(List<SearchCriteria> searchCriterias,String sortBy,boolean isDesc){
        if(searchCriterias == null || searchCriterias.isEmpty()){
            return null;
        }
        Query query = new SimpleQuery(getCriteria(searchCriterias));
        Long count = solrTemplate.count(query);
        if(count == null || count.equals(0L)){
            return null;
        }
        GenericPage<T> genericPage = queryPage(searchCriterias,count.intValue(),0,sortBy,isDesc);
        return genericPage.getData();
    }

    public GenericPage<T> queryPage(List<SearchCriteria> searchCriterias,int pageSize,int start){
        return this.queryPage(searchCriterias,pageSize,start,null,false);
    }

    @Override
    public GenericPage<T> queryPage(List<SearchCriteria> searchCriterias,int pageSize,int start,String sortBy,boolean isDesc){
        if(searchCriterias == null || searchCriterias.isEmpty()){
            return null;
        }
        Criteria criteria = this.getCriteria(searchCriterias);
        SimpleQuery simpleQuery = new SimpleQuery(criteria);
        //分页
        pageSize = (pageSize == 0 ? 10 : pageSize);
        int pageNo = start/pageSize;
        //排序
        Sort sort = null;
        if(StringUtils.isNotBlank(sortBy)){
             sort = new Sort(isDesc?Sort.Direction.DESC:Sort.Direction.ASC,sortBy);
        }
        PageRequest pageRequest = new PageRequest(pageNo,pageSize,sort);
        simpleQuery.setPageRequest(pageRequest);

        Page<T> page = solrTemplate.queryForPage(simpleQuery, entityClass);

        GenericPage<T> genericPage = null;
        if(page != null && page.hasContent()){
            genericPage = new GenericPage<T>(start,page.getTotalElements(),page.getSize(),page.getContent());
        }else {
            genericPage = new GenericPage<T>(start,0L,pageSize,null);
        }
        return genericPage;
    }

    @Override
    public void partialUpdate(PartialUpdate partialUpdate) {
        solrTemplate.saveBean(partialUpdate);
        solrTemplate.commit();
    }

    /**
     * 用于解析封装的查询条件
     * @param searchCriterias
     * @return
     */
    private Criteria getCriteria(List<SearchCriteria> searchCriterias){
        // 检查参数是否为null或者元素长度为0
        // 如果是抛出异常
        Validate.notEmpty(searchCriterias);

        Criteria criteria = null;
        for(SearchCriteria search: searchCriterias){
            switch(search.logic){
                case equal:
                    if(criteria == null){
                        criteria = new Criteria(search.field).is(search.value);
                    }else {
                        criteria.and(new Criteria(search.field).is(search.value));
                    }
                    break;
                case notEqual:
                    if(criteria == null){
                        criteria = new Criteria(search.field).is(search.value).not();
                    }else {
                        criteria.and(new Criteria(search.field).is(search.value).not());
                    }
                    break;
                case greaterThan:
                    if(criteria == null){
                        criteria = new Criteria(search.field).greaterThan(search.value);
                    }else {
                        criteria.and(new Criteria(search.field).greaterThan(search.value));
                    }
                    break;
                case greaterThanEqual:
                    if(criteria == null){
                        criteria = new Criteria(search.field).greaterThanEqual(search.value);
                    }else {
                        criteria.and(new Criteria(search.field).greaterThanEqual(search.value));
                    }
                    break;
                case lessThan:
                    if(criteria == null){
                        criteria = new Criteria(search.field).lessThan(search.value);
                    }else {
                        criteria.and(new Criteria(search.field).lessThan(search.value));
                    }
                    break;
                case lessThanEqual:
                    if(criteria == null){
                        criteria = new Criteria(search.field).lessThanEqual(search.value);
                    }else {
                        criteria.and(new Criteria(search.field).lessThanEqual(search.value));
                    }
                    break;
            }
        }
        return criteria;
    }


}
