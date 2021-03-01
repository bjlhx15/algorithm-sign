package com.github.bjlhx15.sign.eg001.seal.doc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author lihongxu6
 * @version 1.0
 * @className WordToPdfTest
 * @description TODO
 * @date 2021-01-25 16:17
 */
public class WordToPdfTest {

    @Test
    public void htmlToPdf() {
        String docxHtml = WordToPdf.docx2Html("src/main/resources/book2.docx", null);
        docxHtml = WordToPdf.formatHtml(docxHtml);
        WordToPdf.htmlToPdf(docxHtml, "src/main/resources/book2.pdf");
    }
}