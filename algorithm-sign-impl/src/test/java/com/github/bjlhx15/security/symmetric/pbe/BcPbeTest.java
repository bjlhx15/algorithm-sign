package com.github.bjlhx15.security.symmetric.pbe;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Test;

import java.security.Key;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BcPbeTest {

    String msg = "测试数据2222";
    Map.Entry<String, String> padding = null;
    Key key = null;
    byte[] salt = null;
    byte[] encrypt = null;
    byte[] decrypt = null;

    AbstractSymmetric symmetric=null;
    @Before
    public void before(){
        symmetric=new BcPbe();
    }

    @Test
    public void encryptAll() throws Exception {
        //javax.crypto.CipherSpi 实现类 即可
        System.out.println("原文:" + msg);
        List<String> list = new ArrayList<>();
        list.add("PBEWithMD5AndDES");
//        list.add("PBEWithMD5AndTripleDES");
//
//        list.add("PBEWithSHA1AndDESede");
//        list.add("PBEWithSHA1AndRC2_40");
//        list.add("PBEWithSHA1AndRC4_40");
//        list.add("PBEWithSHA1AndRC2_128");
//        list.add("PBEWithSHA1AndRC4_128");

        list.add("PBEWithMD2AndDES");
        list.add("PBEWithMD5AndRC2");
        list.add("PBEWithSHA1AndDES");
        list.add("PBEWithSHA1AndRC2");

        list.add("PBEWithSHAAndIDEA-CBC");
        list.add("PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        list.add("PBEWithSHAAnd3KeyTripleDES");
        list.add("PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
        list.add("PBEWithSHAAnd128BitRC2-CBC");
        list.add("PBEWithSHAAnd40BitRC2-CBC");
        list.add("PBEWithSHAAnd128BitRC4");
        list.add("PBEWithSHAAnd40BitRC4");
        list.add("PBEWithSHAAndTwofish-CBC");

        list.add("PBEWITHSHA1AND128BITAES-CBC-BC");
        list.add("PBEWITHSHA1AND192BITAES-CBC-BC");
        list.add("PBEWITHSHA1AND256BITAES-CBC-BC");
        list.add("PBEWITHSHA-1AND128BITAES-CBC-BC");
        list.add("PBEWITHSHA-1AND192BITAES-CBC-BC");
        list.add("PBEWITHSHA-1AND256BITAES-CBC-BC");
        list.add("PBEWITHSHA-256AND128BITAES-CBC-BC");
        list.add("PBEWITHSHA-256AND192BITAES-CBC-BC");
        list.add("PBEWITHSHA-256AND256BITAES-CBC-BC");
        list.add("PBEWITHSHA-256AND128BITAES-BC");
        list.add("PBEWITHSHA-256AND192BITAES-BC");
        list.add("PBEWITHSHA-256AND256BITAES-BC");
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

    boolean encryptCheck(String algorithm,String password) throws Exception {
        key = symmetric.initKey(algorithm,password);
        salt = AbstractSymmetric.initSalt(8);
        System.out.println("key:" + Base64.getEncoder().encodeToString(key.getEncoded()));
        System.out.println("salt:" + Base64.getEncoder().encodeToString(salt));

        byte[] iv = new SecureRandom().generateSeed(8);
        encrypt = symmetric.encrypt(algorithm, key, salt,iv, msg.getBytes("utf-8"));
        System.out.println("encrypt:" + Base64.getEncoder().encodeToString(encrypt));
        decrypt = symmetric.decrypt(algorithm, key, salt,iv, this.encrypt);
        //System.out.println("decrypt:" + Base64.getEncoder().encodeToString(encrypt));
        //System.out.println("解密原文:" + new String(decrypt, "utf-8"));
        return true;
    }
}