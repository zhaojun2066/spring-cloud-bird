package com.bird.cloud.mybatis.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-07 15:59
 **/

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MyBatisMapperScannerConfig implements EnvironmentAware {

    private  MybatisProperties properties;

    private Environment environment;

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer cfg = new MapperScannerConfigurer();
        cfg.setBasePackage(environment.getProperty("bird.cloud.mybatis.scanPackages"));
        cfg.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return cfg;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
