package com.github.bjlhx15.security.base001base64;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommonsCodecBase64Test {
    CommonsCodecBase64 commonsCodecBase64;

    @Before
    public void setUp() throws Exception {
        commonsCodecBase64 = new CommonsCodecBase64();
    }

    @Test
    public void testBASE64Encoder() throws Exception {
        String encodeMsg = "aaa";
        String encoder = commonsCodecBase64.base64Encoder(encodeMsg, "utf-8");
        String decoder = commonsCodecBase64.base64Decoder(encoder, "utf-8");
        Assert.assertEquals(encodeMsg, decoder);

    }
}