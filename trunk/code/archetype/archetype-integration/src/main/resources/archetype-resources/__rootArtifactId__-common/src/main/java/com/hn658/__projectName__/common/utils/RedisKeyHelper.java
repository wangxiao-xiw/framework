package com.hn658.${projectName}.common.utils;

import com.hn658.${projectName}.common.enums.VerifyCodeType;

/**
 * Created by baihai on 2015/8/24.
 */
public class RedisKeyHelper {

	private static final String keyPrefix = "${projectName}:";
	
    private static final String backendKeyPrefix = "${projectName}:backend:";
    
    private static final String frontendKeyPrefix = "${projectName}:frontend:";
    
    /**
	 * 获取对应userId的后台用户信息key
	 * 
	 * @param userId
	 * @return
	 */
	public static String backendUserId(String userId) {
		return backendKeyPrefix+"uid:" + userId + ":userInfo";
	}
	
	/**
	 * 获取对应后台帐号的userId
	 * 
	 * @param userLoginAccount
	 * @return
	 */
	public static String backendUserAccount(String userLoginAccount) {
		return backendKeyPrefix+"userLoginAccount:" + userLoginAccount + ":userId";
	}
	
	/**
	 * 获取userId对应的auth
	 * 
	 * @param userId
	 * @return
	 */
	public static String backendUserAuth(String userId) {
		return backendKeyPrefix+"userId:" + userId + ":auth";
	}
	
	/**
	 * 获取userAuthKey对应的userId
	 * 
	 * @param userAuthKey
	 * @return
	 */
	public static String backendUserAuthKey(String userAuthKey) {
		return backendKeyPrefix+"auth:" + userAuthKey + ":userId";
	}
	
	/**
	 * 获取accountId对应的auth
	 * 
	 * @param accountId
	 * @return
	 */
	public static String backendAccountAuth(String accountId) {
		return backendKeyPrefix+"accountId:" + accountId + ":auth";
	}

    /**
     * 数据字典生成key值， 由传入的type动态生成
     * @param type
     * @return
     */
    public static String backendDictionaryKey(String type){
        return backendKeyPrefix+"dictionary:"+type+"type";
    }
    
    /**
	 * 获取对应uid的用户信息key
	 * @param uid
	 * @return
	 */
	public static String backendUid(String uid) {
		return backendKeyPrefix+"uid:" + uid + ":userInfo";
	}
	
	/**
	 * 获取对应帐号的uid
	 * @param loginAccount
	 * @return
	 */
	public static String backendAccount(String loginAccount){
		return backendKeyPrefix+"loginAccount:" + loginAccount + ":uid";
	}
	
	/**
	 * 获取uid对应的auth
	 * @param uid
	 * @return
	 */
	public static String backendAuth(String uid){
		return backendKeyPrefix+"uid:" + uid + ":auth";
	}
	
	/**
	 * 获取authkey对应的用户id
	 * @param authkey
	 * @return
	 */
	public static String backendAuthkey(String authkey){
		return backendKeyPrefix+"auth:" + authkey + ":uid";
	}
	
	/**
	 * 根据tokenId获取账户信息
	 * @return
	 */
	public static String backendTokenAccount(String tokenId){
		return backendKeyPrefix+"tokenId:" + tokenId + ":loginAccount";
	}
	/**
	 * 
	 * <p>查询所有权限树信息</p> 
	 * @author ztjie
	 * @date 2015-11-5 下午3:46:38
	 * @param id
	 * @return
	 * @see
	 */
	public static String allTreeNode() {
		return keyPrefix+"function:allTreeNode";
	}
	/**
	 * 
	 * <p>权限信息ID缓存KEY</p> 
	 * @author ztjie
	 * @date 2015-11-5 下午3:46:38
	 * @param id
	 * @return
	 * @see
	 */
	public static String functionId(String id) {
		return backendKeyPrefix+"function:id"+id+":info";
	}

	/**
	 * 
	 * <p>权限信息uri缓存</p> 
	 * @author ztjie
	 * @date 2015-11-5 下午3:47:44
	 * @param uri
	 * @return
	 * @see
	 */
	public static String functionUri(String uri) {
		return backendKeyPrefix+"function:functionUri:"+uri;
	}

	public static String backendUserAccessTreeNodes(Long uid) {
		return backendKeyPrefix+"auth:treeNode:"+uid;
	}

	public static String backendUserAccessUris(Long uid) {
		return backendKeyPrefix+"auth:accessUris:"+uid;
	}

	public static String backendUserAccessIds(Long uid) {
		return backendKeyPrefix+"auth:accessIds:"+uid;
	}

	public static String mobileVerifyCode(String phone, VerifyCodeType type) {
		return keyPrefix + "mobileVerifyCode:" + phone +":" + type.getTypeValue();
	}

	public static String frontendAccountUserInfo(Long userId) {
		return frontendKeyPrefix + "uid:" + userId + ":userAccount";
	}

	public static String frontendMobilePhoneUid(String mobilePhone) {
		return frontendKeyPrefix + "mobilePhone:" + mobilePhone + ":userAccountId";
	}

	public static String frontendAuthkeyUid(String authkey) {
		return frontendKeyPrefix + "authkey:" + authkey + ":userAccountId";
	}

	public static String frontendUidAuthkey(Long uid) {
		return frontendKeyPrefix + "uid:" + uid + ":authkey";
	}

	public static String frontendIp(String ip) {
		return frontendKeyPrefix + "ip:" + ip + ":address";
	}
	
    /**
     * 数据字典生成key值， 由传入的type动态生成
     * @param type
     * @return
     */
    public static String dictionaryKey(String type){
        return keyPrefix+"dictionary:"+type+"type";
    }
    


}
