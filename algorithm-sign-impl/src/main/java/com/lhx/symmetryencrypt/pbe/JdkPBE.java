package com.lhx.symmetryencrypt.pbe;

import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * PBE 算法
 *
 * @author 木子旭
 * @since 2017年3月14日上午11:56:24
 * @version %I%,%G%
 */
public class JdkPBE {
	/**
	 * 支持以下任意一种算法
	 *
	 * <pre>
	 * PBEWithMD5AndDES
	 * PBEWithMD5AndTripleDES
	 * PBEWithSHA1AndDESede
	 * PBEWithSHA1AndRC2_40
	 * </pre>
	 */
	public static final String ALGORITHM = "PBEWITHMD5andDES";

	/**
	 * 盐初始化
	 *
	 * @return
	 * @throws Exception
	 */
	public static byte[] initSalt() throws Exception {
		byte[] salt = new byte[8];
		Random random = new Random();
		random.nextBytes(salt);
		return salt;
	}

	/**
	 * 转换密钥<br>
	 *
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(String password) throws Exception {
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(JdkPBE.ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		return secretKey;
	}

	/**
	 * 加密
	 *
	 * @param data
	 *            数据
	 * @param password
	 *            密码
	 * @param salt
	 *            盐
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String password, byte[] salt) throws Exception {
		Key key = JdkPBE.toKey(password);
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
		Cipher cipher = Cipher.getInstance(JdkPBE.ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 *
	 * @param data
	 *            数据
	 * @param password
	 *            密码
	 * @param salt
	 *            盐
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String password, byte[] salt) throws Exception {
		Key key = JdkPBE.toKey(password);
		PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
		Cipher cipher = Cipher.getInstance(JdkPBE.ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		return cipher.doFinal(data);
	}

	public static byte[] decryptBASE64(String data) {
		return Base64.decodeBase64(data);
	}

	public static String encryptBASE64(byte[] data) {
		return new String(Base64.encodeBase64(data));
	}
}
