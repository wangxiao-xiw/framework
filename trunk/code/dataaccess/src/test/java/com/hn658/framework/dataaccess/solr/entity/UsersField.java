package com.hn658.framework.dataaccess.solr.entity;

import com.hn658.framework.dataaccess.solr.SolrField;

/**
 * Created by zdl on 15/11/10.
 */
public interface UsersField extends SolrField{
    /**
     * Prefix 用于指明 属性所在的 项目_系统_模块_*** <br/>
     */
    String Prefix         = "hn658_tt_Users";

    /**
     * 对应solr中的属性字段<br/>
     * -------------------------------
     *     对象属性       solr中字段名
     */
    String ID           = Prefix+"_id";
    String NAME         = Prefix+"_name";
    String HEIGHT       = Prefix+"_height";
    String AGE          = Prefix+"_age";
    String MONEY        = Prefix+"_money";
    String CREATETIME   = Prefix+"_createTime";
    String ISDELETED    = Prefix+"_isDeleted";
}
