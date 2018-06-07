package com.lhx.symmetryencrypt.pbe;

import org.junit.Assert;
import org.junit.Test;

public class JdkPBETest {
	@Test
	public void testEncrypt() throws Exception {
		String inputStr = "abc";
		System.err.println("原文: " + inputStr);
		byte[] input = inputStr.getBytes();

		String pwd = "efg";
		System.err.println("密码: " + pwd);

		byte[] salt = JdkPBE.initSalt();

		byte[] data = JdkPBE.encrypt(input, pwd, salt);

		System.err.println("加密后: " + JdkPBE.encryptBASE64(data));

		byte[] output = JdkPBE.decrypt(data, pwd, salt);
		String outputStr = new String(output);

		System.err.println("解密后: " + outputStr);
		Assert.assertEquals(inputStr, outputStr);
	}
}
