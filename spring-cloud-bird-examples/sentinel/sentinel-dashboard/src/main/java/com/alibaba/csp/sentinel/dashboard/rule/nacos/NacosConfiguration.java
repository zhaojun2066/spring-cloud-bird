package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-20 17:57
 **/

@Configuration
@EnableConfigurationProperties(NacosProperties.class)
public class NacosConfiguration {


    @Autowired
    private  NacosProperties nacosProperties;

    @Bean("authorityRuleEntityEncoder")
    public Converter<List<AuthorityRuleEntity>, String> authorityRuleEntityEncoder() {
        return JSON::toJSONString;
    }
    @Bean("degradeRuleEntityEncoder")
    public Converter<List<DegradeRuleEntity>, String> degradeRuleEntityEncoder() {
        return JSON::toJSONString;
    }
    @Bean("flowRuleEntityEncoder")
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }
    @Bean("gatewayApiRuleEntityEncoder")
    public Converter<List<ApiDefinitionEntity>, String> gatewayApiRuleEntityEncoder() {
        return JSON::toJSONString;
    }
    @Bean("GatewayFlowRuleEntityEncoder")
    public Converter<List<GatewayFlowRuleEntity>, String> GatewayFlowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean("paramFlowRuleEntityEncoder")
    public Converter<List<ParamFlowRuleEntity>, String> paramFlowRuleEntityEncoder() {
        return JSON::toJSONString;
    }
    @Bean("systemRuleEntityEncoder")
    public Converter<List<SystemRuleEntity>, String> systemRuleEntityEncoder() {
        return JSON::toJSONString;
    }


    @Bean("authorityRuleEntityDecoder")
    public Converter<String, List<AuthorityRuleEntity>> AuthorityRuleEntityRuleEntityDecoder() {
        return s -> JSON.parseArray(s, AuthorityRuleEntity.class);
    }
    @Bean("degradeRuleEntityDecoder")
    public Converter<String, List<DegradeRuleEntity>> DegradeRuleEntityDecoder() {
        return s -> JSON.parseArray(s, DegradeRuleEntity.class);
    }
    @Bean("flowRuleEntityDecoder")
    public Converter<String, List<FlowRuleEntity>> FlowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }
    @Bean("apiDefinitionEntityDecoder")
    public Converter<String, List<ApiDefinitionEntity>> ApiDefinitionEntityDecoder() {
        return s -> JSON.parseArray(s, ApiDefinitionEntity.class);
    }
    @Bean("gatewayFlowRuleEntityDecoder")
    public Converter<String, List<GatewayFlowRuleEntity>> GatewayFlowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, GatewayFlowRuleEntity.class);
    }
    @Bean("paramFlowRuleEntityDecoder")
    public Converter<String, List<ParamFlowRuleEntity>> ParamFlowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, ParamFlowRuleEntity.class);
    }
    @Bean("systemRuleEntityDecoder")
    public Converter<String, List<SystemRuleEntity>> SystemRuleEntityDecoder() {
        return s -> JSON.parseArray(s, SystemRuleEntity.class);
    }


    @Bean
    public ConfigService nacosConfigService() throws Exception {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, nacosProperties.getServerAddr());
      //  properties.put(PropertyKeyConst.NAMESPACE, nacosProperties.getNamespace());
        return ConfigFactory.createConfigService(properties);
    }
}
