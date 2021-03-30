package com.bird.cloud.monitor.cat.mybatis;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring-cloud-bird
 * @description: 默认开启
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 16:52
 **/

@Configuration
@ConditionalOnClass(Interceptor.class)
@ConditionalOnProperty(value = "bird.cloud.monitor.cat.mybatis.enable",matchIfMissing = false)
public class CatMybatisPluginConfig {

    @Bean
    public CatMybatisPlugin catMybatisPlugin(){
        return new CatMybatisPlugin();
    }
}
