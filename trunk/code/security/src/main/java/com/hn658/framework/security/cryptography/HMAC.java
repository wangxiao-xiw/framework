/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		HMAC
 *	包	名：		com.wzitech.chaos.framework.server.common.security
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	Dec 11, 2012
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 Dec 11, 2012 5:10:31 PM
 * 				
 ************************************************************************************/
package com.hn658.framework.security.cryptography;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Shawn
 *
 */
public class HMAC {
	public static byte[] encryptHMAC(String data, String secret) throws IOException {
		byte[] bytes = null;
		try {
			SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacMD5");
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			bytes = mac.doFinal(data.getBytes("UTF-8"));
		} catch (GeneralSecurityException gse) {
			throw new RuntimeException(gse.getMessage(), gse);
		}
		return bytes;
	}
}
