package com.github.bjlhx15.security.base001base64;

import org.apache.commons.codec.binary.Base64;

public class CommonsCodecBase64 {

    public String base64Encoder(String encodeMsg, String charsetName) throws Exception {
        byte[] base64 = Base64.encodeBase64(encodeMsg.getBytes(charsetName));
        return new String(base64);
    }

    public String base64Decoder(String base64String, String charsetName) throws Exception {
        byte[] base64 = Base64.decodeBase64(base64String);
        return new String(base64, charsetName);
    }
}
