package com.bird.cloud.job.xxljob.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-13 22:52
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(XXLJobProperties.class)
public class XXLJobConfig {


    @Autowired
    private XXLJobProperties properties;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(properties.getAdminAddresses());
        xxlJobSpringExecutor.setAccessToken(properties.getAccessToken());
        XXLJobProperties.Executor executorProperties = properties.getExecutor();
        xxlJobSpringExecutor.setAppname(executorProperties.getAppname());
        xxlJobSpringExecutor.setAddress(executorProperties.getAddress());
        xxlJobSpringExecutor.setIp(executorProperties.getIp());
        xxlJobSpringExecutor.setPort(executorProperties.getPort());
        xxlJobSpringExecutor.setLogPath(executorProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(executorProperties.getLogRetentionDays());

        return xxlJobSpringExecutor;
    }
}
