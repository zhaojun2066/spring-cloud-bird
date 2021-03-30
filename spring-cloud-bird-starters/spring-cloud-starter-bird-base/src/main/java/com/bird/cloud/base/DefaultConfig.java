package com.bird.cloud.base;

import com.bird.cloud.base.exception.*;
import com.bird.cloud.base.spring.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-10 22:16
 **/
@Configuration
@ComponentScan("com.bird.cloud.base.exception")
public class DefaultConfig {
    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public DefaultCeErrorAttributes defaultRosesErrorAttributes() {
        return new DefaultCeErrorAttributes();
    }


}
