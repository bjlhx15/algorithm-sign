package com.github.bjlhx15.security.base001base64;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Jdk8Base64Test {

    Jdk8Base64 jdk8Base64;
    @Before
    public void setUp() throws Exception {
        jdk8Base64=new Jdk8Base64();
    }

    @Test
    public void base64Encoder() throws Exception {
        String encodeMsg="A";
        String encoder = jdk8Base64.base64Encoder(encodeMsg, "utf-8");
        String decoder = jdk8Base64.base64Decoder(encoder, "utf-8");
        Assert.assertEquals(encodeMsg,decoder);
    }

    @Test
    public void base64Decoder() {
    }
}