package com.lhx.algorithm.hmactiger;

import com.lhx.algorithm.ripemd.RipeMDCoder;

public class TigerTest {
	public static void main(String[] args) throws Exception {
		String str = "Tiger消息摘要";
		System.out.println("原文：" + str);
		byte[] data1 = RipeMDCoder.encodeRipeMD("Tiger", str.getBytes());
		String data1hex = RipeMDCoder.encodeRipeMDHex("Tiger", str.getBytes());
		System.out.println("Tiger的消息摘要算法值：" + data1.toString());
		System.out.println("Tiger的十六进制消息摘要算法值：" + data1hex);
		System.out.println();
	}
}
