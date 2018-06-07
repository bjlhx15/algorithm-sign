package com.lhx.asymmetryencrypt.rsa.sign;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * RSA签名流程：
 *
 * 发送方—>构建密钥对-》公布密钥给接收方—>使用私钥对数据签名-》发送签名、数据给接收方。<br>
 * 接收方—》使用公钥、签名验证数据
 *
 * @author Administrator
 *
 */
public class JdkRsaSign {
	/**
	 *
	 * 使用rsa签名 发送方—>构建密钥对-》公布密钥给接收方。 发送方—>使用私钥对数据签名-》发送签名、数据给接收方—》接收方使用公钥、签名验证数据
	 */
	/**
	 *
	 * @param signAlgorithm
	 *            算法 密钥长度 默认长度 签名长度 实现的方<br>
	 *            MD2withRSA 512-65536（64的整数倍） 1024 同密钥 JDK<br>
	 *            MD5withRSA 同上 1024 同密钥 JDK<br>
	 *            SHA1withRSA ... 1024 同密钥 JDK<br>
	 *            SHA224withRSA ... 2048 同密钥 BC<br>
	 *            SHA256withRSA ... 2048 同密钥 BC<br>
	 *            SHA384withRSA ... 2048 同密钥 BC<br>
	 *            SHA512withRSA ... 2048 同密钥 BC<br>
	 *            RIPEMD128withRSA 2048 同密钥 BC<br>
	 *            RIPEMD160withRSA 同上 2048 同密钥 BC<br>
	 * @param src
	 */
	public static void jdkRSASign(String signAlgorithm, String src) {
		try {
			// 初始化签名
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

			// 2.执行签名
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(privateKey);
			signature.update(src.getBytes());
			byte[] result = signature.sign();
			System.out.println("jdk ras sign:" + Hex.encodeHexString(result));

			// 3.验证签名
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			keyFactory = KeyFactory.getInstance("RSA");// 为了数据的完整性
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

			signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(publicKey);
			signature.update(src.getBytes());
			boolean bool = signature.verify(result);
			System.out.println("jdk rsa verify:" + bool);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
