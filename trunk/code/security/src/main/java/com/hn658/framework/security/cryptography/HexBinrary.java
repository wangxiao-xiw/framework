/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		HexBinrary
 *	包	名：		com.wzitech.chaos.framework.server.common.security
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-12
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-7-12 下午5:52:24
 * 				
 ************************************************************************************/
package com.hn658.framework.security.cryptography;

/**
 * HexBinrary算法类
 * @author Shawn
 * 
 */
public class HexBinrary {
	private HexBinrary() {
	}

	private static int hexToBin(char ch) {
		if ('0' <= ch && ch <= '9')
			return ch - 48;
		if ('A' <= ch && ch <= 'F')
			return (ch - 65) + 10;
		if ('a' <= ch && ch <= 'f')
			return (ch - 97) + 10;
		else
			return -1;
	}

	public static byte[] decodeHexBinrary(String s) {
		int len = s.length();
		if (len % 2 != 0)
			return null;
		byte out[] = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			int h = hexToBin(s.charAt(i));
			int l = hexToBin(s.charAt(i + 1));
			if (h == -1 || l == -1)
				return null;
			out[i / 2] = (byte) (h * 16 + l);
		}

		return out;
	}

	private static char encode(int ch) {
		ch &= 0xf;
		if (ch < 10)
			return (char) (48 + ch);
		else
			return (char) (65 + (ch - 10));
	}

	public static String encodeHexBinrary(byte data[]) {
		StringBuffer r = new StringBuffer(data.length * 2);
		for (int i = 0; i < data.length; i++) {
			r.append(encode(data[i] >> 4));
			r.append(encode(data[i] & 0xf));
		}

		return r.toString();
	}
}
