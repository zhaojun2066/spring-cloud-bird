package com.bird.cloud.discover.eureka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-26 22:11
 **/
@Configuration
public class EurekaConfigBootstrapConfiguration {
    @Bean("eurekeConfigPropertySourceLocator")
    public EurekaConfigPropertySourceLocator propertySourceLocator() {
        return new EurekaConfigPropertySourceLocator();
    }
}
