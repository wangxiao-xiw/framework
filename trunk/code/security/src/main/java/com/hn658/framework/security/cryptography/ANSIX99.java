/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		ANSIX99
 *	包	名：		com.wzitech.chaos.framework.server.common.security
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-12
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-7-12 下午5:49:16
 * 				
 ************************************************************************************/
package com.hn658.framework.security.cryptography;

import com.hn658.framework.shared.exception.SystemException;

/**
 * ANSI.X99算法
 * @author Shawn
 *
 */
public class ANSIX99 {
	private static final int UNIT_LENGTH = 8;

	/**
	 * 整个算法的初始化，默认为0
	 */
	private static final byte[] INITIAL_VALUE = new byte[UNIT_LENGTH];

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
	 *            byte数组，长度要求为8 由于Des的输入参数必须为64位，本方法规定b1和b2的长度必须为8
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
	 * 将byte数组长度扩充为8的倍数，不足的部分用二进0填充
	 * 
	 * @param bs
	 *            待扩充的数组
	 * @return
	 */
	private static byte[] appendTo8Multiple(byte bs[]) {

		int length = bs.length;
		int gap = 0;

		if (length % UNIT_LENGTH != 0) {
			gap = UNIT_LENGTH - length % UNIT_LENGTH;
		}

		int newlength = length + gap;

		byte rs[] = new byte[newlength];

		for (int i = 0; i < bs.length; i++) {
			rs[i] = bs[i];
		}

		return rs;
	}

	public static byte[] encode(byte[] key, String mab) {
		try {
			return encode(key, mab.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	public static byte[] encode1(byte[] key, final byte[] mab) {
		// 生成Des加密
		SimpleDES des = new SimpleDES(key, false);

		try {
			// 对原始msg处理，生成完整的8倍数的byte
			final byte[] msg_whole = appendTo8Multiple(mab);
			int unit_number = msg_whole.length / UNIT_LENGTH;

			byte[] resultPre = subByte(msg_whole, 0, 8);
			// 对每组64位做循环处理，最终获得Mac
			for (int i = 1; i < unit_number; i++) {

				int start = i * UNIT_LENGTH;
				int end = start + UNIT_LENGTH;
				byte[] curr_unit = subByte(msg_whole, start, end);
				resultPre = doXor(resultPre, curr_unit);
			}

			String r = BCDASCII.fromBCDToASCIIString(resultPre, 0,
					resultPre.length * 2, false);

			String r1 = r.substring(0, 8);
			byte[] e = des.encrypt(r1.getBytes());

			final byte[] result = doXor(e, r.substring(8).getBytes());
			byte[] e2 = des.encrypt(result);

			String re = BCDASCII.fromBCDToASCIIString(e2, 0, e2.length, false);
			return re.substring(0, 8).getBytes();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public static byte[] encode(byte[] key, final byte[] mab) {
		// 生成Des加密
		SimpleDES des = new SimpleDES(key, false);
		byte[] result = INITIAL_VALUE;

		try {
			// 对原始msg处理，生成完整的8倍数的byte
			final byte[] msg_whole = appendTo8Multiple(mab);
			int unit_number = msg_whole.length / UNIT_LENGTH;
			
			// 对每组64位做循环处理，最终获得Mac
			for (int i = 0; i < unit_number; i++) {
				int start = i * UNIT_LENGTH;
				int end = start + UNIT_LENGTH;
				byte[] curr_unit = subByte(msg_whole, start, end);
				result = des.encrypt(doXor(result, curr_unit));
			}

			// 取前64位
			result = subByte(result, 0, 8);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}

		return result;
	}
}
