/************************************************************************************
 *  Copyright 2011 WZITech Corporation. All rights reserved.
 *	
 *	模        块：	CodingUtils
 *	包        名：	com.wzitech.iboxpayment.framework.common.utils
 *	项目名称：	iboxpayment-framework-common 
 *	作        者： 	Shawn
 *	创建时间：	2011-11-6
 *	描        述：	CodingUtils工具类
 *	更新纪录：
 * 
 ************************************************************************************/
package com.hn658.framework.shared.utils;

/**
 * CodingUtils工具类
 * 
 * @author Shawn
 * 
 */
public class CodingUtils {
	private CodingUtils() {

	}

	public static String byte2hex(byte[] bytes) {
		StringBuffer retString = new StringBuffer();
		for (int i = 0; i < bytes.length; ++i) {
			retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF))
					.substring(1).toUpperCase());
		}
		return retString.toString();
	}
}
