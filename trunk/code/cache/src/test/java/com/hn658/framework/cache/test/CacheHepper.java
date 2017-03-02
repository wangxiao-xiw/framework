package com.hn658.framework.cache.test;

/**
 * Created by baihai on 2015/10/28.
 */
public class CacheHepper {

    private static final String keyPrefix = "hn658:base:";

    /**
     * 获取对应后台帐号的userId
     *
     * @param userLoginAccount
     * @return
     */
    public static String userAccount(String userLoginAccount) {
        return keyPrefix+"backend:userLoginAccount:" + userLoginAccount + ":userId";
    }

}
