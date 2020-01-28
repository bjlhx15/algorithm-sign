package com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4;

import com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4.AbstractSymmetric;
import com.github.bjlhx15.security.symmetric.des3DesAesBlowfishRC2RC4.JdkSymmetric;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class JdkSymmetricDesTest {
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


    // DES SupportedPaddings=NOPADDING|PKCS5PADDING|ISO10126PADDING,
    // DES SupportedModes=ECB|CBC|PCBC|CTR|CTS|CFB|OFB|
    //      CFB8|CFB16|CFB24|CFB32|CFB40|CFB48|CFB56|CFB64|
    //      OFB8|OFB16|OFB24|OFB32|OFB40|OFB48|OFB56|OFB64
    @Test
    public void encryptAll() throws Exception {
        System.out.println("原文:" + msg);
        List<Map.Entry<String, String>> paddingListMap = new ArrayList<>();
        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES"));//相当于：DES/ECB/PKCS5PADDING
        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/ECB/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/ECB/PKCS5PADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/ECB/ISO10126PADDING"));

        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/CBC/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/CBC/PKCS5PADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/CBC/ISO10126PADDING"));


        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/PCBC/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/PCBC/PKCS5PADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/PCBC/ISO10126PADDING"));

        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/CTR/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/CTR/PKCS5PADDING"));
        //paddingListMap.add(new AbstractMap.SimpleEntry<>("DES","DES/CTR/ISO10126PADDING"));//不支持

        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/CTS/NOPADDING"));
        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES", "DES/CTS/PKCS5PADDING"));
//        paddingListMap.add(new AbstractMap.SimpleEntry<>("DES","DES/CTS/ISO10126PADDING"));//不支持
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
        //Wrong keysize: must be equal to 56,或者不写也可以
        key = symmetric.initKey(padding,56);//Base64.getDecoder().decode("koY9NPFJGf4=");//
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