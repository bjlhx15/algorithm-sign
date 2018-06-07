package com.lhx.symmetryencrypt.desAndaesAndrc4;

import org.junit.Assert;
import org.junit.Test;

public class JdkSymmetryEncryptTest {
	@Test
	public void testEncrypt() throws Exception {
		String inputStr = "RC4";
		JdkSymmetryEncrypt.ALGORITHM = "RC4";
		String key = JdkSymmetryEncrypt.initKey();
		System.err.println("原文:\t" + inputStr);

		System.err.println("密钥:\t" + key);

		byte[] inputData = inputStr.getBytes();
		inputData = JdkSymmetryEncrypt.encrypt(inputData, key);

		System.err.println("加密后:\t" + JdkSymmetryEncrypt.encryptBASE64(inputData));

		byte[] outputData = JdkSymmetryEncrypt.decrypt(inputData, key);
		String outputStr = new String(outputData);

		System.err.println("解密后:\t" + outputStr);

		Assert.assertEquals(inputStr, outputStr);
	}

	@Test
	public void testEncryptDES() throws Exception {
		String inputStr = "DES";
		JdkSymmetryEncrypt.ALGORITHM = "DES";
		String key = JdkSymmetryEncrypt.initKey();
		System.err.println("原文:\t" + inputStr);

		System.err.println("密钥:\t" + key);

		byte[] inputData = inputStr.getBytes();
		inputData = JdkSymmetryEncrypt.encrypt(inputData, key);

		System.err.println("加密后:\t" + JdkSymmetryEncrypt.encryptBASE64(inputData));

		byte[] outputData = JdkSymmetryEncrypt.decrypt(inputData, key);
		String outputStr = new String(outputData);

		System.err.println("解密后:\t" + outputStr);

		Assert.assertEquals(inputStr, outputStr);
	}
}
