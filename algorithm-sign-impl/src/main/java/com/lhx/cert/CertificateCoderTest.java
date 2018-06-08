package com.lhx.cert;

import org.junit.Assert;
import org.junit.Test;

public class CertificateCoderTest {
	private String password = "123456";
	private String alias = "www.lhx.org";
	private String certificatePath = "d:/lhx.cer";
	private String keyStorePath = "d:/lhx.keystore";

	@Test
	public void test() throws Exception {
		System.err.println("公钥加密——私钥解密");
		String inputStr = "Ceritifcate";
		byte[] data = inputStr.getBytes();

		byte[] encrypt = CertificateCoder.encryptByPublicKey(data, this.certificatePath);

		byte[] decrypt = CertificateCoder.decryptByPrivateKey(encrypt, this.keyStorePath, this.alias, this.password);
		String outputStr = new String(decrypt);

		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);

		// 验证数据一致
		Assert.assertArrayEquals(data, decrypt);

		// 验证证书有效
		Assert.assertTrue(CertificateCoder.verifyCertificate(this.certificatePath));

	}

	@Test
	public void testSign() throws Exception {
		System.err.println("私钥加密——公钥解密");

		String inputStr = "sign";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = CertificateCoder.encryptByPrivateKey(data, this.keyStorePath, this.alias, this.password);

		byte[] decodedData = CertificateCoder.decryptByPublicKey(encodedData, this.certificatePath);

		String outputStr = new String(decodedData);
		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
		Assert.assertEquals(inputStr, outputStr);

		System.err.println("私钥签名——公钥验证签名");
		// 产生签名
		String sign = CertificateCoder.sign(encodedData, this.keyStorePath, this.alias, this.password);
		System.err.println("签名:\r" + sign);

		// 验证签名
		boolean status = CertificateCoder.verify(encodedData, sign, this.certificatePath);
		System.err.println("状态:\r" + status);
		Assert.assertTrue(status);

	}
}
