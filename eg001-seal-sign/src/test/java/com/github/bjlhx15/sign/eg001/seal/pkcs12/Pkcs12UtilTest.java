package com.github.bjlhx15.sign.eg001.seal.pkcs12;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * @author lihongxu6
 * @version 1.0
 * @className Pkcs12UtilTest
 * @description TODO
 * @date 2021-01-20 14:02
 */
public class Pkcs12UtilTest {

    @Test
    public void createCert() throws Exception {
        // CN: 名字与姓氏    OU : 组织单位名称
        // O ：组织名称  L : 城市或区域名称  E : 电子邮件
        // ST: 州或省份名称  C: 单位的两字母国家代码
        String issuerStr = "CN=李宏旭测试公司,OU=github研发部,O=github有限公司,C=CN,E=bjlhx15@163com,L=北京,ST=北京";
        String subjectStr = "CN=LihongxuTestCompany,OU=github研发部,O=github有限公司,C=CN,E=bjlhx15@163com,L=北京,ST=北京";
        String certificateCRL  = "https://blog.itag.top";
        Map<String, byte[]> result = Pkcs12Util.createCert("123456", issuerStr, subjectStr, certificateCRL);

        FileOutputStream outPutStream = new FileOutputStream("src/main/resources/cert/keystore.p12"); // ca.jks
        outPutStream.write(result.get("keyStoreData"));
        outPutStream.close();
        FileOutputStream fos = new FileOutputStream(new File("src/main/resources/cert/keystore.cer"));
        fos.write(result.get("certificateData"));
        fos.flush();
        fos.close();
    }
}