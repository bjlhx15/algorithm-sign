package com.lhx.symmetryencrypt.desAndaesAndrc4;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class JdkSymmetryEncrypt {
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
	public static String ALGORITHM = "DES";

	/**
	 * 转换密钥<br>
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		SecretKey secretKey = null;
		if (JdkSymmetryEncrypt.ALGORITHM.equalsIgnoreCase("des")) {
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(JdkSymmetryEncrypt.ALGORITHM);
			secretKey = keyFactory.generateSecret(dks);
		} else {
			// 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
			secretKey = new SecretKeySpec(key, JdkSymmetryEncrypt.ALGORITHM);
		}
		return secretKey;
	}

	/**
	 * 解密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		Key k = JdkSymmetryEncrypt.toKey(JdkSymmetryEncrypt.decryptBASE64(key));
		Cipher cipher = Cipher.getInstance(JdkSymmetryEncrypt.ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * 加密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		Key k = JdkSymmetryEncrypt.toKey(JdkSymmetryEncrypt.decryptBASE64(key));
		Cipher cipher = Cipher.getInstance(JdkSymmetryEncrypt.ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * 生成密钥
	 *
	 * @return
	 * @throws Exception
	 */
	public static String initKey() throws Exception {
		return JdkSymmetryEncrypt.initKey(null);
	}

	/**
	 * 生成密钥
	 *
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static String initKey(String seed) throws Exception {
		SecureRandom secureRandom = null;
		if (seed != null) {
			secureRandom = new SecureRandom(JdkSymmetryEncrypt.decryptBASE64(seed));
		} else {
			secureRandom = new SecureRandom();
		}
		KeyGenerator kg = KeyGenerator.getInstance(JdkSymmetryEncrypt.ALGORITHM);
		kg.init(secureRandom);
		SecretKey secretKey = kg.generateKey();
		return JdkSymmetryEncrypt.encryptBASE64(secretKey.getEncoded());
	}

	public static byte[] decryptBASE64(String data) {
		return Base64.getDecoder().decode(data);
	}

	public static String encryptBASE64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}
}
