package com.lhx.basealgorithm.hmactiger;

import com.lhx.basealgorithm.ripemd.HmacRipeMDCoder;

/**
 * HmacTiger128,HmacRipeMD160
 *
 * @author Administrator
 *
 */
public class HmacTigerCoderTest {
	public static void main(String[] args) throws Exception {
		System.out.println("================以下的算法支持是HmacTiger系列，现阶段只有BouncyCastle支持=======================");

		String str = "HmacTiger消息摘要";
		System.out.println("原文：" + str);
		// 初始化密钥
		byte[] key5 = HmacRipeMDCoder.initHmacRipeMDKey("HmacTiger");
		// 获取摘要信息
		byte[] data5 = HmacRipeMDCoder.encodeHmacRipeMD("HmacTiger", str.getBytes(), key5);
		String datahex5 = HmacRipeMDCoder.encodeHmacRipeMDHex("HmacTiger", str.getBytes(), key5);
		System.out.println("Bouncycastle HmacTiger的密钥:" + key5.toString());
		System.out.println("Bouncycastle HmacTiger算法摘要：" + data5.toString());
		System.out.println("Bouncycastle HmacTigerHex算法摘要：" + datahex5.toString());
		System.out.println();

	}
}
