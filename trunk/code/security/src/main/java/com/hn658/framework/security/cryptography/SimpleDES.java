/************************************************************************************
 *  Copyright 2012 WZITech Corporation. All rights reserved.
 *	
 *	模	块：		SimpleDES
 *	包	名：		com.wzitech.chaos.framework.server.common.security
 *	项目名称：	chaos-common
 *	作	者：		Shawn
 *	创建时间：	2012-7-12
 *	描	述：		
 *	更新纪录：	1. Shawn 创建于 2012-7-12 下午5:50:28
 * 				
 ************************************************************************************/
package com.hn658.framework.security.cryptography;

import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES算法类
 * 
 * @author Shawn
 * 
 */
public class SimpleDES {
	/**
	 * DES加密处理对象
	 */
	private Cipher theDESCipher;

	/**
	 * DES密钥对象
	 */
	private SecretKey key;

	/**
	 * Padding标志
	 */
	private boolean paddingFlag = false;

	/**
	 * theDESCipher对象初始化标志
	 */
	private boolean initCipherFlag = false;

	/**
	 * theDESCipher对象初始化的Mode
	 */
	private int initCipherMode;

	public SimpleDES() {
		try {
			if (paddingFlag == true) {
				theDESCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			} else {
				theDESCipher = Cipher.getInstance("DES/ECB/NoPadding");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void instanceSimpleDESCrypto(byte[] keyBuf, boolean aPaddingFlag) {
		try {
			paddingFlag = aPaddingFlag;
			if (paddingFlag == true) {
				theDESCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			} else {
				theDESCipher = Cipher.getInstance("DES/ECB/NoPadding");
			}

			key = new SecretKeySpec(keyBuf, "DES");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public SimpleDES(byte[] keyBuf, boolean aPaddingFlag) {
		instanceSimpleDESCrypto(keyBuf, aPaddingFlag);
	}

	public SimpleDES(byte[] keyBuf) {
		instanceSimpleDESCrypto(keyBuf, false);
	}

	public void setPaddingFlag(boolean aPaddingFlag) {
		paddingFlag = aPaddingFlag;
		initCipherFlag = false;
	}

	public boolean getPaddingFlag() {
		return paddingFlag;
	}

	public void setKey(byte[] keyBuf) {
		key = new SecretKeySpec(keyBuf, "DES");
		initCipherFlag = false;
	}

	public byte[] getKey() {
		return key.getEncoded();
	}

	private void initCipher(int mode) throws InvalidKeyException {
		if (initCipherFlag == false || mode != initCipherMode) {
			theDESCipher.init(mode, key);
			initCipherFlag = true;
			initCipherMode = mode;
		}
	}

	public byte[] encrypt(byte[] input, int inputOffset, int inputLen) {
		try {
			initCipher(Cipher.ENCRYPT_MODE);
			return theDESCipher.doFinal(input, inputOffset, inputLen);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public byte[] encrypt(byte[] input) {
		return encrypt(input, 0, input.length);
	}

	public int encrypt(byte[] input, int inputOffset, int inputLen,
			byte[] output, int outputOffset) {
		try {
			initCipher(Cipher.ENCRYPT_MODE);
			return theDESCipher.doFinal(input, inputOffset, inputLen, output,
					outputOffset);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public int encrypt(byte[] input, int inputOffset, int inputLen,
			byte[] output) {
		return encrypt(input, inputOffset, inputLen, output, 0);
	}

	public byte[] decrypt(byte[] input, int inputOffset, int inputLen) {
		try {
			initCipher(Cipher.DECRYPT_MODE);
			return theDESCipher.doFinal(input, inputOffset, inputLen);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public byte[] decrypt(byte[] input) {
		return decrypt(input, 0, input.length);
	}

	public int decrypt(byte[] input, int inputOffset, int inputLen,
			byte[] output, int outputOffset) {
		try {
			initCipher(Cipher.DECRYPT_MODE);
			return theDESCipher.doFinal(input, inputOffset, inputLen, output,
					outputOffset);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public int decrypt(byte[] input, int inputOffset, int inputLen,
			byte[] output) {
		return decrypt(input, inputOffset, inputLen, output, 0);
	}

	public void setEncryptMode() {
		try {
			initCipher(Cipher.ENCRYPT_MODE);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void setDecryptMode() {
		try {
			initCipher(Cipher.DECRYPT_MODE);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public byte[] update(byte[] input, int inputOffset, int inputLen) {
		return theDESCipher.update(input, inputOffset, inputLen);
	}

	public byte[] update(byte[] input, int inputLen) {
		return update(input, 0, inputLen);
	}

	public byte[] update(byte[] input) {
		return update(input, 0, input.length);
	}

	public int update(byte[] input, int inputOffset, int inputLen,
			byte[] output, int outputOffset) {
		try {
			return theDESCipher.update(input, inputOffset, inputLen, output,
					outputOffset);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public int update(byte[] input, int inputOffset, int inputLen, byte[] output) {
		return update(input, inputOffset, inputLen, output, 0);
	}

	public byte[] doFinal(byte[] input, int inputOffset, int inputLen) {
		try {
			return theDESCipher.doFinal(input, inputOffset, inputLen);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public byte[] doFinal(byte[] input) {
		return doFinal(input, 0, input.length);
	}

	public int doFinal(byte[] input, int inputOffset, int inputLen,
			byte[] output, int outputOffset) {
		try {
			return theDESCipher.doFinal(input, inputOffset, inputLen, output,
					outputOffset);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public int doFinal(byte[] input, int inputOffset, int inputLen,
			byte[] output) {
		return doFinal(input, inputOffset, inputLen, output, 0);
	}
}
