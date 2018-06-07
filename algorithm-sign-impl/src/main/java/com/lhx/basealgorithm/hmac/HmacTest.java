package com.lhx.basealgorithm.hmac;

import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class HmacTest {
	@Test
	public void testHashMsgCode() throws Exception {
		String macKey = HmacTest.initMacKey();
		System.out.println(macKey);
		// vTVhh1xBdDTm9/TZhVsOK0+G/Aw2fkCx0gC6KcM7o2lbCy6DyatcUSe66PTu70E7J0r/hhtodcZBPuLI4/aCgw==

		String msgCode = HmacTest.hashMsgCode("测试HMAC".getBytes(), macKey);
		System.out.println(msgCode);
		// 7e4f0f95cfef2c8f5af9799d03798e76
	}

	public static String initMacKey() throws Exception {
		// HmacMD5,HmacSHA1,HmacSHA256,HmacSHA384,HmacSHA512
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");// 初始化KeyGenerator
		SecretKey secretKey = keyGenerator.generateKey();// 产生密钥
		return Base64.getEncoder().encodeToString(secretKey.getEncoded());// 获得密钥
	}

	public static String hashMsgCode(byte[] data, String key) throws Exception {
		// 还原密钥
		SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "HmacMD5");
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());// 实例化MAC
		mac.init(secretKey);// 初始化Mac
		return new String(Hex.encodeHex(mac.doFinal(data)));// 执行摘要
	}
}
