package com.github.bjlhx15.sign.eg001.seal.imgcompress;

import org.apache.poi.util.IOUtils;
import org.junit.Test;

import java.io.*;

/**
 * @author lihongxu6
 * @version 1.0
 * @className CompressImgTest
 * @description TODO
 * @date 2021-01-23 10:03
 */
public class CompressImgTest {

    /**
     * 压缩至500kb,输出流
     */
    @Test
    public void handle() throws Exception {
        File file = new File("src/main/resources/WechatIMG2.jpeg");
        InputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = CompressImg.commpressPicForScale(inputStream, 500);
        try (OutputStream outputStream = new FileOutputStream("src/main/resources/WechatIMG2_new.jpeg")) {
            byteArrayOutputStream.writeTo(outputStream);
        }
    }

    /**
     * 压缩至300kb,输入流
     */
    @Test
    public void commpressPicForScaleInput() throws Exception {
        File file = new File("src/main/resources/WechatIMG2.jpeg");
        InputStream inputStream = new FileInputStream(file);
        ByteArrayInputStream inputStream2 = CompressImg.commpressPicForScaleInput(inputStream, 300);

        try (FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/WechatIMG2_new.jpeg")) {
            IOUtils.copy(inputStream2, fileOutputStream);
        }
    }
}