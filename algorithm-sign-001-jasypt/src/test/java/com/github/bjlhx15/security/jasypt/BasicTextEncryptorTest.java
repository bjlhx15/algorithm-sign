package com.github.bjlhx15.common.help.jasypt;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Before;
import org.junit.Test;

public class BasicTextEncryptorTest {

    BasicTextEncryptor textEncryptor;

    @Before
    public void setUp() {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
    }

    @Test
    public void encrypt() {
        // 加密
        System.out.println(textEncryptor.encrypt("root@1234"));
        //TJetNWzmC4os1CCb+gHtz+5MpL9NFMML
        //KCTSu/Dv1elE1A/ZyppCHgJAAwKiez/p
    }

    @Test
    public void decyptPwd() {
        // 解密
//        root@1234
        System.out.println(textEncryptor.decrypt("TJetNWzmC4os1CCb+gHtz+5MpL9NFMML"));

//        root@1234
        System.out.println(textEncryptor.decrypt("KCTSu/Dv1elE1A/ZyppCHgJAAwKiez/p"));
    }
}
