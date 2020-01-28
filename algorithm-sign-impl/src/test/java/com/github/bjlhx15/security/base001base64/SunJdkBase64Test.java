package com.github.bjlhx15.security.base001base64;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SunJdkBase64Test {

    SunJdkBase64 sunJdkBase64;

    @Before
    public void setUp() throws Exception {
        sunJdkBase64 = new SunJdkBase64();
    }

    @Test
    public void base64Encoder() throws Exception {
        String encodeMsg = "测试";
        String encoder = sunJdkBase64.base64Encoder(encodeMsg, "utf-8");
        String decoder = sunJdkBase64.base64Decoder(encoder, "utf-8");
        Assert.assertEquals(encodeMsg,decoder);
    }

    @Test
    public void base64Decoder() {
    }
}