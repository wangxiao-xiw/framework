package com.hn658.framework.dataaccess.solr;

/**
 * Created by zdl on 15/11/11.
 */
public class SearchCriteria {

    public String field;    //属性

    public LOGIC logic;     //逻辑词

    public Object value;    //值

    /**
     * 查询条件的构造方法
     * @param field 属性
     * @param logic 逻辑关系
     * @param value 值
     */
    public SearchCriteria(String field, LOGIC logic, Object value) {
        this.field = field;
        this.logic = logic;
        this.value = value;
    }

    public enum LOGIC{
        /**
         * 等于
         */
        equal,
        /**
         * 不等于
         */
        notEqual,
        /**
         * 大于
         */
        greaterThan,
        /**
         * 大于等于
         */
        greaterThanEqual,
        /**
         * 小于
         */
        lessThan,
        /**
         * 小于等于
         */
        lessThanEqual
    }
}
