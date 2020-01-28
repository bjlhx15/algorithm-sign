package com.github.bjlhx15.security.changekey002ECDH;

import org.junit.Test;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class BcECDHCoderTest {

    @Test
    public void initKey() throws Exception {


//        Cipher.ARIARFC3211WRAP -> org.bouncycastle.jcajce.provider.symmetric.ARIA $RFC3211Wrap
        String msg = "我是密文";
        List<String> list = new ArrayList<>();
//        list.add("EC");
        list.add("ECDH");
        list.add("ECDHC");
        for (String algo : list) {

            // 甲方构建密钥对
            KeyPair keyPair = BcECDHCoder.initKey(algo,BcECDHCoder.secp256k1);
            System.out.println("甲方 pubkey：" + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
            System.out.println("甲方 prikey：" + Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));

            System.out.println("甲方将 公钥 发送给 乙方");
            System.out.println("乙方 使用甲方pubkey构建密钥对");
            KeyPair keyPair2 = BcECDHCoder.initKeyByPubKey(algo, keyPair.getPublic());

            System.out.println("乙方 pubkey：" + Base64.getEncoder().encodeToString(keyPair2.getPublic().getEncoded()));
            System.out.println("乙方 prikey：" + Base64.getEncoder().encodeToString(keyPair2.getPrivate().getEncoded()));

            System.out.println("乙方 pubkey 发送给 甲方");
            System.out.println("甲方加密数据，使用自己私钥以及 对方公钥");
            byte[] encrypt = BcECDHCoder.encrypt(algo,BcECDHCoder.AES, msg.getBytes(), keyPair2.getPublic(), keyPair.getPrivate());
            System.out.println("甲方将加密数据，发送给乙方");
            System.out.println("乙方 使用自己私钥以及 对方公钥 解密");
            byte[] decrypt = BcECDHCoder.decrypt(algo,BcECDHCoder.AES, encrypt,keyPair.getPublic(), keyPair2.getPrivate());
            System.out.println(new String(decrypt));

//        System.out.println("乙方回发消息");
            byte[] encrypt2 = BcECDHCoder.encrypt(algo,BcECDHCoder.AES, "乙方回发消息".getBytes(), keyPair.getPublic(),keyPair2.getPrivate());

            byte[] decrypt2 = BcECDHCoder.decrypt(algo,BcECDHCoder.AES, encrypt2, keyPair2.getPublic(), keyPair.getPrivate());
            System.out.println(new String(decrypt2));

        }
    }


    @Test
    public void initKey2() throws Exception {

        String msg = "我是密文";
        List<String> list = new ArrayList<>();
        list.add("ECDH");
        for (String algo : list) {

            // 甲方构建密钥对
            KeyPair keyPair = BcECDHCoder.initKey(algo,BcECDHCoder.secp256k1);
            System.out.println("甲方 pubkey：" + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
            System.out.println("甲方 prikey：" + Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));

            System.out.println("甲方将 公钥 发送给 乙方");
            System.out.println("乙方 使用甲方pubkey构建密钥对");
            KeyPair keyPair2 = BcECDHCoder.initKeyByPubKey(algo, keyPair.getPublic());

            System.out.println("乙方 pubkey：" + Base64.getEncoder().encodeToString(keyPair2.getPublic().getEncoded()));
            System.out.println("乙方 prikey：" + Base64.getEncoder().encodeToString(keyPair2.getPrivate().getEncoded()));

            System.out.println("乙方 pubkey 发送给 甲方");
            System.out.println("甲方加密数据，使用自己私钥以及 对方公钥");
            byte[] encrypt = BcECDHCoder.encrypt(algo,BcECDHCoder.DESede, msg.getBytes(), keyPair2.getPublic(), keyPair.getPrivate());
            System.out.println("甲方将加密数据，发送给乙方");
            System.out.println("乙方 使用自己私钥以及 对方公钥 解密");
            byte[] decrypt = BcECDHCoder.decrypt(algo,BcECDHCoder.DESede, encrypt,keyPair.getPublic(), keyPair2.getPrivate());
            System.out.println(new String(decrypt));

//        System.out.println("乙方回发消息");
            byte[] encrypt2 = BcECDHCoder.encrypt(algo,BcECDHCoder.DESede, "乙方回发消息".getBytes(), keyPair.getPublic(),keyPair2.getPrivate());

            byte[] decrypt2 = BcECDHCoder.decrypt(algo,BcECDHCoder.DESede, encrypt2, keyPair2.getPublic(), keyPair.getPrivate());
            System.out.println(new String(decrypt2));

        }
    }
}