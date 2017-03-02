/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	MD5
 *	包        名：	com.wzitech.iboxpayment.framework.security.cryptography
 *	项目名称：	iboxpayment-framework-security 
 *	作        者： 	Shawn
 *	创建时间：	2011-11-6
 *	描        述：
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.security.cryptography;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hn658.framework.shared.exception.SystemException;
import com.hn658.framework.shared.utils.CodingUtils;

/**
 * @author Shawn
 *
 */
public class MD5 {
	static Log log = LogFactory.getLog(MD5.class);

	public final static String SIGN_TYPE = "MD5";

	public static String md5(String str) {
		try {
			MessageDigest msgDigest = MessageDigest.getInstance(SIGN_TYPE);
			msgDigest.update(str.getBytes());
			byte[] enbyte = msgDigest.digest();
			return CodingUtils.byte2hex(enbyte);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new SystemException(e.getMessage());
		}
	}
}
