package com.bird.cloud.monitor.cat.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 16:01
 **/

@Configuration
public class CatAopConfig {


    @Bean
    public  CatMethodAop catMethodAop(){
        return new CatMethodAop();
    }
}
