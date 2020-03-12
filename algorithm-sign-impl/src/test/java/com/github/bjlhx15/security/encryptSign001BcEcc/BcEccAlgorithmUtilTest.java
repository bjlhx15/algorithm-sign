package com.github.bjlhx15.security.encryptSign001BcEcc;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

import java.util.AbstractMap;
import java.util.Map;

public class BcEccAlgorithmUtilTest {

    @org.junit.Test
    public void initKeyPairBase64() throws Exception {
        String msg = "我是测试数据对的 http://blog.bjlhx.top/";
        Map.Entry<String, String> entryA = BcEccAlgorithmUtil.initKeyPairBase64();
        System.out.println("A pubKey:" + entryA.getKey());
        System.out.println("A priKey:" + entryA.getValue());
        System.out.println("A pubKey[pkcs1]:" + formatPubKeyPkcs8ToPkcs1(entryA.getKey()));
        System.out.println("A priKey[pkcs1]:" + formatpriKeyPkcs8ToPkcs1(entryA.getValue()));
        System.out.println();


        Map.Entry<String, String> entryB = BcEccAlgorithmUtil.initKeyPairBase64();
        System.out.println("B pubKey:" + entryB.getKey());
        System.out.println("B priKey:" + entryB.getValue());
        System.out.println();

        System.out.println("A 向 B 发送数据【密文、签名】");
        System.out.println("A 需要用B的 公钥加密数据");
        String bytes = BcEccAlgorithmUtil.encryptBase64Utf8(entryB.getKey(), msg);
        System.out.println("密文:" + bytes);

        System.out.println("A 需要用自己的 私钥签名");
        String sign = BcEccAlgorithmUtil.sign(entryA.getValue(), msg);
//        String sign2 = BcEccAlgorithmUtil.sign(formatPkcs8ToPkcs1(entryA.getValue()), msg);
        System.out.println("sign:" + sign);
//        System.out.println("sign:" + sign2);

        System.out.println("A 向 B 发送数据:ok");
        System.out.println();



        System.out.println("B用 需要用自己 的私钥解密");
        String de = BcEccAlgorithmUtil.decryptBase64Utf8(entryB.getValue(), bytes);
        System.out.println("解密后:" + de);

        System.out.println("B需要用A  的公钥验签");
        boolean check = BcEccAlgorithmUtil.verify(entryA.getKey(), de, sign);
        System.out.println("check:" + check);


    }

    @org.junit.Test
    public void pkcs8checkSign() throws Exception {
        String msg = "我是测试数据对的 http://blog.bjlhx.top/";

        System.out.println("B需要用A  的公钥验签");
        boolean check = BcEccAlgorithmUtil.verify("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEYfNJOtj1Xkfp9bVqoXlB4ixVhNtN7Zl+mPPiyeDrPbKNX7XhmN8EcyOhjfpbXYmJY8JItue9rajOqouS45wYpQ==",
                msg,
                "MEUCIEuuqtMhHw/JvZgyBrs5djPD0VIZjxdeHYUWeEJsqcdlAiEAyVowkbpvQJuZWrUG2FXhq6+BFDpq9wFSl2CcjcSjGRM=");
        System.out.println("check:" + check);
    }

    @org.junit.Test
    public void pkcs1checkSign() throws Exception {
        String msg = "我是测试数据对的 http://blog.bjlhx.top/";

        System.out.println("B需要用A  的公钥验签");
        boolean check = BcEccAlgorithmUtil.verify("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEYfNJOtj1Xkfp9bVqoXlB4ixVhNtN7Zl+mPPiyeDrPbKNX7XhmN8EcyOhjfpbXYmJY8JItue9rajOqouS45wYpQ==",
                msg,
                "MEUCIQCGIKcNXqPw/zvyMIJ+LXrEPnSjiwzrDkKldZve2a1vFwIgA/si+DtemscVQ9KuvvAocsKfQUYcWu64qeGvLfmS4nU=");
        System.out.println("check:" + check);
    }

    @org.junit.Test
    public void process() throws Exception {
        String msg = "我是测试数据对的 http://blog.bjlhx.top/";
        Map.Entry<String, String> entryA = new AbstractMap.SimpleEntry<>("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEYfNJOtj1Xkfp9bVqoXlB4ixVhNtN7Zl+mPPiyeDrPbKNX7XhmN8EcyOhjfpbXYmJY8JItue9rajOqouS45wYpQ==",
                "MIGNAgEAMBAGByqGSM49AgEGBSuBBAAKBHYwdAIBAQQg1xRtgNwZ3oo+509hN+EkoH+hGRDhHiq0zfZy0zQxAOegBwYFK4EEAAqhRANCAARh80k62PVeR+n1tWqheUHiLFWE203tmX6Y8+LJ4Os9so1fteGY3wRzI6GN+ltdiYljwki2572tqM6qi5LjnBil");
        System.out.println("A pubKey:" + entryA.getKey());
        System.out.println("A priKey:" + entryA.getValue());
//        System.out.println("A pubKey[pkcs1]:" + formatPubKeyPkcs8ToPkcs1(entryA.getKey()));
        System.out.println("A priKey[pkcs1]:" + formatpriKeyPkcs8ToPkcs1(entryA.getValue()));
        System.out.println();


        Map.Entry<String, String> entryB = new AbstractMap.SimpleEntry<>("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEJN5FVWR90XaFSMjVEbCGgAqrMbvHCIM0i84kVLuKpESDNgGSnz0AZt4HKElRR8MkZbzsnJdMq5gmDxTrYMyg8Q==",
                "MIGNAgEAMBAGByqGSM49AgEGBSuBBAAKBHYwdAIBAQQgUHzI83yRMCfl395xdpx/CB2eZPIsEORBN3OPQyN0RT6gBwYFK4EEAAqhRANCAAQk3kVVZH3RdoVIyNURsIaACqsxu8cIgzSLziRUu4qkRIM2AZKfPQBm3gcoSVFHwyRlvOycl0yrmCYPFOtgzKDx");
        System.out.println("B pubKey:" + entryB.getKey());
        System.out.println("B priKey:" + entryB.getValue());
        System.out.println();

        System.out.println("A 向 B 发送数据【密文、签名】");
        System.out.println("A 需要用B的 公钥加密数据");
        String bytes = BcEccAlgorithmUtil.encryptBase64Utf8(entryB.getKey(), msg);
        System.out.println("密文:" + bytes);

        System.out.println("A 需要用自己的 私钥签名");
        String sign = BcEccAlgorithmUtil.sign(entryA.getValue(), msg);
//        String sign2 = BcEccAlgorithmUtil.sign(formatPkcs8ToPkcs1(entryA.getValue()), msg);
        System.out.println("sign:" + sign);
//        System.out.println("sign:" + sign2);

        System.out.println("A 向 B 发送数据:ok");
        System.out.println();



        System.out.println("B用 需要用自己 的私钥解密");
        String de = BcEccAlgorithmUtil.decryptBase64Utf8(entryB.getValue(), bytes);
        System.out.println("解密后:" + de);

        System.out.println("B需要用A  的公钥验签");
        boolean check = BcEccAlgorithmUtil.verify(entryA.getKey(), de, sign);
        System.out.println("check:" + check);



        System.out.println("=============================");
        System.out.println("B 向 A 发送数据【密文、签名】");
        System.out.println("B 需要用A 的 公钥加密数据");
        String bytes2 = BcEccAlgorithmUtil.encryptBase64Utf8(entryA.getKey(), msg+":B");
        System.out.println("密文:" + bytes2);

        System.out.println("B 需要用自己的 私钥签名");
        String sign2 = BcEccAlgorithmUtil.sign(entryB.getValue(), msg+":B");
//        String sign2 = BcEccAlgorithmUtil.sign(formatPkcs8ToPkcs1(entryA.getValue()), msg);
        System.out.println("sign:" + sign2);
//        System.out.println("sign:" + sign2);

        System.out.println("B 向 A 发送数据:ok");
        System.out.println();


        System.out.println("A用 需要用自己 的私钥解密");
        String de2 = BcEccAlgorithmUtil.decryptBase64Utf8(entryA.getValue(), bytes2);
        System.out.println("解密后:" + de2);

        System.out.println("A需要用B  的公钥验签");
        boolean check2 = BcEccAlgorithmUtil.verify(entryB.getKey(), de2, sign2);
        System.out.println("check:" + check2);

    }

    //format PKCS#8 to PKCS#1
    public static String formatpriKeyPkcs8ToPkcs1(String rawKey) throws Exception {


//        PrivateKey priv = pair.getPrivate();
        byte [] privBytes =java.util.Base64.getDecoder().decode(rawKey);

        PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(privBytes);
        ASN1Encodable encodable = pkInfo.parsePrivateKey();
        ASN1Primitive primitive = encodable.toASN1Primitive();
        byte [] privateKeyPKCS1 = primitive.getEncoded();
        return Base64.encodeBase64String(privateKeyPKCS1);
    }

    public static String formatPubKeyPkcs8ToPkcs1(String rawKey) throws Exception {
        byte [] privBytes =java.util.Base64.getDecoder().decode(rawKey);

        SubjectPublicKeyInfo pkInfo=SubjectPublicKeyInfo.getInstance(privBytes);
        ASN1Primitive encodable = pkInfo.parsePublicKey();
//        ASN1Primitive primitive = encodable.toASN1Primitive();
        byte [] pkPKCS1 = encodable.getEncoded();
        return Base64.encodeBase64String(pkPKCS1);
    }

}