package com.github.bjlhx15.sign.eg001.seal.imgcompress;

import net.coobird.thumbnailator.Thumbnails;

import java.io.*;

/**
 * @author lihongxu6
 * @version 1.0
 * @className CompressImg
 * @description TODO
 * @date 2021-01-23 10:03
 */
public class CompressImg {
    public static ByteArrayOutputStream commpressPicForScale(InputStream inputStream, double size) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int available = inputStream.available();
        if (available < size * 1024) {
            inputToOut(inputStream, outputStream);
            return outputStream;
        }

        Thumbnails.of(inputStream)
                .outputQuality(0.5f)
                .scale(0.8f)
                .toOutputStream(outputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return commpressPicForScale(byteArrayInputStream, size);
    }

    public static ByteArrayInputStream commpressPicForScaleInput(InputStream inputStream, double size) throws Exception {
        ByteArrayOutputStream outputStream = commpressPicForScale(inputStream, size);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return byteArrayInputStream;
    }

    public static void inputToOut(InputStream source, OutputStream target) throws IOException {
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) > 0) {
            target.write(buf, 0, length);
        }
    }
}
