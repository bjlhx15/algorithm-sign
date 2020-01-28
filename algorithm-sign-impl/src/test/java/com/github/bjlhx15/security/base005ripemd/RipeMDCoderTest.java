package com.github.bjlhx15.security.base005ripemd;

import org.junit.Test;

public class RipeMDCoderTest {

    @Test
    public void encodeRipeMD() throws Exception {
        String str = "RIPEMD消息摘要";
        System.out.println("原文：" + str);
        String data1hex = RipeMDCoder.encodeRipeMDHex(RipeMDCoder.RipeMD128, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = RipeMDCoder.encodeRipeMDHex(RipeMDCoder.RipeMD160, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = RipeMDCoder.encodeRipeMDHex(RipeMDCoder.RipeMD256, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = RipeMDCoder.encodeRipeMDHex(RipeMDCoder.RipeMD320, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
    }
}