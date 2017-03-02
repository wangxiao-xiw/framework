package com.hn658.framework.dataaccess.solr;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

import com.hn658.framework.shared.entity.IEntity;
import com.hn658.framework.shared.utils.UUIDUtils;

/**
 * <p>Solr对象抽象基类</p>
 * 每一个solr对象都必须有 indexId(全局唯一索引), index_Type(文档类型) 两个属性值.<br/>
 * Created by zdl on 15/10/30.
 */
public abstract class BaseSolrEntity implements SolrField,Serializable {

    /**
     * 索引<br/>
     * Solr中全局唯一
     */
    @Id
    @Field(INDEX_ID)
    private String indexId;

    /**
     * 文档类型
     */
    @Field(INDEX_TYPE)
    private String indexType;

    /**
     * Solr无参构造方法<br/>
     * 创建对象时,不指明ID
     */
    public BaseSolrEntity(){
    }

    /**
     * Solr有参构造方法<br/>
     * (推荐,数据只保存在solr中时用此构造)<br/>
     * 创建对象时,指明indexType,并用UUID拼接成生成indexId;
     * @param Prefix 即 indexType (文档类型)
     */
    public BaseSolrEntity(String Prefix) {
        this.indexType = Prefix;
        this.indexId = this.indexType+"_"+ UUIDUtils.getUUID();
    }

    /**
     * Solr有参构造方法<br/>
     * (推荐,数据从db保存到solr中时用此构造)<br/>
     * 创建对象时,指明indexType和indexId;
     * @param Prefix 即 indexType
     * @param id   用于生成 indexId = indexType + id
     */
    public BaseSolrEntity(String Prefix,String id){
        this.indexType = Prefix;
        this.indexId = this.indexType+"_"+id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass().getPackage() != obj.getClass().getPackage()) {
            return false;
        }
        if (IEntity.class.isAssignableFrom((obj.getClass()))) {
            final BaseSolrEntity other = (BaseSolrEntity) obj;
            if (indexId == null) {
                if (other != null) {
                    return false;
                }
            } else if (!indexId.equals(other.getIndexId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }
}
