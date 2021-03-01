package com.github.bjlhx15.sign.eg001.seal.doc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lihongxu6
 * @version 1.0
 * @className WordUntils2Test
 * @description TODO
 * @date 2021-01-23 22:45
 */
public class WordUntils2Test {

    @Test
    public void generateWord() throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        //key为pdf模板的form表单的名字，value为需要填充的值
        data.put("${title}", "证书协议");
        data.put("${name}", "李宏旭");
        data.put("${gender}", "男");
        data.put("${Aname}", "李小旭");
        data.put("${Bname}", "李大旭");
        WordUtil.generateWord(
                "src/main/resources/tpl2.docx", "src/main/resources/book2.docx", data);
    }
}