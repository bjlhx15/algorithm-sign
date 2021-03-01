package com.github.bjlhx15.sign.eg001.seal.pkcs12;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

/**
 * @author lihongxu6
 * @version 1.0
 * @className Pkcs12Util
 * @description TODO
 * @date 2021-01-20 13:57
 */
public class Pkcs12Util {
    public static KeyPair getKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA",
                new BouncyCastleProvider());
        generator.initialize(1024);
        // 证书中的密钥 公钥和私钥
        KeyPair keyPair = generator.generateKeyPair();
        return keyPair;
    }

    /**
     * @param password
     *            密码
     * @param issuerStr 颁发机构信息
     *
     * @param subjectStr 使用者信息
     *
     * @param certificateCRL 颁发地址
     *
     * @return
     */
    public static Map<String, byte[]> createCert(String password,
                                                 String issuerStr, String subjectStr, String certificateCRL) {
        Map<String, byte[]> result = new HashMap<String, byte[]>();
        ByteArrayOutputStream out = null;
        try {
            // 生成JKS证书
            // KeyStore keyStore = KeyStore.getInstance("JKS");
            // 标志生成PKCS12证书
            KeyStore keyStore = KeyStore.getInstance("PKCS12",
                    new BouncyCastleProvider());
            keyStore.load(null, null);
            KeyPair keyPair = getKey();
            // issuer与 subject相同的证书就是CA证书
            Certificate cert = generateCertificateV3(issuerStr, subjectStr,
                    keyPair, result, certificateCRL, null);
            // cretkey随便写，标识别名
            keyStore.setKeyEntry("cretkey", keyPair.getPrivate(),
                    password.toCharArray(), new Certificate[] { cert });
            out = new ByteArrayOutputStream();
            cert.verify(keyPair.getPublic());
            keyStore.store(out, password.toCharArray());
            byte[] keyStoreData = out.toByteArray();
            result.put("keyStoreData", keyStoreData);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * @param issuerStr
     * @param subjectStr
     * @param keyPair
     * @param result
     * @param certificateCRL
     * @param extensions
     * @return
     */
    public static Certificate generateCertificateV3(String issuerStr,
                                                    String subjectStr, KeyPair keyPair, Map<String, byte[]> result,
                                                    String certificateCRL, List<Extension> extensions) {
        ByteArrayInputStream bout = null;
        X509Certificate cert = null;
        try {
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            Date notBefore = new Date();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(notBefore);
            // 日期加1年
            rightNow.add(Calendar.YEAR, 1);
            Date notAfter = rightNow.getTime();
            // 证书序列号
            BigInteger serial = BigInteger.probablePrime(256, new Random());
            X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(
                    new X500Name(issuerStr), serial, notBefore, notAfter,
                    new X500Name(subjectStr), publicKey);
            JcaContentSignerBuilder jBuilder = new JcaContentSignerBuilder(
                    "SHA1withRSA");
            SecureRandom secureRandom = new SecureRandom();
            jBuilder.setSecureRandom(secureRandom);
            ContentSigner singer = jBuilder.setProvider(
                    new BouncyCastleProvider()).build(privateKey);
            // 分发点
            ASN1ObjectIdentifier cRLDistributionPoints = new ASN1ObjectIdentifier(
                    "2.5.29.31");
            GeneralName generalName = new GeneralName(
                    GeneralName.uniformResourceIdentifier, certificateCRL);
            GeneralNames seneralNames = new GeneralNames(generalName);
            DistributionPointName distributionPoint = new DistributionPointName(
                    seneralNames);
            DistributionPoint[] points = new DistributionPoint[1];
            points[0] = new DistributionPoint(distributionPoint, null, null);
            CRLDistPoint cRLDistPoint = new CRLDistPoint(points);
            builder.addExtension(cRLDistributionPoints, true, cRLDistPoint);
            // 用途
            ASN1ObjectIdentifier keyUsage = new ASN1ObjectIdentifier(
                    "2.5.29.15");
            // | KeyUsage.nonRepudiation | KeyUsage.keyCertSign
            builder.addExtension(keyUsage, true, new KeyUsage(
                    KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
            // 基本限制 X509Extension.java
            ASN1ObjectIdentifier basicConstraints = new ASN1ObjectIdentifier(
                    "2.5.29.19");
            builder.addExtension(basicConstraints, true, new BasicConstraints(
                    true));
            // privKey:使用自己的私钥进行签名，CA证书
            if (extensions != null)
                for (Extension ext : extensions) {
                    builder.addExtension(
                            new ASN1ObjectIdentifier(ext.getOid()),
                            ext.isCritical(),
                            ASN1Primitive.fromByteArray(ext.getValue()));
                }
            X509CertificateHolder holder = builder.build(singer);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            bout = new ByteArrayInputStream(holder.toASN1Structure()
                    .getEncoded());
            cert = (X509Certificate) cf.generateCertificate(bout);
            byte[] certBuf = holder.getEncoded();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // 证书数据
            result.put("certificateData", certBuf);
            //公钥
            result.put("publicKey", publicKey.getEncoded());
            //私钥
            result.put("privateKey", privateKey.getEncoded());
            //证书有效开始时间
            result.put("notBefore", format.format(notBefore).getBytes("utf-8"));
            //证书有效结束时间
            result.put("notAfter", format.format(notAfter).getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bout != null) {
                try {
                    bout.close();
                } catch (IOException e) {
                }
            }
        }
        return cert;
    }

}
