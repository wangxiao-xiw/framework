/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		ECB
 *	包	名：		com.wzitech.chaos.framework.server.common.security
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-12
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-7-12 下午5:57:06
 * 				
 ************************************************************************************/
package com.hn658.framework.security.cryptography;

import com.hn658.framework.shared.exception.SystemException;

/**
 * ECB算法类
 * @author Shawn
 * 
 */
public class ECB {
	private static final int UNIT_LENGTH = 8;

	/**
	 * 取byte数组的一部分返回
	 * 
	 * @param b
	 *            被取的byte数组
	 * @param start
	 *            起始位置
	 * @param end
	 *            结束位置
	 * @return
	 */
	private static byte[] subByte(byte b[], int start, int end) {

		int sublength = end - start;
		byte[] result = new byte[sublength];

		for (int i = 0; i < sublength; i++) {
			result[i] = b[start + i];
		}

		return result;
	}

	/**
	 * 对byte数组做xor操作
	 * 
	 * @param b1
	 *            byte数组，长度要求为8
	 * @param b2
	 *            byte数组，长度要求为8 由于Des的输入参数必须为64位，�?��本方法规定b1和b2的长度必须为8
	 * @return 两个byte数组xor后的结果
	 * @exception IllegalArgumentException
	 *                when 参数b1和b2的长度!= 8
	 */
	private static byte[] doXor(byte[] b1, byte[] b2) {

		int byte_length = 8;

		byte[] result = new byte[byte_length];

		if (b1.length != byte_length || b2.length != byte_length) {
			throw new IllegalArgumentException(
					"Both byte array'length must = 8!");
		}

		for (int i = 0; i < b1.length; i++) {
			result[i] = doXor(b1[i], b2[i]);
		}

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
	 * 将byte数组长度扩充为8的倍数 不足的部分用二进行填充
	 * 
	 * @param bs
	 *            待扩充的数据
	 * @return
	 */
	private static byte[] appendTo8Multiple(byte mab[]) {

		int length = mab.length;
		int gap = 0;

		if (length % UNIT_LENGTH != 0) {
			gap = UNIT_LENGTH - length % UNIT_LENGTH;
		}

		int newlength = length + gap;

		byte bytes2Return[] = new byte[newlength];

		for (int i = 0; i < mab.length; i++) {
			bytes2Return[i] = mab[i];
		}

		return bytes2Return;
	}

	public static byte[] encrypt(byte[] mak, final byte[] mab) {
		// 对原始msg处理，生成完整的8倍数的byte
		try {
			final byte[] msg_whole = appendTo8Multiple(mab);
			int unit_number = msg_whole.length / UNIT_LENGTH;
			// 逐个字节做异或
			byte[] xorResult = subByte(msg_whole, 0, 8);
			for (int i = 1; i < unit_number; i++) {
				int start = i * UNIT_LENGTH;
				int end = start + UNIT_LENGTH;
				byte[] unit = subByte(msg_whole, start, end);
				xorResult = doXor(xorResult, unit);
			}
			// 把结果转换成16进制表示的字符串
			String xorResultHex = HexBinrary.encodeHexBinrary(xorResult);
			// 取高位的8个char
			String xorResultHexH = xorResultHex.substring(0, 8);
			// 取低位的8个char
			String xorResultHexL = xorResultHex.substring(8, 16);
			// 生成Des加密
			SimpleDES des = new SimpleDES(mak, false);
			// 对高位的8个char进行DES加密
			byte[] desdata1 = des.encrypt(xorResultHexH.getBytes("GBK"));
			// 对高位的8个char进行DES加密的结果与低位8个char进行异或
			byte[] desXorData1 = doXor(xorResultHexL.getBytes("GBK"), desdata1);
			// 对上一组的异或结果进行DES加密
			byte[] desdata2 = des.encrypt(desXorData1);
			// 对上�?��的结果进行16进制码转码
			String desdata2Hex = HexBinrary.encodeHexBinrary(desdata2);
			// 取前8个char作为结果返回
			desdata2Hex = desdata2Hex.substring(0, 8);
			return desdata2Hex.getBytes("GBK");
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}

	}
}
