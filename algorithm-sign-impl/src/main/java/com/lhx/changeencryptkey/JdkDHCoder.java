package com.lhx.changeencryptkey;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * DH密码交换协议<br>
 * DH加密流程：<br>
 * 1.初始化DH算法密钥对：<br>
 * 1.1.发送方—>构建发送方密钥-》公布发送方密钥给接收方-》使用接收者公钥构建发送方本地密钥<br>
 * 1.2.接收方—》使用发送方密钥密钥构建接收方密钥-》公布接收者公钥给发送方—》构建接收方本地密钥<br>
 * 2.DH算法加密消息传递：<br>
 * 2.1.发送方—>使用本地密钥加密消息—》发送加密消息给接收方<br>
 * 2.2.接收方—》使用本地密钥解密消息<br>
 *
 * @author 木子旭
 * @since 2017年3月17日上午9:18:14
 * @version %I%,%G%
 */
public class JdkDHCoder {
	public static final String ALGORITHM = "DH";

	/**
	 * 默认密钥字节数
	 *
	 * <pre>
	 * DH
	 * Default Keysize 1024
	 * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
	 * </pre>
	 */
	private static final int KEY_SIZE = 1024;

	/**
	 * DH加密下需要一种对称加密算法对数据加密，这里我们使用DES，也可以使用其他对称加密算法。
	 */
	public static final String SECRET_ALGORITHM = "DES";
	private static final String PUBLIC_KEY = "DHPublicKey";
	private static final String PRIVATE_KEY = "DHPrivateKey";

	/**
	 * 初始化甲方密钥
	 *
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(JdkDHCoder.ALGORITHM);
		keyPairGenerator.initialize(JdkDHCoder.KEY_SIZE);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 甲方公钥
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();

		// 甲方私钥
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(JdkDHCoder.PUBLIC_KEY, publicKey);
		keyMap.put(JdkDHCoder.PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 初始化乙方密钥
	 *
	 * @param key
	 *            甲方公钥
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(String key) throws Exception {
		// 解析甲方公钥
		byte[] keyBytes = JdkDHCoder.decryptBASE64(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(JdkDHCoder.ALGORITHM);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		// 由甲方公钥构建乙方密钥
		DHParameterSpec dhParamSpec = ((DHPublicKey) pubKey).getParams();

		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
		keyPairGenerator.initialize(dhParamSpec);

		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		// 乙方公钥
		DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();

		// 乙方私钥
		DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(JdkDHCoder.PUBLIC_KEY, publicKey);
		keyMap.put(JdkDHCoder.PRIVATE_KEY, privateKey);

		return keyMap;
	}

	/**
	 * 加密<br>
	 *
	 * @param data
	 *            待加密数据
	 * @param publicKey
	 *            甲方公钥
	 * @param privateKey
	 *            乙方私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String publicKey, String privateKey) throws Exception {

		// 生成本地密钥
		SecretKey secretKey = JdkDHCoder.getSecretKey(publicKey, privateKey);

		// 数据加密
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 *
	 * @param data
	 *            待解密数据
	 * @param publicKey
	 *            乙方公钥
	 * @param privateKey
	 *            乙方私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String publicKey, String privateKey) throws Exception {

		// 生成本地密钥
		SecretKey secretKey = JdkDHCoder.getSecretKey(publicKey, privateKey);
		// 数据解密
		Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		return cipher.doFinal(data);
	}

	/**
	 * 构建密钥
	 *
	 * @param publicKey
	 *            公钥
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	private static SecretKey getSecretKey(String publicKey, String privateKey) throws Exception {
		// 初始化公钥
		byte[] pubKeyBytes = JdkDHCoder.decryptBASE64(publicKey);

		KeyFactory keyFactory = KeyFactory.getInstance(JdkDHCoder.ALGORITHM);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		// 初始化私钥
		byte[] priKeyBytes = JdkDHCoder.decryptBASE64(privateKey);

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKeyBytes);
		Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
		keyAgree.init(priKey);
		keyAgree.doPhase(pubKey, true);

		// 生成本地密钥
		SecretKey secretKey = keyAgree.generateSecret(JdkDHCoder.SECRET_ALGORITHM);

		return secretKey;
	}

	/**
	 * 取得私钥
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(JdkDHCoder.PRIVATE_KEY);

		return JdkDHCoder.encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(JdkDHCoder.PUBLIC_KEY);

		return JdkDHCoder.encryptBASE64(key.getEncoded());
	}

	public static byte[] decryptBASE64(String data) {
		return Base64.decodeBase64(data);
	}

	public static String encryptBASE64(byte[] data) {
		return new String(Base64.encodeBase64(data));
	}

}
