package com.github.bjlhx15.common.help.jasypt;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Before;
import org.junit.Test;

public class BasicPasswordEncryptorTest {

    @Before
    public void setUp() {
//        textEncryptor = new BasicPasswordEncryptor();
    }

    @Test
    public void encrypt() {
        BasicPasswordEncryptor textEncryptor = new BasicPasswordEncryptor();
        String encryptPassword = textEncryptor.encryptPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
        System.out.println(encryptPassword);
        boolean checkPassword = textEncryptor.checkPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7", encryptPassword);
        System.out.println(checkPassword);
    }

}
