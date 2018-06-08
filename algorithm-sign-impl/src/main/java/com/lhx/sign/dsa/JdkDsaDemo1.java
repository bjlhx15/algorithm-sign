package com.lhx.sign.dsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Hex;

public class JdkDsaDemo1 {
	private static String src = "dsa security";

	public static void main(String[] args) {
		JdkDsaDemo1.jdkDSA();
	}

	public static void jdkDSA() {
		try {
			// 1.初始化密钥
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
			keyPairGenerator.initialize(512);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			DSAPublicKey dsaPublicKey = (DSAPublicKey) keyPair.getPublic();
			DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate();

			// 2.执行签名
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(dsaPrivateKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Signature signature = Signature.getInstance("SHA1withDSA");
			signature.initSign(privateKey);
			signature.update(JdkDsaDemo1.src.getBytes());
			byte[] res = signature.sign();
			System.out.println("签名：" + Hex.encodeHexString(res));

			// 3.验证签名
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(dsaPublicKey.getEncoded());
			keyFactory = KeyFactory.getInstance("DSA");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			signature = Signature.getInstance("SHA1withDSA");
			signature.initVerify(publicKey);
			signature.update(JdkDsaDemo1.src.getBytes());
			boolean bool = signature.verify(res);
			System.out.println("验证：" + bool);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
