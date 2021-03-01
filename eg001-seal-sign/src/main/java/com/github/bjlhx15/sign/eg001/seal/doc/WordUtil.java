package com.github.bjlhx15.sign.eg001.seal.doc;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author lihongxu6
 * @version 1.0
 * @className WordUntils2
 * @description TODO
 * @date 2021-01-23 22:29
 */
public class WordUtil {
    private static final String varkey_prefix="$";
    private static final String varkey_suffix="}";
    public static boolean generateWord(String template, String descpath, Map<String, String> data) throws Exception {
        // 模版位置
        File file = new File(template);
        InputStream inputStream = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(inputStream);
        // 获取整个文本对象
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        processParagraphs(paragraphs, data);
        FileOutputStream fileOutputStream = new FileOutputStream(descpath);
        document.write(fileOutputStream);
        document.close();
        return true;
    }


    private static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, String> data) throws Exception {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                // 遍历获取段落中所有的runs
                List<XWPFRun> runs = paragraph.getRuns();
                System.out.println(runs.toString());
                // 合并逻辑
                for (int i = 0; i < runs.size(); i++) {
                    String text0 = runs.get(i).getText(runs.get(i).getTextPosition());
                    if (text0 != null && text0.contains(varkey_prefix)) {
                        int startIndex = text0.lastIndexOf(varkey_prefix);
                        int endIndex = 1;
                        if (startIndex != -1) {
                            endIndex = text0.substring(startIndex).indexOf(varkey_suffix);
                        }
                        if (endIndex < 0) {
                            // 记录分隔符中间跨越的runs数量，用于字符串拼接和替换
                            int num = 0;
                            int j = i + 1;
                            for (; j < runs.size(); j++) {
                                String text1 = runs.get(j).getText(runs.get(j).getTextPosition());
                                if (text1 != null && text1.contains(varkey_suffix)) {
                                    num = j - i;
                                    break;
                                }
                            }
                            if (num != 0) {
                                // num!=0说明找到了@@配对，需要替换
                                StringBuilder newText = new StringBuilder();
                                for (int s = i; s <= i + num; s++) {
                                    String text2 = runs.get(s).getText(runs.get(s).getTextPosition());
                                    String replaceText = text2;
                                    if (s == i && text2.contains(varkey_prefix) && text2.contains(varkey_suffix)) {
                                        newText.append(text2);
                                    } else if (s == i && text2.contains(varkey_prefix)) {
                                        replaceText = text2.substring(0, text2.indexOf(varkey_prefix));
                                        newText.append(text2.substring(text2.indexOf(varkey_prefix)));
                                    } else if (text2.contains(varkey_suffix)) {
                                        replaceText = text2.substring(replaceText.indexOf(varkey_suffix) + 1);
                                        newText.append(text2.substring(0, text2.indexOf(varkey_suffix) + 1));
                                    } else {
                                        replaceText = "";
                                        newText.append(text2);
                                    }
                                    runs.get(s).setText(replaceText, 0);
                                }
                                runs.get(i).setText(newText.toString(), 0);
                                i = i - 1;
                            }
                        }
                    }
                }
                // 从第一个@符号到第二个@做拼接
                for (int j = 0; j < runs.size(); j++) {
                    String text = runs.get(j).getText(runs.get(j).getTextPosition());
                    boolean isSetText = false;
                    if (text != null) {
                        for (Map.Entry<String, String> entry : data.entrySet()) {

                            if (text.indexOf(entry.getKey()) >= 0) {
                                // 文本替换
                                text = text.replace(entry.getKey(), entry.getValue());
                                isSetText = true;
                            }
                        }

                        if (isSetText) {
                            runs.get(j).setText(text, 0);
                        }
                    }

                }

            }
        }
    }


    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isClose) {
                try {
                    in.close();
                } catch (Exception e2) {
                    System.out.println("关闭流失败");
                }
            }
        }
        return byteArray;
    }
}
