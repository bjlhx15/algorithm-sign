package com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4;

import com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4.AbstractSymmetric;
import com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4.JdkSymmetric;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class JdkSymmetricAesTest {
    String msg = "测试数据2222";
    Map.Entry<String, String> padding = null;
    byte[] key = null;
    byte[] iv = null;
    byte[] encrypt = null;
    byte[] decrypt = null;
    AbstractSymmetric symmetric = null;

    @Before
    public void before() {
        symmetric = new JdkSymmetric();
    }

//    SunJCE: Cipher.AES -> com.sun.crypto.provider.AESCipher$General
//    aliases: [Rijndael]
//    attributes: {SupportedPaddings=NOPADDING|PKCS5PADDING|ISO10126PADDING,
//    SupportedKeyFormats=RAW, SupportedModes=ECB|CBC|PCBC|CTR|CTS|CFB|OFB|CFB8|CFB16|CFB24|CFB32|CFB40|CFB48|CFB56|CFB64
//    |OFB8|OFB16|OFB24|OFB32|OFB40|OFB48|OFB56|OFB64|GCM|CFB72|CFB80|CFB88|CFB96|CFB104|CFB112|CFB120|CFB128|OFB72|OFB80
//    |OFB88|OFB96|OFB104|OFB112|OFB120|OFB128}

    @Test
    public void encryptAll() throws Exception {
        System.out.println("原文:" + msg);
        List<Map.Entry<String, String>> paddingListMap = new ArrayList<>();
        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/ECB/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/ECB/PKCS5PADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/ECB/ISO10126PADDING"));

        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/CBC/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/CBC/PKCS5PADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/CBC/ISO10126PADDING"));


        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/PCBC/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/PCBC/PKCS5PADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/PCBC/ISO10126PADDING"));

        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/CTR/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/CTR/PKCS5PADDING"));
        // paddingListMap.add(new AbstractMap.SimpleEntry<>("AES","AES/CTR/ISO10126PADDING"));//不支持

        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/CTS/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("AES", "AES/CTS/PKCS5PADDING"));
        // paddingListMap.add(new AbstractMap.SimpleEntry<>("AES","AES/CTS/ISO10126PADDING"));//不支持
        for (Map.Entry<String, String> entry : paddingListMap) {
            try {
                boolean b = encryptCheck(entry);
                if (b) {
                    System.out.println(entry.getValue() + ":支持");
                    System.out.println("---------------------------------");

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(entry.getValue() + ":不支持");
            }
        }
    }

    boolean encryptCheck(Map.Entry<String, String> entry) throws Exception {
        padding = entry;
        //Wrong keysize: must be equal to 56,或者不写也可以
        if (key == null)
            key = symmetric.initKey(padding, 128);//Base64.getDecoder().decode("koY9NPFJGf4=");//

        if (iv == null)
            iv = AbstractSymmetric.initIv(16);

        System.out.println("key:" + Base64.getEncoder().encodeToString(key));
        System.out.println("iv:" + Base64.getEncoder().encodeToString(iv));
        encrypt = symmetric.encrypt(padding, key, iv, msg.getBytes("utf-8"));
        System.out.println("encrypt:" + Base64.getEncoder().encodeToString(encrypt));
        decrypt = symmetric.decrypt(padding, key, iv, this.encrypt);
        System.out.println("decrypt:" + Base64.getEncoder().encodeToString(encrypt));
        System.out.println("解密原文:" + new String(decrypt, "utf-8"));
        return true;
    }
}