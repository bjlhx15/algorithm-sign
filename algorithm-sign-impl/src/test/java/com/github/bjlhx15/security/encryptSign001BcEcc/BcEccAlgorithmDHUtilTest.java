package com.github.bjlhx15.security.encryptSign001BcEcc;

import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;

public class BcEccAlgorithmDHUtilTest {

    @Test
    public void initKeyByPubKey() throws Exception {
        String msg = "我是测试数据对的纷纷";
        System.out.println("1、A 初始化密钥对=================");
        Map.Entry<String, String> entryA = new AbstractMap.SimpleEntry<>("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEklGeocxDXXpJRKKHSILpbvMkZs/HbJApy5s7mJdmJPfOhMzqfOM4DVJmdW+aXOS80PR+5NQ7tExkwTSVGpj4Gg=="
                ,"MIGNAgEAMBAGByqGSM49AgEGBSuBBAAKBHYwdAIBAQQg3NhqyL1GPnvjDIj5l9OMDYCXB+25NTSjfgv2dWriXwagBwYFK4EEAAqhRANCAASSUZ6hzENdeklEoodIgulu8yRmz8dskCnLmzuYl2Yk986EzOp84zgNUmZ1b5pc5LzQ9H7k1Du0TGTBNJUamPga");
        //BcEccAlgorithmDHUtil.initKeyPairBase64();
        System.out.println("A pubKey:" + entryA.getKey());
        System.out.println("A priKey:" + entryA.getValue());
        System.out.println();

        System.out.println("2、A 将 公钥 发送给B");

        System.out.println("B 用A的公钥生成密钥对，将公钥发给A");
        Map.Entry<String, String> entryB = BcEccAlgorithmDHUtil.initKeyByPubKey(entryA.getKey());
        System.out.println("B pubKey:" + entryB.getKey());
        System.out.println("B priKey:" + entryB.getValue());
        System.out.println("=================================");

        System.out.println("A 向 B 发送数据【密文、签名】");
        System.out.println("A 需要用B的 公钥加密数据,以及自己的私钥 加密数据");
        String bytes = BcEccAlgorithmDHUtil.encryptBase64Utf8(entryB.getKey(),entryA.getValue(),msg);
        System.out.println("密文:" + bytes);

        System.out.println("A 需要用自己的 私钥签名");
        String sign = BcEccAlgorithmDHUtil.sign(entryA.getValue(), msg);
        System.out.println("sign:" + sign);

        System.out.println("A 向 B 发送数据:ok");
        System.out.println("======================");



        System.out.println("B用 需要用自己 的私钥解密，以及A的公钥解密");
        String de = BcEccAlgorithmDHUtil.decryptBase64Utf8(entryA.getKey(),entryB.getValue(), bytes);
        System.out.println("解密后:" + de);

        System.out.println("B需要用A  的公钥验签");
        boolean check = BcEccAlgorithmDHUtil.verify(entryA.getKey(), de, sign);
        System.out.println("check:" + check);
    }
}