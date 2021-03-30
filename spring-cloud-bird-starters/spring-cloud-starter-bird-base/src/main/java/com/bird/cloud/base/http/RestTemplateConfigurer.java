package com.bird.cloud.base.http;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-09 22:02
 **/
@Configuration
public class RestTemplateConfigurer {
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate initRestTemplate(RestTemplateBuilder restTemplateBuilder){
        //HttpComponentsClientHttpRequestFactory
        RestTemplate restTemplate =  restTemplateBuilder
               .build();
        restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(
               new OkHttpClient().newBuilder().connectionPool(
                       new ConnectionPool(20,60, TimeUnit.SECONDS)
               ).writeTimeout(8000,TimeUnit.MILLISECONDS).connectTimeout(8000,TimeUnit.MILLISECONDS)
                       .readTimeout(8000,TimeUnit.MILLISECONDS)
                       .retryOnConnectionFailure(true).build()
        ));
        return  restTemplate;
    }
}
