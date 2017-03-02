package com.hn658.framework.dataaccess.solr.entity;

import com.hn658.framework.dataaccess.solr.BaseSolrEntity;
import org.apache.solr.client.solrj.beans.Field;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zdl on 15/11/10.
 */
public class Users extends BaseSolrEntity implements UsersField {

    @Field(ID)
    private Long id;

    @Field(NAME)
    private String name;

    @Field(HEIGHT)
    private Float height;

    @Field(AGE)
    private Integer age;

    @Field(MONEY)
    private BigDecimal money;

    @Field(CREATETIME)
    private Date createTime;

    @Field(ISDELETED)
    private Boolean isDeleted;

    /**
     * 默认构造
     */
    public Users() {
    }

    /**
     * Solr有参构造方法<br/>
     * (推荐,数据只保存在solr中时用此构造)<br/>
     * 创建对象时,指明indexType,并用UUID拼接成生成indexId;
     * @param Prefix 即 indexType (文档类型)
     */
    public Users(String Prefix) {
        super(Prefix);
    }

    /**
     * Solr有参构造方法<br/>
     * (推荐,数据从db保存到solr中时用此构造)<br/>
     * 创建对象时,指明indexType和indexId;
     * @param Prefix 即 indexType
     * @param id   用于生成 indexId = indexType + id
     */
    public Users(String Prefix, String id) {
        super(Prefix, id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
