package com.lhx.basealgorithm.base64;

import org.junit.Test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class SunJdkBase64Test {

	@Test
	public void testBASE64Encoder() throws Exception {
		// JDK里sun.misc套件下的BASE64Encoder和BASE64Decoder这两个类
		String encode = new BASE64Encoder().encode("测试".getBytes("UTF-8"));
		System.out.println(encode);
	}

	@Test
	public void testBASE64Decoder() throws Exception {
		// JDK里sun.misc套件下的BASE64Encoder和BASE64Decoder这两个类
		String encode = new String(new BASE64Decoder().decodeBuffer("5rWL6K+V"), "UTF-8");
		System.out.println(encode);
	}
}
