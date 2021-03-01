package com.github.bjlhx15.sign.eg001.seal.sign;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author lihongxu6
 * @version 1.0
 * @className SignPdfTest
 * @description TODO
 * @date 2021-01-25 22:10
 */
public class SignPdfTest {

    @Test
    public void sign() throws Exception {
        byte[] fileData = SignPdf.sign("123456", "src/main/resources/cert/keystore.p12",
                "src/main/resources/book.pdf",
                "src/main/resources/sealimg/私章.png", 350, 500);
        FileOutputStream f = new FileOutputStream(new File("src/main/resources/signed.pdf"));
        f.write(fileData);
        f.close();
    }
}