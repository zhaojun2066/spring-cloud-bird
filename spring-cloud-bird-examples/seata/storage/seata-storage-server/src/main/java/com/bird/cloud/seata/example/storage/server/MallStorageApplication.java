package com.bird.cloud.seata.example.storage.server;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-17 18:06
 **/
@SpringBootApplication/*( exclude = DataSourceAutoConfiguration.class)*/
@EnableAutoDataSourceProxy
public class MallStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallStorageApplication.class, args);
    }
}
