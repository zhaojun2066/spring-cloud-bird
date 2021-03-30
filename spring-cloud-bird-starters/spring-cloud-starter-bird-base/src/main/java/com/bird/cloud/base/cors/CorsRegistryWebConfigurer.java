package com.bird.cloud.base.cors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-09 21:53
 **/
@Configuration
@ConditionalOnProperty(value = "bird.framework.cors.enabled",havingValue = "true")
public class CorsRegistryWebConfigurer {
    @Bean
    public FilterRegistrationBean corsFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("corsFilerCe");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
