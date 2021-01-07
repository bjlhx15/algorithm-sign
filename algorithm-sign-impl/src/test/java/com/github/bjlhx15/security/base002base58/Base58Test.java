package com.github.bjlhx15.security.base002base58;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class Base58Test {

    Base58 base58;

    @Before
    public void setUp() throws Exception {
        base58 = new Base58();
    }

    @Test
    public void encode() throws Exception {
        String msg = "453534534534543534:dfsdfsdfsdfsdfsdfdsfdsfs";
        String encode = Base58.encode(msg.getBytes("UTF-8"), "UTF-8");
        System.out.println(encode);

        String dencode = new String(Base58.decode(encode), "UTF-8");
        System.out.println(dencode);
        Assert.assertEquals(msg, dencode);
    }
}