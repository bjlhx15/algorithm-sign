package com.github.bjlhx15.security.sm;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author lihongxu6
 * @version 1.0
 * @className BcSm3UtilTest
 * @description TODO
 * @date 2021-01-13 23:17
 */
public class BcSm3UtilTest {
    private String test="woshi测试数据。。..";

    @Test
    public void sm3() throws Exception {
        String s = BcSm3Util.sm3Hex(test.getBytes());
        System.out.println(s);
        String s2 = BcSm3Util.sm3bcHex(test.getBytes());
        System.out.println(s2);
        Assert.assertEquals(s,s2);
    }

    @Test
    public void hmacSm3Hex() {
        String s = BcSm3Util.hmacSm3Hex("AAAA".getBytes(),test.getBytes());
        System.out.println(s);
    }
}