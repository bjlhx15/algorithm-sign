package com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4;

import com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4.AbstractSymmetric;
import com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4.JdkSymmetric;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class JdkSymmetricRC4Test {
    String msg = "测试数据2222";
    Map.Entry<String, String> padding = null;
    byte[] key = null;
    byte[] iv = null;
    byte[] encrypt = null;
    byte[] decrypt = null;
    AbstractSymmetric symmetric=null;
    @Before
    public void before(){
        symmetric=new JdkSymmetric();
    }

//    SunJCE: Cipher.ARCFOUR -> com.sun.crypto.provider.ARCFOURCipher
//    aliases: [RC4]
//    attributes: {SupportedPaddings=NOPADDING, SupportedKeyFormats=RAW, SupportedModes=ECB}
    @Test
    public void encryptAll() throws Exception {
        System.out.println("原文:" + msg);
        List<Map.Entry<String, String>> paddingListMap = new ArrayList<>();
//        paddingListMap.add(new AbstractMap.SimpleEntry<>("RC4", "RC4"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("RC4", "RC4/ECB/NOPADDING"));
        for (Map.Entry<String, String> entry : paddingListMap) {
            try {
                boolean b = encryptCheck(entry);
                if (b) {
                    System.out.println(entry.getValue() + ":支持");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(entry.getValue() + ":不支持");
            }
        }
    }

    boolean encryptCheck(Map.Entry<String, String> entry) throws Exception {
        padding = entry;
        key = symmetric.initKey(padding);//Base64.getDecoder().decode("koY9NPFJGf4=");//
        iv = AbstractSymmetric.initIv();
        System.out.println("key:" + Base64.getEncoder().encodeToString(key));
        System.out.println("iv:" + Base64.getEncoder().encodeToString(iv));
        encrypt = symmetric.encrypt(padding, key, iv, msg.getBytes("utf-8"));
        System.out.println("encrypt:" + Base64.getEncoder().encodeToString(encrypt));
        decrypt = symmetric.decrypt(padding, key, iv, this.encrypt);
        //System.out.println("decrypt:" + Base64.getEncoder().encodeToString(encrypt));
        //System.out.println("解密原文:" + new String(decrypt, "utf-8"));
        return true;
    }
}