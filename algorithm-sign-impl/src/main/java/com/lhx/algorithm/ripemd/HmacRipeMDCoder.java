package com.lhx.algorithm.ripemd;

import java.security.Security;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * HmacRipeMD128,HmacRipeMD160
 *
 * @author Administrator
 *
 */
public class HmacRipeMDCoder {
	/////////////////////////////// HmacRipeMD-BouncyCastle支持的实现//////////////////////////////////
	/**
	 * 初始化HmacRipeMD128的密钥
	 *
	 * @return byte[] 密钥
	 */
	public static byte[] initHmacRipeMDKey(String algorithm) throws Exception {
		// 加入BouncyCastleProvider的支持
		Security.addProvider(new BouncyCastleProvider());
		// 初始化KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		// 产生密钥
		SecretKey secretKey = keyGenerator.generateKey();
		// 获取密钥
		return secretKey.getEncoded();
	}

	/**
	 * HmacRipeMD128消息摘要
	 *
	 * @param data
	 *            待做摘要处理的数据
	 * @param key
	 *            密钥
	 * @return byte[] 消息摘要
	 */
	public static byte[] encodeHmacRipeMD(String algorithm, byte[] data, byte[] key) throws Exception {
		// 加入BouncyCastleProvider的支持
		Security.addProvider(new BouncyCastleProvider());
		// 还原密钥，因为密钥是以byte形式为消息传递算法所拥有
		SecretKey secretKey = new SecretKeySpec(key, algorithm);
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// 初始化Mac
		mac.init(secretKey);
		// 执行消息摘要处理
		return mac.doFinal(data);
	}

	/**
	 * HmacRipeMDHex消息摘要
	 *
	 * @param data
	 *            待做消息摘要处理的数据
	 * @param String
	 *            密钥
	 * @return byte[] 消息摘要
	 */
	public static String encodeHmacRipeMDHex(String algorithm, byte[] data, byte[] key) throws Exception {
		// 执行消息摘要处理
		byte[] b = HmacRipeMDCoder.encodeHmacRipeMD(algorithm, data, key);
		// 做十六进制转换
		return new String(Hex.encodeHex(b));
	}
}
