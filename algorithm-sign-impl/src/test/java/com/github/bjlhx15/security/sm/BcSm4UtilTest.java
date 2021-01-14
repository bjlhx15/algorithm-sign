package com.github.bjlhx15.security.sm;

import com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4.AbstractSymmetric;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.IllegalBlockSizeException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author lihongxu6
 * @version 1.0
 * @className BcSm4UtilTest
 * @description TODO
 * @date 2021-01-13 23:47
 */
public class BcSm4UtilTest {
    byte[] key = BcSm4Util.generateKey();
    byte[] iv = null;

    String text = "我是加密数据，请测试。。8888";

    public BcSm4UtilTest() throws NoSuchProviderException, NoSuchAlgorithmException {
    }

    @Test
    public void bcSm4UtilTest() throws Exception {
        List<String> algorithm = new ArrayList<>();
        algorithm.add(("SM4/ECB/NOPADDING"));
        algorithm.add(("SM4/ECB/PKCS5PADDING"));
        algorithm.add(("SM4/ECB/ISO10126PADDING"));
        algorithm.add(("SM4/CBC/NOPADDING"));
        algorithm.add(("SM4/CBC/PKCS5PADDING"));
        algorithm.add(("SM4/CBC/ISO10126PADDING"));
        algorithm.add(("SM4/PCBC/NOPADDING"));
        algorithm.add(("SM4/PCBC/PKCS5PADDING"));
        algorithm.add(("SM4/PCBC/ISO10126PADDING"));
        algorithm.add(("SM4/CTR/NOPADDING"));
        algorithm.add(("SM4/CTR/PKCS5PADDING"));
        algorithm.add(("SM4/CTR/ISO10126PADDING"));
        algorithm.add(("SM4/CTS/NOPADDING"));
        algorithm.add(("SM4/CTS/PKCS5PADDING"));
        algorithm.add(("SM4/CTS/ISO10126PADDING"));
        if (iv == null)
            iv = AbstractSymmetric.initIv(16);

        for (String s : algorithm) {
            //SM4加密
            try {
                System.out.println("SM4加密算法： " + s);
                System.out.println("SM4加密原始数据： " + text);
                System.out.println("SM4加密key： " + Base64.getEncoder().encodeToString(key));
                System.out.println("SM4加密iv： " + Base64.getEncoder().encodeToString(iv));

                byte[] encrypt = BcSm4Util.encrypt(s, key, iv, text.getBytes());
                System.out.println("SM4加密数据密文： " + Base64.getEncoder().encodeToString(encrypt));

                //SM4解密
                byte[] decrypt = BcSm4Util.decrypt(s, key, iv, encrypt);
                System.out.println("SM4解密数据： " + new String(decrypt));
            } catch (Exception e) {
                if (e instanceof IllegalBlockSizeException) {
                    System.err.println("SM4解密数据：算法 " + s + "数据需自己手工对齐");
                } else {
                    System.err.println("SM4解密数据：算法 " + s +"::"+ e.getMessage());
                }
            } finally {
                System.err.println("---------------------------------------");
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}