package com.bird.cloud.nacos.springboot.example;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: spring-cloud-bird
 * @description:
 * NacosPropertySource 和 配置文件内的配置不冲突
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-19 16:11
 **/
@SpringBootApplication
//@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class NacosConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }
}
