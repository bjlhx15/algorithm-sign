package com.lhx.algorithm.base58;

import org.junit.Test;

public class Base58Test {

	@Test
	public void testBASE58Encoder() throws Exception {
		// JDK里sun.misc套件下的BASE64Encoder和BASE64Decoder这两个类
		String encode = Base58.encode("测试".getBytes("UTF-8"), "UTF-8");
		System.out.println(encode);
	}

	@Test
	public void testBASE64Decoder() throws Exception {
		// JDK里sun.misc套件下的BASE64Encoder和BASE64Decoder这两个类
		String encode = new String(Base58.decode("2ytQwSoWc"), "UTF-8");
		System.out.println(encode);
	}
}
