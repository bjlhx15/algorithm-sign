package com.github.bjlhx15.security.base006others;

import com.github.bjlhx15.security.base005ripemd.RipeMDCoder;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.digest.Whirlpool;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.Security;

public class BcExtHashUtil {
    public static final String RipeMD128 = "RipeMD128";
    public static final String RipeMD160 = "RipeMD160";
    public static final String RipeMD256 = "RipeMD256";
    public static final String RipeMD320 = "RipeMD320";

    public static final String Tiger = "Tiger";
    public static final String Whirlpool = "Whirlpool";
    public static final String Gost3411 = "Gost3411";


    /**
     * RipeMD消息摘要
     *
     * @param data 待处理的消息摘要数据
     * @return byte[] 消息摘要
     */
    public static byte[] encodeExtHash(String algorithm, byte[] data) throws Exception {
        // 加入BouncyCastleProvider的支持
        Security.addProvider(new BouncyCastleProvider());
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance(algorithm);
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * RipeMDHex消息摘要
     *
     * @param data 待处理的消息摘要数据
     * @return String 消息摘要
     **/
    public static String encodeExtHashHex(String algorithm, byte[] data) throws Exception {
        // 执行消息摘要
        byte[] b = RipeMDCoder.encodeRipeMD(algorithm, data);
        // 做十六进制的编码处理
        return new String(Hex.encodeHex(b));
    }
}
