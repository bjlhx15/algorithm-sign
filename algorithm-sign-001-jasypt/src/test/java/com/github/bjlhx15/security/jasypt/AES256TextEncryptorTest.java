package com.github.bjlhx15.common.help.jasypt;

import org.jasypt.util.text.AES256TextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;
import org.junit.Before;
import org.junit.Test;

public class AES256TextEncryptorTest {

    AES256TextEncryptor textEncryptor;

    @Before
    public void setUp() {
        textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
    }

    @Test
    public void encrypt() {
        // 加密
        System.out.println(textEncryptor.encrypt("root@1234"));
        //n6T6UM9+hLKg6QMl+r9snFvXULR3YAK2rhqQpxiAh+f0dkXVhcP6ak/Soy+9gAs8
    }

    @Test
    public void decyptPwd() {
        // 解密
//        root@1234
        System.out.println(textEncryptor.decrypt("n6T6UM9+hLKg6QMl+r9snFvXULR3YAK2rhqQpxiAh+f0dkXVhcP6ak/Soy+9gAs8"));
    }
}
