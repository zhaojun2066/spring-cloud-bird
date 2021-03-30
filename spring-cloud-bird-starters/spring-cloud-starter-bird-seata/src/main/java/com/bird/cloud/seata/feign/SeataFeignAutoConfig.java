package com.bird.cloud.seata.feign;

import feign.Client;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring-cloud-bird
 * @descrihption:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-17 16:36
 **/
@Configuration
@ConditionalOnClass(Client.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class SeataFeignAutoConfig {

    @Bean
    public SeataRequestHeaderInterceptor seataRequestHeaderInterceptor(){
        return new SeataRequestHeaderInterceptor();
    }
}
