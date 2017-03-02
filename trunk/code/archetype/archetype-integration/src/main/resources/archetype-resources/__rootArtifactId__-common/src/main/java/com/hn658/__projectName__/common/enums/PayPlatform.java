package com.hn658.${projectName}.common.enums;
/**
 * 
 * @author davidcun
 *
 */
public enum PayPlatform {

    Alipay(1, "支付宝", "alipayManager"),
	
	WeixinPay(2, "微信支付", "weixPayManager");
	
	private int code;
	private String name;
	private String beanName;

	PayPlatform(int code, String name, String beanName) {
		this.code = code;
		this.name = name;
		this.beanName = beanName;
	}

	public String getBeanName() {
		return beanName;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static PayPlatform getPayPlatform(int code) {
		for (PayPlatform type : PayPlatform.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("未能找到匹配的PayPlatform:" + code);
	}
	
	
	public static PayPlatform getPayPlatform(String name) {
		for (PayPlatform type : PayPlatform.values()) {
			if (type.getName() == name) {
				return type;
			}
		}
		throw new IllegalArgumentException("未能找到匹配的PayPlatform:" + name);
	}
}
