package com.github.bjlhx15.security.base004hmac;

import org.junit.Test;

public class HmacUtilsTest {

    @Test
    public void initMacKey() throws Exception {
        String msg = "测试数据";
        String key = HmacUtils.initMacKey(HmacUtils.HmacMD5);
        String hash = HmacUtils.hashMsgCode(HmacUtils.HmacMD5, key, msg.getBytes("utf-8"));
        System.out.println("key:"+msg+";hash:"+hash);
        boolean check = HmacUtils.check(HmacUtils.HmacMD5, key, hash, msg);
        System.out.println("check:"+check);


        key = HmacUtils.initMacKey(HmacUtils.HmacSHA1);
        hash = HmacUtils.hashMsgCode(HmacUtils.HmacSHA1, key, msg.getBytes("utf-8"));
        System.out.println("key:"+msg+";hash:"+hash);
        check = HmacUtils.check(HmacUtils.HmacSHA1, key, hash, msg);
        System.out.println("check:"+check);

        key = HmacUtils.initMacKey(HmacUtils.HmacSHA224);
        hash = HmacUtils.hashMsgCode(HmacUtils.HmacSHA224, key, msg.getBytes("utf-8"));
        System.out.println("key:"+msg+";hash:"+hash);
        check = HmacUtils.check(HmacUtils.HmacSHA224, key, hash, msg);
        System.out.println("check:"+check);

        key = HmacUtils.initMacKey(HmacUtils.HmacSHA256);
        hash = HmacUtils.hashMsgCode(HmacUtils.HmacSHA256, key, msg.getBytes("utf-8"));
        System.out.println("key:"+msg+";hash:"+hash);
        check = HmacUtils.check(HmacUtils.HmacSHA256, key, hash, msg);
        System.out.println("check:"+check);


        key = HmacUtils.initMacKey(HmacUtils.HmacSHA384);
        hash = HmacUtils.hashMsgCode(HmacUtils.HmacSHA384, key, msg.getBytes("utf-8"));
        System.out.println("key:"+msg+";hash:"+hash);
        check = HmacUtils.check(HmacUtils.HmacSHA384, key, hash, msg);
        System.out.println("check:"+check);


        key = HmacUtils.initMacKey(HmacUtils.HmacSHA512);
        hash = HmacUtils.hashMsgCode(HmacUtils.HmacSHA512, key, msg.getBytes("utf-8"));
        System.out.println("key:"+msg+";hash:"+hash);
        check = HmacUtils.check(HmacUtils.HmacSHA512, key, hash, msg);
        System.out.println("check:"+check);
    }
}