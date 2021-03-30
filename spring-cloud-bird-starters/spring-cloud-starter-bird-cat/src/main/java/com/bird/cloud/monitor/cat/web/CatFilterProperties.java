package com.bird.cloud.monitor.cat.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 14:27
 **/


@Data
@ConfigurationProperties(prefix = "bird.cloud.monitor.cat.web")
public class CatFilterProperties {
    private String [] urlPatterns = {"/*"};
    private int order = 1;
}
