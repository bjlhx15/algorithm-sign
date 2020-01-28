package com.github.bjlhx15.security.jasypt;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableEncryptableProperties
@PropertySource(value = {"classpath:security.properties"},ignoreResourceNotFound = false)
public class SpringRunnerMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringRunnerMain.class, args);
    }
}
