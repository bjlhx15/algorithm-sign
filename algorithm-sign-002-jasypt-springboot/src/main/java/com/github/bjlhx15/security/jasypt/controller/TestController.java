package com.github.bjlhx15.security.jasypt.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@RestController
public class TestController {

    @GetMapping("test")
    public Object testProperties(@Value("${jdbc.password}") String pwd, @Value("${spring.datasource.password}") String jbbc) {
        System.out.println(pwd);
        System.out.println(jbbc);
        Properties properties = System.getProperties();
        Set<Object> objects = properties.keySet();
        for (Object object : objects) {
            System.out.println("key:" + object + "---:" + properties.get(object));
        }

        Map<String, String> getenv = System.getenv();
        for (Map.Entry<String, String> entry : getenv.entrySet()) {
            System.out.println(entry.getKey() + "---:" + entry.getValue());
        }
        return "";
    }
}
