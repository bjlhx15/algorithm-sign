package com.github.bjlhx15.security.base001base64;

import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class SunJdkBase64 {

    public String base64Encoder(String encodeMsg, String charsetName) throws Exception {
        // JDK里sun.misc套件下的BASE64Encoder和BASE64Decoder这两个类
        return new BASE64Encoder().encode(encodeMsg.getBytes(charsetName));
    }

    public String base64Decoder(String decodeMsg, String charsetName) throws Exception {
        // JDK里sun.misc套件下的BASE64Encoder和BASE64Decoder这两个类
        return new String(new BASE64Decoder().decodeBuffer(decodeMsg), charsetName);
    }
}
