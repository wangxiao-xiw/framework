package com.hn658.${projectName}.common.enums;

public enum VerifyCodeType {

	//手机登录
	MOBILE_LOGIN(1);
	
	private int type;
	
	private VerifyCodeType(int type) {
		this.type = type;
	}
	public int getTypeValue(){
		return this.type;
	}
	
	public static VerifyCodeType getVerifyCodeType(int type){
		for (VerifyCodeType t : VerifyCodeType.values()) {
			if (t.getTypeValue() == type) {
				return t;
			}
		}
		throw new IllegalArgumentException("未能找到匹配的VerifyCodeType:" + type);
		
	}
}
