package com.bird.cloud.discover.eureka;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-26 22:07
 **/
@Order(value = Ordered.HIGHEST_PRECEDENCE + 1)
public class EurekaConfigPropertySourceLocator implements PropertySourceLocator {
    @Override
    public PropertySource<?> locate(Environment environment) {

        Map<String, Object> p = new HashMap<>(2);
        // 远程仓库此配置为false，本地进行复写
        p.put("eureka.instance.prefer-ip-address", "true");

        // 上报续约的时间间隔
        if(environment.getProperty("eureka.instance.lease-renewal-interval-in-seconds")==null){
            p.put("eureka.instance.lease-renewal-interval-in-seconds", "3");
        }

        // 最大多少秒不续约 就会被剔除
        if(environment.getProperty("eureka.instance.lease-expiration-duration-in-seconds")==null){
            p.put("eureka.instance.lease-expiration-duration-in-seconds", "10");
        }

        p.put("eureka.instance.hostname", environment.getProperty("spring.cloud.client.ip-address"));

        // 是否注册到eureka
        if (environment.getProperty("eureka.client.register-with-eureka")==null){
            p.put("eureka.client.register-with-eureka", "true");
        }
        // 是否注册到eureka
        if (environment.getProperty("eureka.client.fetch-registry")==null){
            p.put("eureka.client.fetch-registry", "true");
        }

        // client 重新刷取 服务列表的时间间隔
        if (environment.getProperty("eureka.client.registry-fetch-interval-seconds")==null){
            p.put("eureka.client.registry-fetch-interval-seconds", "10");
        }
        return new MapPropertySource("eurekeConfig", p);
    }
}
