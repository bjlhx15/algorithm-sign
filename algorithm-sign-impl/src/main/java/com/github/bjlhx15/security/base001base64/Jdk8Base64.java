package com.github.bjlhx15.security.base001base64;

import java.util.Base64;

public class Jdk8Base64 {

    public String base64Encoder(String encodeMsg, String charsetName) throws Exception {
        //默认 ISO-8859-1
        return Base64.getEncoder().encodeToString(encodeMsg.getBytes(charsetName));
    }

    public String base64Decoder(String decodeMsg, String charsetName) throws Exception {
        return new String(Base64.getDecoder().decode(decodeMsg), charsetName);
    }
}
