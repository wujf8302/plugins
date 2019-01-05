package com.plugin.api.cipher;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES安全编码组件
 *
 * <pre>
 * 支持 DES、DESede(TripleDES,就是3DES)、AES、Blowfish、RC2、RC4(ARCFOUR)
 * DES                  key size must be equal to 56
 * DESede(TripleDES)    key size must be equal to 112 or 168
 * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
 * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
 * RC2                  key size must be between 40 and 1024 bits
 * RC4(ARCFOUR)         key size must be between 40 and 1024 bits
 * 具体内容 需要关注 JDK Document http://.../docs/technotes/guides/security/SunProviders.html
 * </pre>
 * @author wujf
 */
public class DESCoder {

	public static void main(String[] args) {
		try {
			String source = "wujianfei吴剑飞123456";
			String minwen1 = encrypt(source);
			String minwen2 = decrypt(minwen1);
			
			System.out.println(minwen1);
			System.out.println(minwen2);
			System.out.println(source.equals(minwen2));
			
			/**
			 * tPOb2f8QvVK9oKA3dcofKxfbfQHm1oge90z73RyuF2Y=
				wujianfei吴剑飞123456
				true
			 */
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ALGORITHM 算法 <br>
	 * 可替换为以下任意一种算法，同时key值的size相应改变。
	 *
	 * <pre>
	 * DES                  key size must be equal to 56
	 * DESede(TripleDES)    key size must be equal to 112 or 168
	 * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
	 * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
	 * RC2                  key size must be between 40 and 1024 bits
	 * RC4(ARCFOUR)         key size must be between 40 and 1024 bits
	 * </pre>
	 *
	 * 在Key toKey(byte[] key)方法中使用下述代码
	 * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> 替换
	 * <code>
	 * DESKeySpec dks = new DESKeySpec(key);
	 * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
	 * SecretKey secretKey = keyFactory.generateSecret(dks);
	 * </code>
	 */
	public static final String ALGORITHM = "DES";

	/**
	 * 默认密钥
	 */
	private static final String DEFAULT_KEY = "FFCS_OSS_20101114";

	/**
	 * DEFAULT_KEY经过转化后的密钥，已经用Coder.encryptBASE64加密
	 * 提供这个密钥主要是为解决不同平台（主要是虚拟机版本不同）下，同一明文（DEFAULT_KEY）产生的SecretKey不一致
	 */
	private static final String DEFAULT_SECRET_KEY = "GmfpAfvjmF0=";

	/**
	 * 转换密钥.
	 */
	private static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);

		// 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
		// SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

		return secretKey;
	}

	/**
	 * DES解密
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		Key k = toKey(BASE64Coder.decryptBASE64(key));

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);

		return cipher.doFinal(data);
	}

	/**
	 * DES解密
	 */
	public static String decrypt(String encryptData, String key) throws Exception {
		if (encryptData == null || encryptData.trim().length() == 0) {
			throw new IllegalArgumentException("密码参数不能为空");
		}
		String desStr = "";

		desStr = new String(decrypt(BASE64Coder.decryptBASE64(encryptData), key));

		return desStr;
	}

	/**
	 * DES解密
	 */
	public static String decrypt(String encryptData) throws Exception {
		if (encryptData == null || encryptData.trim().length() == 0) {
			throw new IllegalArgumentException("密码参数不能为空");
		}
		String desStr = "";

		// Key key = toKey(decryptBASE64(initKey()));
		Key key = toKey(BASE64Coder.decryptBASE64(DEFAULT_SECRET_KEY));

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);

		byte decryptByet[] = cipher.doFinal(BASE64Coder.decryptBASE64(encryptData));

		desStr = new String(decryptByet);

		return desStr;
	}

	/**
	 * DES加密
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		Key k = toKey(BASE64Coder.decryptBASE64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);

		return cipher.doFinal(data);
	}

	/**
	 * DES加密
	 */
	public static String encrypt(String data, String key) throws Exception {
		if (data == null || data.trim().length() == 0) {
			throw new IllegalArgumentException("密码参数不能为空");
		}

		String desStr = "";

		desStr = BASE64Coder.encryptBASE64(encrypt(data.getBytes(), key));

		return desStr;
	}

	/**
	 * DES加密
	 */
	public static String encrypt(String data) throws Exception {
		if (data == null || data.trim().length() == 0) {
			throw new IllegalArgumentException("密码参数不能为空");
		}

		String desStr = "";

		// Key key = toKey(decryptBASE64(initKey()));
		Key key = toKey(BASE64Coder.decryptBASE64(DEFAULT_SECRET_KEY));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		desStr = BASE64Coder.encryptBASE64(cipher.doFinal(data.getBytes()));
		return desStr;
	}

	/**
	 * DES生成默认密钥
	 */
	public static String initKey() throws Exception {
		return initKey(DEFAULT_KEY);
	}

	/**
	 * DES生成密钥 注意：此方法在不同的虚拟机环境中，同一个seed生成的SecretKey是不一样的
	 */
	public static String initKey(String seed) throws Exception {
		SecureRandom secureRandom = null;

		if (seed != null) {
			secureRandom = new SecureRandom(BASE64Coder.decryptBASE64(seed));
		} else {
			secureRandom = new SecureRandom();
		}

		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
		kg.init(secureRandom);

		SecretKey secretKey = kg.generateKey();

		return BASE64Coder.encryptBASE64(secretKey.getEncoded());
	}

}
