package com.github.bjlhx15.sign.eg001.seal.pdf;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author lihongxu6
 * @version 1.0
 * @className PdfUtilTest
 * @description TODO
 * @date 2021-01-20 16:54
 */
public class PdfUtilTest {

    @Test
    public void generatePDF() {
        Map<String, String> data = new HashMap<String, String>();
        //key为pdf模板的form表单的名字，value为需要填充的值
        data.put("title", "证书协议");
        data.put("name", "李宏旭");
        data.put("gender", "男");
        data.put("Aname", "李小旭");
        data.put("Bname", "李大旭");
        PdfUtil.generatePDF("src/main/resources/tpl.pdf","src/main/resources/book.pdf", data);

    }
}