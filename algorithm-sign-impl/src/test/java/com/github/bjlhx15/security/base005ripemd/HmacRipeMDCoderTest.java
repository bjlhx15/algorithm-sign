package com.github.bjlhx15.security.base005ripemd;

import org.junit.Test;

import static org.junit.Assert.*;

public class HmacRipeMDCoderTest {

    @Test
    public void encodeHmacRipeMDHex() throws Exception {
        String str = "RIPEMD消息摘要";
        System.out.println("原文：" + str);
        String key = HmacRipeMDCoder.initHmacRipeMDKey(HmacRipeMDCoder.HmacRipeMD128);
        String hex = HmacRipeMDCoder.encodeHmacRipeMDHex(HmacRipeMDCoder.HmacRipeMD128, key,str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + hex);


        key = HmacRipeMDCoder.initHmacRipeMDKey(HmacRipeMDCoder.HmacRipeMD160);
        hex = HmacRipeMDCoder.encodeHmacRipeMDHex(HmacRipeMDCoder.HmacRipeMD160, key,str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + hex);
    }
}