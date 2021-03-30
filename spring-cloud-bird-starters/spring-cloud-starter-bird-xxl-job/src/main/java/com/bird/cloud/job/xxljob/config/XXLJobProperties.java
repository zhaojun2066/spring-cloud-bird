package com.bird.cloud.job.xxljob.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-13 22:43
 **/
@Data
@ConfigurationProperties(prefix = "bird.cloud.job.xxl")
public class XXLJobProperties {

    private String adminAddresses;

    private String accessToken;

    private Executor executor;


    @Data
    public static class Executor{
        private String appname;

        private String address;

        private String ip;

        private int port;

        private String logPath;

        private int logRetentionDays;
    }
}
