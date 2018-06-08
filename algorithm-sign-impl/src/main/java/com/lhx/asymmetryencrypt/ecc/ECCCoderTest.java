package com.lhx.asymmetryencrypt.ecc;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class ECCCoderTest {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");
		String inputStr = "abc";
		byte[] data = inputStr.getBytes();

		Map<String, Object> keyMap = ECCCoder.initKey();

		String publicKey = ECCCoder.getPublicKey(keyMap);
		String privateKey = ECCCoder.getPrivateKey(keyMap);
		System.err.println("公钥: \n" + publicKey);
		System.err.println("私钥： \n" + privateKey);

		byte[] encodedData = ECCCoder.encrypt(data, publicKey);
		byte[] decodedData = ECCCoder.decrypt(encodedData, privateKey);

		String outputStr = new String(decodedData);
		// Chipher不支持EC算法 未能实现
		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
	}
}
