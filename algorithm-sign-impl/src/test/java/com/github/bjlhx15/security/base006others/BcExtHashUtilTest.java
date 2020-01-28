package com.github.bjlhx15.security.base006others;

import com.github.bjlhx15.security.base005ripemd.RipeMDCoder;
import org.junit.Test;

import static org.junit.Assert.*;

public class BcExtHashUtilTest {

    @Test
    public void encodeExtHash() throws Exception {
        String str = "RIPEMD消息摘要";
        System.out.println("原文：" + str);
        String data1hex = "";

        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.RipeMD128, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.RipeMD160, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.RipeMD256, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.RipeMD320, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);


        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.Tiger, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.Whirlpool, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
        data1hex = BcExtHashUtil.encodeExtHashHex(BcExtHashUtil.Gost3411, str.getBytes());
        System.out.println("十六进制消息摘要算法值：" + data1hex);
    }
}