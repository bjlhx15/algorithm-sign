package com.lhx.basealgorithm.ripemd;

public class HmacRipeMDCoderTest {
	public static void main(String[] args) throws Exception {
		System.out.println("================以下的算法支持是HmacRipeMD系列，现阶段只有BouncyCastle支持=======================");

		String str = "RIPEMD消息摘要";
		System.out.println("原文：" + str);
		// 初始化密钥
		byte[] key5 = HmacRipeMDCoder.initHmacRipeMDKey("HmacRipeMD128");
		// 获取摘要信息
		byte[] data5 = HmacRipeMDCoder.encodeHmacRipeMD("HmacRipeMD128", str.getBytes(), key5);
		String datahex5 = HmacRipeMDCoder.encodeHmacRipeMDHex("HmacRipeMD128", str.getBytes(), key5);
		System.out.println("Bouncycastle HmacRipeMD128的密钥:" + key5.toString());
		System.out.println("Bouncycastle HmacRipeMD128算法摘要：" + data5.toString());
		System.out.println("Bouncycastle HmacRipeMD128Hex算法摘要：" + datahex5.toString());
		System.out.println();

	}
}
