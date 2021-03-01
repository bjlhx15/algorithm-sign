package com.github.bjlhx15.sign.eg001.seal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@EnableAsync
@ImportResource({
        "classpath:spring-config.xml"
})
//@PropertySource(value = {
//        "${config.path}"
//}, encoding = "utf-8")
//@Import({MyImportBeanDefinitionRegistrar.class})
//@Import({MyImportSelector.class})
//@Import({TestJoinBean.class})
public class DemoApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }


    //main启动
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(DemoApplication.class, args);
        System.err.println("\r\n---项目 启动成功---");
    }

}
