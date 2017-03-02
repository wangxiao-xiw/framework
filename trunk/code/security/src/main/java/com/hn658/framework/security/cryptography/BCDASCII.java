/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		BCDASCII
 *	包	名：		com.wzitech.chaos.framework.server.common.security
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-12
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-7-12 下午5:48:50
 * 				
 ************************************************************************************/
package com.hn658.framework.security.cryptography;

/**
 * @author Shawn
 * 
 */
public class BCDASCII {
	/**
	 * 字母'A'的ASCII编码
	 */
	public final static byte ALPHA_A_ASCII_VALUE = 0x41;

	/**
	 * 字母'a'的ASCII编码
	 */
	public final static byte ALPHA_a_ASCII_VALUE = 0x61;

	/**
	 * 数字'0'的ASCII编码
	 */
	public final static byte DIGITAL_0_ASCII_VALUE = 0x30;

	private BCDASCII() {
	}

	/**
	 * 从BCD编码转换成ASCII编码.
	 * 
	 * @param bcdBuf
	 *            , BCD编码缓冲区
	 * @param bcdOffset
	 *            , BCD编码缓冲区起始偏移
	 * @param asciiBuf
	 *            , ASCII编码缓冲区
	 * @param asciiOffset
	 *            , ASCII编码缓冲区的起始偏移
	 * @param asciiLen
	 *            , 采用ASCII编码时的信息长度
	 * @param rightAlign
	 *            , 奇数个ASCII码时采用的右对齐方式标志
	 * @return, ASCII编码缓冲区
	 */
	public static void fromBCDToASCII(byte[] bcdBuf, int bcdOffset,
			byte[] asciiBuf, int asciiOffset, int asciiLen,
			boolean rightAlignFlag) {
		int cnt;

		if (((asciiLen & 1) == 1) && rightAlignFlag) {
			cnt = 1;
			asciiLen++;
		} else
			cnt = 0;

		for (; cnt < asciiLen; cnt++, asciiOffset++) {
			asciiBuf[asciiOffset] = (byte) ((((cnt) & 1) == 1) ? (bcdBuf[bcdOffset++] & 0x0f)
					: ((bcdBuf[bcdOffset] >> 4) & 0x0f));
			asciiBuf[asciiOffset] = (byte) (asciiBuf[asciiOffset] + ((asciiBuf[asciiOffset] > 9) ? (ALPHA_A_ASCII_VALUE - 10)
					: DIGITAL_0_ASCII_VALUE));
		}
	}

	/**
	 * 从BCD编码转换成ASCII编码.
	 * 
	 * @param bcdBuf
	 *            , BCD编码缓冲区
	 * @param asciiLen
	 *            , 统一采用ASCII编码时的信息长度
	 * @param rightAlignFlag
	 *            , 奇数个ASCII码时采用的右对齐方式标志
	 * @return, ASCII编码缓冲区
	 */
	public static byte[] fromBCDToASCII(byte[] bcdBuf, int bcdOffset,
			int asciiLen, boolean rightAlignFlag) {
		byte[] asciiBuf = new byte[asciiLen];
		fromBCDToASCII(bcdBuf, bcdOffset, asciiBuf, 0, asciiLen, rightAlignFlag);

		return asciiBuf;
	}

	public static String fromBCDToASCIIString(byte[] bcdBuf, int bcdOffset,
			int asciiLen, boolean rightAlignFlag) {
		try {
			return new String(fromBCDToASCII(bcdBuf, bcdOffset, asciiLen,
					rightAlignFlag), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 从ASCII编码转换成BCD编码.
	 * 
	 * @param asciiBuf
	 *            , ASCII编码缓冲区
	 * @param asciiOffset
	 *            , ASCII编码缓冲区的起始偏移
	 * @param asciiLen
	 *            , 采用ASCII编码时的信息长度
	 * @param bcdBuf
	 *            , BCD编码缓冲区
	 * @param bcdOffset
	 *            , BCD编码缓冲区起始偏移
	 * @param rightAlignFlag
	 *            , 奇数个ASCII码时采用的右对齐方式标志
	 */
	public static void fromASCIIToBCD(byte[] asciiBuf, int asciiOffset,
			int asciiLen, byte[] bcdBuf, int bcdOffset, boolean rightAlignFlag) {
		int cnt;
		byte ch, ch1;

		if (((asciiLen & 1) == 1) && rightAlignFlag) {
			ch1 = 0;
		} else {
			ch1 = 0x55;
		}

		for (cnt = 0; cnt < asciiLen; cnt++, asciiOffset++) {
			if (asciiBuf[asciiOffset] >= ALPHA_a_ASCII_VALUE)
				ch = (byte) (asciiBuf[asciiOffset] - ALPHA_a_ASCII_VALUE + 10);
			else if (asciiBuf[asciiOffset] >= ALPHA_A_ASCII_VALUE)
				ch = (byte) (asciiBuf[asciiOffset] - ALPHA_A_ASCII_VALUE + 10);
			else if (asciiBuf[asciiOffset] >= DIGITAL_0_ASCII_VALUE)
				ch = (byte) (asciiBuf[asciiOffset] - DIGITAL_0_ASCII_VALUE);
			else
				ch = 0x00;

			if (ch1 == 0x55)
				ch1 = ch;
			else {
				bcdBuf[bcdOffset] = (byte) (ch1 << 4 | ch);
				bcdOffset++;
				ch1 = 0x55;
			}
		}

		if (ch1 != 0x55)
			bcdBuf[bcdOffset] = (byte) (ch1 << 4);
	}

	public static void fromASCIIToBCD(String asciiStr, int asciiOffset,
			int asciiLen, byte[] bcdBuf, int bcdOffset, boolean rightAlignFlag) {
		try {
			byte[] asciiBuf = asciiStr.getBytes("UTF-8");
			fromASCIIToBCD(asciiBuf, asciiOffset, asciiLen, bcdBuf, bcdOffset,
					rightAlignFlag);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 从ASCII编码转换成BCD编码.
	 * 
	 * @param asciiBuf
	 *            , ASCII编码缓冲区
	 * @param asciiOffset
	 *            , ASCII编码缓冲区的起始偏移
	 * @param asciiLen
	 *            , 统一采用ASCII编码时的信息长度
	 * @param rightAlignFlag
	 *            , 奇数个ASCII码时采用的右对齐方式标志
	 * @return, BCD编码缓冲区
	 */
	public static byte[] fromASCIIToBCD(byte[] asciiBuf, int asciiOffset,
			int asciiLen, boolean rightAlignFlag) {
		byte[] bcdBuf = new byte[(asciiLen + 1) / 2];
		fromASCIIToBCD(asciiBuf, asciiOffset, asciiLen, bcdBuf, 0,
				rightAlignFlag);

		return bcdBuf;
	}

	public static byte[] fromASCIIToBCD(String asciiStr, int asciiOffset,
			int asciiLen, boolean rightAlignFlag) {
		try {
			byte[] asciiBuf = asciiStr.getBytes("UTF-8");
			return fromASCIIToBCD(asciiBuf, asciiOffset, asciiLen,
					rightAlignFlag);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}

