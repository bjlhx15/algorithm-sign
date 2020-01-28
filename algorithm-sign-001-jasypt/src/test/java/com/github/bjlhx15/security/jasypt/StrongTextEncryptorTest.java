package com.github.bjlhx15.common.help.jasypt;

import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;
import org.junit.Before;
import org.junit.Test;

public class StrongTextEncryptorTest {

    StrongTextEncryptor textEncryptor;

    @Before
    public void setUp() {
        textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
    }

    @Test
    public void encrypt() {
        // 加密
        System.out.println(textEncryptor.encrypt("root@1234"));
        //KU6h0PmBBJ2mZa1zq3PkaXmhmxnTT9pL
        //DOiGEcatVuYitcWFkdS5MzeA2W3ZttN0
    }

    @Test
    public void decyptPwd() {

        // 解密
//        root@1234
        System.out.println(textEncryptor.decrypt("KU6h0PmBBJ2mZa1zq3PkaXmhmxnTT9pL"));

//        root@1234
        System.out.println(textEncryptor.decrypt("DOiGEcatVuYitcWFkdS5MzeA2W3ZttN0"));
    }
}
