package com.lhx.basealgorithm.md5andsha;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class MD5AndShaTest {

	@Test
	public void testMD5() throws Exception {
		String md5Msg = this.msgSafeBase("测试MD5", "MD5");
		System.out.println(md5Msg);// c2dbb895a66c3ca924ccdbea49fa6884
	}

	@Test
	public void testSHA() throws Exception {
		// SHA-1，SHA-256,SHA-384，和SHA-512
		String hashMsg = this.msgSafeBase("测试SHA", "SHA-1");
		System.out.println(hashMsg);
		// sha1:9bfec0ff7027c76c28fdaa51bd5a619c5e2f69bb
	}

	public String msgSafeBase(String msg, String algorithmName) throws Exception {
		MessageDigest m = MessageDigest.getInstance(algorithmName);
		m.update(msg.getBytes("UTF8"));
		byte s[] = m.digest();
		return Hex.encodeHexString(s);
	}

}
