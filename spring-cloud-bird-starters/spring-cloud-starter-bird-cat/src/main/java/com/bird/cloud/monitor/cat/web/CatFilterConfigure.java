package com.bird.cloud.monitor.cat.web;

import com.dianping.cat.servlet.CatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 14:26
 **/

@Configuration
@ConditionalOnProperty(value = "bird.cloud.monitor.cat.web.enabled",havingValue = "true")
@EnableConfigurationProperties(CatFilterProperties.class)
public class CatFilterConfigure {


    @Autowired
    private CatFilterProperties catFilterProperties;

    @Bean
    public FilterRegistrationBean catFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CatFilter filter = new CatFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns(catFilterProperties.getUrlPatterns());
        registration.setName("cat-filter");
        registration.setOrder(catFilterProperties.getOrder());
        return registration;
    }
}
