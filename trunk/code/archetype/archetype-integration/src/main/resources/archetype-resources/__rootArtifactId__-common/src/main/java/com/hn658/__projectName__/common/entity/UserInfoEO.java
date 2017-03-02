package com.hn658.${projectName}.common.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hn658.framework.shared.entity.BaseEntity;
import com.hn658.framework.shared.entity.IUser;

/**
 * 
 * @author ztjie
 *
 */
public class UserInfoEO extends BaseEntity  implements IUser{
	
	private static final long serialVersionUID = 5639136773133666985L;

	/**
	 * 登录帐号
	 */
	private String loginAccount;
	
	/**
	 * 登录密码
	 */
	private String password;
	
	
	/**
	 * 姓名
	 */
	private String realName;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 姓别
	 */
	private String sex;
	
	/**
	 * QQ
	 */
	private String qq;
	
	/**
	 * 微信
	 */
	private String weiXin;
	
	/**
	 * 手机号码
	 */
	private String phoneNumber;
	
	/**
	 * 个性签名
	 */
	private String sign;
	
	/**
	 * 头像地址
	 */
	private String avatarUrl;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;
	
	/**
	 * 是否删除
	 */
	private Boolean isDeleted;

	/**
	 * @return the loginAccount
	 */
	@Override
	public String getLoginAccount() {
		return loginAccount;
	}

	/**
	 * @param loginAccount the loginAccount to set
	 */
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the weiXin
	 */
	public String getWeiXin() {
		return weiXin;
	}

	/**
	 * @param weiXin the weiXin to set
	 */
	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return the avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * @param avatarUrl the avatarUrl to set
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		if(isDeleted==null){
			return true;
		}else{
			return isDeleted;			
		}
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	@JsonProperty("isDeleted") 
	public void setIsDeleted(Boolean isDeleted) {
		if(isDeleted==null){
			this.isDeleted = true;
		}else{
			this.isDeleted = isDeleted;			
		}
	}

}
