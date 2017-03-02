package com.hn658.framework.cache.base;

/**
 * 缓存类型
 */
public enum CacheType {
    Local(1, "本地"),
    Redis(2, "Redis");

    private Integer code;

    private String name;

    CacheType(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据code值获取对应的枚举
     * @param code
     * @return
     */
    public static CacheType getTypeByCode(Integer code){
        for(CacheType type : CacheType.values()){
            if(type.getCode() == code){
                return type;
            }
        }

        throw new IllegalArgumentException("未能找到匹配的CacheType:" + code);
    }
}
