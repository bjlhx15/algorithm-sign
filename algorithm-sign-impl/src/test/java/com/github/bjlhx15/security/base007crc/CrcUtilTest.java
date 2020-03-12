package com.github.bjlhx15.security.base007crc;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

import static org.junit.Assert.*;

public class CrcUtilTest {

    @Test
    public void crc4_itu() {
        List<String> ip = getIP();
        for (int i = 0; i < ip.size(); i++) {
            if (i > 50) {
                return;
            }
            String s = ip.get(i);

            int b = CrcUtil.crc32(s.getBytes(), 0, s.length());
            System.out.println(b);

        }

    }

    public static Long ip2int(String ip) {
        Long num = 0L;
        if (ip == null) {
            return num;
        }

        try {
            ip = ip.replaceAll("[^0-9\\.]", ""); //去除字符串前的空字符
            String[] ips = ip.split("\\.");
            if (ips.length == 4) {
                num = Long.parseLong(ips[0], 10) * 256L * 256L * 256L + Long.parseLong(ips[1], 10) * 256L * 256L + Long.parseLong(ips[2], 10) * 256L + Long.parseLong(ips[3], 10);
                num = num >>> 0;
            }
        } catch (NullPointerException ex) {
            System.out.println(ip);
        }

        return num;
    }

    @Test
    public void testIP() {
        System.out.println(ip2int("255.255.255.255"));
        System.out.println(ip2int("255.255.255.254"));
    }


    @Test
    public void crc32() {
//        byte[] b = new byte[100];//用于验证的数据
        Long ip2int = ip2int("255.255.255.255");
        byte[] b = ByteBuffer.allocate(8).putLong(ip2int).array();
//        byte[] b="255.255.255.255".getBytes();
        CRC32 c = new CRC32();
        c.reset();//Resets CRC-32 to initial value.
        c.update(b, 0, b.length);//将数据丢入CRC32解码器
        long value = (long) c.getValue();//获取CRC32 的值  默认返回值类型为long 用于保证返回值是一个正数
        System.out.println(value);//2422070025
        System.out.println("255.255.255.255".hashCode());
        System.out.println(ip2int.hashCode());
    }

    List<String> getIP() {
        List<String> result = new ArrayList<>();
        for (int i = 1; i < 255; i++) {
            for (int j = 0; j < 255; j++) {
                for (int k = 0; k < 255; k++) {
                    for (int l = 0; l < 255; l++) {
                        if(result.size()>100){
                            return result;
                        }
                        result.add(i + "." + j + "." + k + "." + l);
                    }
                }
            }
        }
        return result;
    }
}