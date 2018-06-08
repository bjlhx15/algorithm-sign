package com.lhx.changeencryptkey;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author 木子旭
 * @since 2017年3月17日上午9:18:14
 * @version %I%,%G%
 */
public class JdkDHCoderTest {
	@Test
	public void test() throws Exception {
		// 生成甲方密钥对儿
		Map<String, Object> aKeyMap = JdkDHCoder.initKey();
		String aPublicKey = JdkDHCoder.getPublicKey(aKeyMap);
		String aPrivateKey = JdkDHCoder.getPrivateKey(aKeyMap);

		System.err.println("甲方公钥:\r" + aPublicKey);
		System.err.println("甲方私钥:\r" + aPrivateKey);

		// 乙方由甲方公钥产生乙方本地密钥对儿
		Map<String, Object> bKeyMap = JdkDHCoder.initKey(aPublicKey);
		String bPublicKey = JdkDHCoder.getPublicKey(bKeyMap);
		String bPrivateKey = JdkDHCoder.getPrivateKey(bKeyMap);

		System.err.println("乙方公钥:\r" + bPublicKey);
		System.err.println("乙方私钥:\r" + bPrivateKey);

		System.err.println("乙方构建加密，甲方解密 ");
		String aInput = "abc ";
		System.err.println("原文: " + aInput);

		// 乙方构建密钥消息，使用甲方公钥，乙方私钥构建密文
		byte[] aCode = JdkDHCoder.encrypt(aInput.getBytes(), aPublicKey, bPrivateKey);

		// 甲方解密乙方加密消息，使用乙方公钥，甲方私钥解密
		byte[] aDecode = JdkDHCoder.decrypt(aCode, bPublicKey, aPrivateKey);
		String aOutput = (new String(aDecode));

		System.err.println("解密: " + aOutput);

		Assert.assertEquals(aInput, aOutput);

		System.err.println(" ===============反过来加密解密================== ");
		System.err.println("甲方构建加密，乙方解密 ");
		String bInput = "def ";
		System.err.println("原文: " + bInput);

		// 甲方构建密钥消息，由乙方公钥，甲方私钥构建密文
		byte[] bCode = JdkDHCoder.encrypt(bInput.getBytes(), bPublicKey, aPrivateKey);

		// 乙方解密甲方加密消息，使用甲方公钥，乙方私钥解密
		byte[] bDecode = JdkDHCoder.decrypt(bCode, aPublicKey, bPrivateKey);
		String bOutput = (new String(bDecode));

		System.err.println("解密: " + bOutput);

		Assert.assertEquals(bInput, bOutput);
	}

}
