package com.github.bjlhx15.security.asymmetric002ecc;

import org.junit.Test;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.util.Base64;

import static org.junit.Assert.*;

public class BcEccTest {

    @Test
    public void initKeyPair() throws Exception {
        String msg="我是测试数据";
        KeyPair keyPair = BcEcc.initKeyPair("", 256);
        System.out.println("pub:"+ Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        System.out.println("pri:"+ Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));

        byte[] encrypt = BcEcc.encrypt(msg.getBytes(), keyPair.getPublic());
        System.out.println("encrypt DAta:"+ Base64.getEncoder().encodeToString(encrypt));

        byte[] decrypt = BcEcc.decrypt(encrypt, keyPair.getPrivate());
        System.out.println("txt DAta:"+ new String(decrypt));
    }
}