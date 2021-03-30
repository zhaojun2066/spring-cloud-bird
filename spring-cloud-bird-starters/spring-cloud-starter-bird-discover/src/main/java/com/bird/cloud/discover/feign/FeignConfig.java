package com.bird.cloud.discover.feign;

import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-10 23:15
 **/
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
@EnableFeignClients(basePackages = "com.bird")
public class FeignConfig {

   @Bean
    public FeignHeaderProcessInterceptor requestInterceptors(){
        return new FeignHeaderProcessInterceptor();
    }

    @Bean
    public FeignErrorDecoder feignErrorDecoder(){
        return new FeignErrorDecoder();
    }

    @Bean
    @ConditionalOnMissingClass("com.bird.cloud.seata.feign.hystrix.SeataHystrixConcurrencyStrategy")
    public FeignHystrixConcurrencyStrategy feignHystrixConcurrencyStrategy(){
       return  new FeignHystrixConcurrencyStrategy();
    }

}
