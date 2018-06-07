package com.lhx.basealgorithm.ripemd;

public class RipeMDCoderTest {
	public static void main(String[] args) throws Exception {
		String str = "RIPEMD消息摘要";
		System.out.println("原文：" + str);
		byte[] data1 = RipeMDCoder.encodeRipeMD("RipeMD128", str.getBytes());
		String data1hex = RipeMDCoder.encodeRipeMDHex("RipeMD128", str.getBytes());
		System.out.println("RipeMD128的消息摘要算法值：" + data1.toString());
		System.out.println("RipeMD128的十六进制消息摘要算法值：" + data1hex);
		System.out.println();
	}
}
