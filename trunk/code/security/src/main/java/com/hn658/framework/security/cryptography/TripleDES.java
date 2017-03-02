/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		TripleDES
 *	包	名：		com.wzitech.chaos.framework.server.common.security
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-12
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-7-12 下午5:51:31
 * 				
 ************************************************************************************/
package com.hn658.framework.security.cryptography;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.hn658.framework.shared.exception.BusinessException;
import com.hn658.framework.shared.exception.SystemException;

/**
 * 三重DES算法类
 * @author Shawn
 *
 */
public class TripleDES {
	private static final int UNIT_LENGTH = 8;

	/**
	 * 取byte数组的一部分返回
	 * 
	 * @param b被取的byte数组
	 * @param start 起始位置
	 * @param end 结束位置
	 * @return
	 */
	public static byte[] subByte(byte b[], int start, int end) {
		int sublength = end - start;
		byte[] result = new byte[sublength];
		System.arraycopy(b, start, result, 0, sublength);

		return result;
	}

	/**
	 * 对byte做xor操作
	 * 
	 * @param b1
	 * @param b2
	 * @return 返回两个byte xor后的结果
	 */
	private static byte doXor(byte b1, byte b2) {
		return (byte) (b1 ^ b2);
	}

	/**
	 * 对byte数组做xor操作
	 * 
	 * @param b1 byte数组，长度要求为8
	 * @param b2 byte数组，长度要求为8
	 * @return 两个byte数组xor后的结果
	 * @exception IllegalArgumentException when
	 *            参数b1和b2的长度!= 8
	 */
	public static byte[] doXor(byte[] b1, byte[] b2) {

		int byte_length = 8;

		byte[] result = new byte[byte_length];

		if (b1.length != byte_length || b2.length != byte_length) {
			throw new IllegalArgumentException("Both byte array'length must = 8!");
		}

		for (int i = 0; i < b1.length; i++) {
			result[i] = doXor(b1[i], b2[i]);
		}

		return result;

	}

	/**
	 * 获取JAVA 3DES Key
	 * 
	 * @param origKey
	 * @return
	 */
	private static String get3DesKey(String origKey) throws BusinessException {
		if (origKey.length() != 32) {
			String errmsg = "Length of Akey Or masterkey in mas is ilegeal, expected length is 32 chars.";
			throw new BusinessException(errmsg);
		}

		StringBuffer des3Key = new StringBuffer(origKey);
		des3Key.append(origKey.substring(0, 16));

		return des3Key.toString();

	}

	/**
	 * 3DES软加密，用于进行签到工作密钥的Check Value
	 * 
	 * @param msg
	 * @param key
	 * @return
	 * @throws AppBizException
	 */
	public static String des3Encrypt(String msg, String key) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
			String keyOnjava = get3DesKey(key);
			byte[] keyInBCD = BCDASCII.fromASCIIToBCD(keyOnjava, 0, keyOnjava.length(), false);
			byte[] msgInBCD = BCDASCII.fromASCIIToBCD(msg, 0, msg.length(), false);
			SecretKey secretKey = new SecretKeySpec(keyInBCD, "DESede");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] resultInBCD = cipher.doFinal(msgInBCD);
			String retnStr = BCDASCII.fromBCDToASCIIString(resultInBCD, 0, resultInBCD.length * 2, false);

			return retnStr;
		} catch (Exception e) {
			String errmsg = "Error when drcrypt key, ermsg: " + e.getMessage();
			throw new SystemException(errmsg, e);
		}
	}

	/**
	 * 3DES软解密，用于进行签到工作密钥的解密
	 * 
	 * @param msg
	 * @param key
	 * @return
	 * @throws AppBizException
	 */
	public static String des3Decrypt(String msg, String key) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
			String keyOnjava = get3DesKey(key);
			byte[] keyInBCD = BCDASCII.fromASCIIToBCD(keyOnjava, 0, keyOnjava.length(), false);
			byte[] msgInBCD = BCDASCII.fromASCIIToBCD(msg, 0, msg.length(), false);
			SecretKey secretKey = new SecretKeySpec(keyInBCD, "DESede");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] resultInBCD = cipher.doFinal(msgInBCD);
			String retnStr = BCDASCII.fromBCDToASCIIString(resultInBCD, 0, resultInBCD.length * 2, false);

			return retnStr;
		} catch (Exception e) {
			String errmsg = "Error when drcrypt key, ermsg: " + e.getMessage();
			throw new SystemException(errmsg, e);
		}
	}

	public static byte[] AbcEncryptMacblock(byte[] mak, final byte[] mab) {
		try {
			final byte[] msg_whole = mab;
			int unit_number = msg_whole.length / UNIT_LENGTH;
			//逐个字节做异或
			byte[] xorResult = subByte(msg_whole, 0, 8);
			for (int i = 1; i < unit_number; i++) {
				int start = i * UNIT_LENGTH;
				int end = start + UNIT_LENGTH;
				byte[] unit = subByte(msg_whole, start, end);
				xorResult = doXor(xorResult, unit);
			}
			String makStr = BCDASCII.fromBCDToASCIIString(mak, 0, mak.length * 2, false);
			String macbStr = BCDASCII.fromBCDToASCIIString(xorResult, 0, xorResult.length * 2, false);
			String output = des3Encrypt(macbStr, makStr);

			return output.getBytes();
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}

	}
}
