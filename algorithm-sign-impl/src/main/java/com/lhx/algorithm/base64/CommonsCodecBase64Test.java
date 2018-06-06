package com.lhx.algorithm.base64;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class CommonsCodecBase64Test {

	@Test
	public void testBASE64Encoder() throws Exception {
		byte[] base64 = Base64.encodeBase64("测试".getBytes("UTF-8"));
		String string = new String(base64);
		System.out.println(string);
	}

	@Test
	public void testBASE64Decoder() throws Exception {
		byte[] base64 = Base64.decodeBase64("5rWL6K+V");
		String string = new String(base64, "UTF-8");
		System.out.println(string);
	}
}
