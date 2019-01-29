package com.example.demo.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * 
 * @author zhancy
 * @see https://blog.csdn.net/cclxh000/article/details/74188263
 *
 */
public class AESCrypt {

	// AES密码器
	private static Cipher cipher;

	// 字符串编码
	private static final String KEY_CHARSET = "UTF-8";

	// 算法方式
	private static final String KEY_ALGORITHM = "AES";

	// 算法/模式/填充
	private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

	// 私钥大小128/192/256(bits)位 即：16/24/32bytes，暂时使用128，如果扩大需要更换java/jre里面的jar包
	private static final Integer PRIVATE_KEY_SIZE_BIT = 128;
	private static final Integer PRIVATE_KEY_SIZE_BYTE = 16;

	private static void initParam(String secretKey, int mode) {
		try {
			// 防止Linux下生成随机key
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(secretKey.getBytes());
			// 获取key生成器
			KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
			keygen.init(PRIVATE_KEY_SIZE_BIT, secureRandom);

			// 获得原始对称密钥的字节数组
			byte[] raw = secretKey.getBytes();

			// 根据字节数组生成AES内部密钥
			SecretKeySpec key = new SecretKeySpec(raw, KEY_ALGORITHM);
			// 根据指定算法"AES/CBC/PKCS5Padding"实例化密码器
			cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			IvParameterSpec iv = new IvParameterSpec(secretKey.getBytes());
			// System.out.println("iv:" + new String(iv.getIV()));
			cipher.init(mode, key, iv);
		} catch (Exception e) {
			throw new RuntimeException("AESCrypt:initParam fail!", e);
		}
	}

	public static String encrypt(String secretKey, String plainText) {
		if (secretKey.length() != PRIVATE_KEY_SIZE_BYTE) {
			throw new RuntimeException("AESCrypt:Invalid AES secretKey length (must be 16 bytes)");
		}

		// 密文字符串
		String cipherText = "";
		try {
			// 加密模式初始化参数
			initParam(secretKey, Cipher.ENCRYPT_MODE);
			// 获取加密内容的字节数组
			byte[] bytePlainText = plainText.getBytes(KEY_CHARSET);
			// 执行加密
			byte[] byteCipherText = cipher.doFinal(bytePlainText);
			cipherText = Base64.encodeBase64String(byteCipherText);
		} catch (Exception e) {
			throw new RuntimeException("AESCrypt:encrypt fail!", e);
		}
		return cipherText;
	}

	public static String decrypt(String secretKey, String cipherText) {
		if (secretKey.length() != PRIVATE_KEY_SIZE_BYTE) {
			throw new RuntimeException("AESCrypt:Invalid AES secretKey length (must be 16 bytes)");
		}

		// 明文字符串
		String plainText = "";
		try {
			initParam(secretKey, Cipher.DECRYPT_MODE);
			// 将加密并编码后的内容解码成字节数组
			byte[] byteCipherText = Base64.decodeBase64(cipherText);
			// 解密
			byte[] bytePlainText = cipher.doFinal(byteCipherText);
			plainText = new String(bytePlainText, KEY_CHARSET);
		} catch (Exception e) {
			throw new RuntimeException("AESCrypt:decrypt fail!", e);
		}
		return plainText;
	}

}
