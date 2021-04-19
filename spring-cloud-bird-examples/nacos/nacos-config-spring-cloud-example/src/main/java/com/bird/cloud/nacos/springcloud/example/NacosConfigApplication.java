package com.bird.cloud.nacos.springcloud.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-bird
 * @description:
 * 其中通过@Value注解，去读取key为nacosconfig的配置的值，并通过/getValue接口返回。
 * 加入@RefreshScope注解，可以使当前类下的配置支持动态更新。
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-19 14:59
 **/

@SpringBootApplication
@RestController
@RefreshScope
public class NacosConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }

    @Value("${log.path}")
    private String config;

    @RequestMapping("/getValue")
    public String getValue() {
        return config;
    }
}
