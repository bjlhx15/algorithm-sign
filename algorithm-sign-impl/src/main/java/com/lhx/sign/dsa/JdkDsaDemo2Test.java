package com.lhx.sign.dsa;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class JdkDsaDemo2Test {
	@Test
	public void test() throws Exception {
		String inputStr = "abc";
		byte[] data = inputStr.getBytes();

		// 构建密钥
		Map<String, Object> keyMap = JdkDsaDemo2.initKey();

		// 获得密钥
		String publicKey = JdkDsaDemo2.getPublicKey(keyMap);
		String privateKey = JdkDsaDemo2.getPrivateKey(keyMap);

		System.err.println("公钥:\r" + publicKey);
		System.err.println("私钥:\r" + privateKey);

		// 产生签名
		String sign = JdkDsaDemo2.sign(data, privateKey);
		System.err.println("签名:\r" + sign);

		// 验证签名
		boolean status = JdkDsaDemo2.verify(data, publicKey, sign);
		System.err.println("状态:\r" + status);
		Assert.assertTrue(status);

	}
}
