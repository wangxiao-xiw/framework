package com.hn658.${projectName}.common.utils;

import org.apache.commons.lang3.StringUtils;

import com.hn658.framework.security.cryptography.Base64;
import com.hn658.framework.security.cryptography.Digests;

/**
 * 密码工具类
 * @author Chengfei.Sun
 */
public class PassWordHelper {
    /**
     * 加密密码
     * @param clearTextPwd 密码明文
     * @param userLoginName 用户名
     * @return
     */
    public static String encyptPassword(String clearTextPwd, String userLoginName) {
        return Base64.encodeBase64Binrary(Digests.sha1(clearTextPwd.getBytes(),
                userLoginName.getBytes()));
    }


    /**
     * 验证密码
     * @param clearTextPwd 请求密码明文
     * @param userLoginName 请求用户名
     * @param dbPassword 数据库保存加密密码
     * @return
     */
    public static boolean verifyPassword(String clearTextPwd, String userLoginName, String dbPassword){
        //根据请求信息，生成密码
        String genePassword = encyptPassword(clearTextPwd, userLoginName);

        //与数据库保存密码比较
        if(StringUtils.equals(genePassword, dbPassword)){
            return true;
        }
        return false;
    }
}
