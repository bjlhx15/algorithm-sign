package com.github.bjlhx15.security.jasypt.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
//        config.setAlgorithm("PBEWithMD5AndDES");//默认配置
//        config.setKeyObtentionIterations("1000");//默认配置
        config.setPoolSize("4");
//        config.setProviderName("SunJCE");//默认配置
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");//默认配置
//        config.setStringOutputType("base64");//默认配置
        encryptor.setConfig(config);
        return encryptor;
    }
}
