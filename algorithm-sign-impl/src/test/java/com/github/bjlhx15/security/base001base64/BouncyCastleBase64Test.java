package com.github.bjlhx15.security.base001base64;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BouncyCastleBase64Test {

    BouncyCastleBase64 bouncyCastleBase64;

    @Before
    public void setUp() throws Exception {
        bouncyCastleBase64 = new BouncyCastleBase64();
    }

    @Test
    public void base64Encoder() throws Exception {
        String msg = "aaaaa";
        String encoder = bouncyCastleBase64.base64Encoder(msg, "utf-8");
        String decoder = bouncyCastleBase64.base64Decoder(encoder, "utf-8");
        Assert.assertEquals(msg, decoder);
    }
}