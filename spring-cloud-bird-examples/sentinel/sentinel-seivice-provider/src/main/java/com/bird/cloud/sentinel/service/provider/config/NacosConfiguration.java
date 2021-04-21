package com.bird.cloud.sentinel.service.provider.config;

//import com.alibaba.nacos.api.PropertyKeyConst;
//import com.alibaba.nacos.api.config.ConfigFactory;
//import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-20 17:57
 **/

//@Configuration
//@EnableConfigurationProperties(NacosProperties.class)
//public class NacosConfiguration {
//
//
//    @Autowired
//    private  NacosProperties nacosProperties;
//
//
//    @Bean
//    public ConfigService nacosConfigService() throws Exception {
//        Properties properties = new Properties();
//        properties.put(PropertyKeyConst.SERVER_ADDR, nacosProperties.getServerAddr());
//      //  properties.put(PropertyKeyConst.NAMESPACE, nacosProperties.getNamespace());
//        return ConfigFactory.createConfigService(properties);
//    }
//}
