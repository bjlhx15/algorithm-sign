package com.lhx.sign.dsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class JdkDsaDemo2 {
	public static final String ALGORITHM = "DSA";

	/**
	 * 默认密钥字节数
	 *
	 * <pre>
	 * DSA
	 * Default Keysize 1024
	 * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
	 * </pre>
	 */
	private static final int KEY_SIZE = 1024;

	/**
	 * 默认种子
	 */
	private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";

	private static final String PUBLIC_KEY = "DSAPublicKey";
	private static final String PRIVATE_KEY = "DSAPrivateKey";

	/**
	 * 用私钥对信息生成数字签名
	 *
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 *
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = JdkDsaDemo2.decryptBASE64(privateKey);

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(JdkDsaDemo2.ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initSign(priKey);
		signature.update(data);

		return JdkDsaDemo2.encryptBASE64(signature.sign());
	}

	/**
	 * 校验数字签名
	 *
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 *
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 *
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

		// 解密由base64编码的公钥
		byte[] keyBytes = JdkDsaDemo2.decryptBASE64(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(JdkDsaDemo2.ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(JdkDsaDemo2.decryptBASE64(sign));
	}

	/**
	 * 生成密钥
	 *
	 * @param seed
	 *            种子
	 * @return 密钥对象
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(String seed) throws Exception {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(JdkDsaDemo2.ALGORITHM);
		// 初始化随机产生器
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(seed.getBytes());
		keygen.initialize(JdkDsaDemo2.KEY_SIZE, secureRandom);

		KeyPair keys = keygen.genKeyPair();

		DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();
		DSAPrivateKey privateKey = (DSAPrivateKey) keys.getPrivate();

		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put(JdkDsaDemo2.PUBLIC_KEY, publicKey);
		map.put(JdkDsaDemo2.PRIVATE_KEY, privateKey);

		return map;
	}

	/**
	 * 默认生成密钥
	 *
	 * @return 密钥对象
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		return JdkDsaDemo2.initKey(JdkDsaDemo2.DEFAULT_SEED);
	}

	/**
	 * 取得私钥
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(JdkDsaDemo2.PRIVATE_KEY);

		return JdkDsaDemo2.encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(JdkDsaDemo2.PUBLIC_KEY);

		return JdkDsaDemo2.encryptBASE64(key.getEncoded());
	}

	public static byte[] decryptBASE64(String data) {
		return Base64.decodeBase64(data);
	}

	public static String encryptBASE64(byte[] data) {
		return new String(Base64.encodeBase64(data));
	}
}
