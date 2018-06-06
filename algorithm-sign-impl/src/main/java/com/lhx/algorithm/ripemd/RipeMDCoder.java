package com.lhx.algorithm.ripemd;

import java.security.MessageDigest;
import java.security.Security;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * RipeMD128,RipeMD160,RipeMD256,RipeMD320
 * 
 * @author Administrator
 *
 */
public class RipeMDCoder {
	//////////////////// RipeMD256消息摘要处理///////////////////////////
	/**
	 * RipeMD消息摘要
	 *
	 * @param data
	 *            待处理的消息摘要数据
	 * @return byte[] 消息摘要
	 */
	public static byte[] encodeRipeMD(String algorithm, byte[] data) throws Exception {
		// 加入BouncyCastleProvider的支持
		Security.addProvider(new BouncyCastleProvider());
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance(algorithm);
		// 执行消息摘要
		return md.digest(data);

	}

	/**
	 * RipeMDHex消息摘要
	 *
	 * @param data
	 *            待处理的消息摘要数据
	 * @return String 消息摘要
	 **/
	public static String encodeRipeMDHex(String algorithm, byte[] data) throws Exception {
		// 执行消息摘要
		byte[] b = RipeMDCoder.encodeRipeMD(algorithm, data);
		// 做十六进制的编码处理
		return new String(Hex.encodeHex(b));
	}

}
