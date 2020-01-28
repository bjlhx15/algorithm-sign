package com.github.bjlhx15.common.help.jasypt;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RC128TextEncryptorTest {

    RC128TextEncryptor textEncryptor;

    @Before
    public void setUp() {
        textEncryptor = new RC128TextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
    }

    @Test
    public void encrypt() {
        // 加密
        System.out.println(textEncryptor.encrypt("root@1234"));
        //zjhmIP38jmvob56qyNevHjs=
        //iMX2aR70CkLGdtlAdhe2XKI=
    }

    @Test
    public void decyptPwd() {
        // 解密
//        root@1234
        System.out.println(textEncryptor.decrypt("zjhmIP38jmvob56qyNevHjs="));

//        root@1234
        System.out.println(textEncryptor.decrypt("iMX2aR70CkLGdtlAdhe2XKI="));
    }
}