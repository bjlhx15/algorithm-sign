package com.github.bjlhx15.common.help.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;
import org.junit.Before;
import org.junit.Test;

public class StandardPBEStringEncryptorTest {
    StandardPBEStringEncryptor textEncryptor;

    @Before
    public void setUp() {
        textEncryptor = new StandardPBEStringEncryptor();
//        textEncryptor.setAlgorithm("");//自行指定
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
    }

    @Test
    public void encrypt() {
        // 加密
        System.out.println(textEncryptor.encrypt("root@1234"));
        //Han0rFt6K2jhvrK5swPpD/ctoUMPckIO
        //upkr4Rc6bhmpUXhdRoT9qqkhiSfEhTvS
    }

    @Test
    public void decyptPwd() {
        // 解密
//        root@1234
        System.out.println(textEncryptor.decrypt("Han0rFt6K2jhvrK5swPpD/ctoUMPckIO"));
//        root@1234
        System.out.println(textEncryptor.decrypt("upkr4Rc6bhmpUXhdRoT9qqkhiSfEhTvS"));
    }
}
