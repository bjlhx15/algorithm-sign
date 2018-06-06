package com.lhx.algorithm.base64;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

public class BouncyCastleBase64Test {

	@Test
	public void testBASE64Encoder() throws Exception {
		// JDK里sun.misc套件下的BASE64Encoder和BASE64Decoder这两个类
		String encode = new String(Base64.encode("测试".getBytes("UTF-8")), "UTF-8");
		System.out.println(encode);
	}

	@Test
	public void testBASE64Decoder() throws Exception {
		// JDK里sun.misc套件下的BASE64Encoder和BASE64Decoder这两个类
		String encode = new String(Base64.decode("5rWL6K+V"), "UTF-8");
		System.out.println(encode);
	}
}
