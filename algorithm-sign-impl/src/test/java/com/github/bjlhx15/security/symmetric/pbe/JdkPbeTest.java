package com.github.bjlhx15.security.symmetric.pbe;

import com.sun.crypto.provider.SunJCE;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.SecureRandom;
import java.util.*;

import static org.junit.Assert.*;

public class JdkPbeTest {
    String msg = "测试数据2222";
    Map.Entry<String, String> padding = null;
    Key key = null;
    byte[] salt = null;
    byte[] encrypt = null;
    byte[] decrypt = null;

    AbstractSymmetric symmetric=null;
    @Before
    public void before(){
        symmetric=new JdkPbe();
    }


    @Test
    public void encryptAll() throws Exception {
        System.out.println("原文:" + msg);
        List<String> list = new ArrayList<>();
        list.add("PBEWithMD5AndDES");
        list.add("PBEWithMD5AndTripleDES");

        list.add("PBEWithSHA1AndDESede");
        list.add("PBEWithSHA1AndRC2_40");
        list.add("PBEWithSHA1AndRC4_40");
        list.add("PBEWithSHA1AndRC2_128");
        list.add("PBEWithSHA1AndRC4_128");
        for (String entry : list) {
            try {
                boolean b = encryptCheck(entry,"123456");
                if (b) {
                    System.out.println(entry + ":支持");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(entry + ":不支持");
            }
        }
    }

    @Test
    public void encryptAll2() throws Exception {
        System.out.println("原文:" + msg);
        String algo="PBEWITHHMACSHA1ANDAES_128,PBEWITHHMACSHA1ANDAES_256,PBEWITHHMACSHA224ANDAES_128,PBEWITHHMACSHA224ANDAES_256,PBEWITHHMACSHA256ANDAES_128,PBEWITHHMACSHA256ANDAES_256,PBEWITHHMACSHA384ANDAES_128,PBEWITHHMACSHA384ANDAES_256,PBEWITHHMACSHA512ANDAES_128,PBEWITHHMACSHA512ANDAES_256,PBEWITHMD5ANDDES,PBEWITHMD5ANDTRIPLEDES,PBEWITHSHA1ANDDESEDE,PBEWITHSHA1ANDRC2_128,PBEWITHSHA1ANDRC2_40,PBEWITHSHA1ANDRC4_128,PBEWITHSHA1ANDRC4_40";
        String[] split = algo.split(",");
        for (String entry : split) {
            try {
                System.out.println(entry + ":算法开始=====");
                boolean b = encryptCheck(entry,"123456");
                if (b) {
                    System.out.println(entry + ":支持");
                }
                System.out.println(entry + ":算法结束=====");
            } catch (Exception e) {
//                e.printStackTrace();
                System.err.println(entry + ":不支持"+e.getMessage());
            }
            System.out.println();
        }
    }

    boolean encryptCheck(String algorithm,String password) throws Exception {
        key = symmetric.initKey(algorithm,password);
        salt = AbstractSymmetric.initSalt(8);
        System.out.println("key:" + Base64.getEncoder().encodeToString(key.getEncoded()));
        System.out.println("salt:" + Base64.getEncoder().encodeToString(salt));
        byte[] iv = new SecureRandom().generateSeed(16);
        encrypt = symmetric.encrypt(algorithm, key, salt,iv, msg.getBytes("utf-8"));
        System.out.println("encrypt:" + Base64.getEncoder().encodeToString(encrypt));
        decrypt = symmetric.decrypt(algorithm, key, salt,iv, this.encrypt);
        //System.out.println("decrypt:" + Base64.getEncoder().encodeToString(encrypt));
        //System.out.println("解密原文:" + new String(decrypt, "utf-8"));
        return true;
    }
}