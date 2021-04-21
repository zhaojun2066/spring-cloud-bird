package com.alibaba.csp.sentinel.dashboard.rule.nacos.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConstants;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosProperties;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-20 18:00
 **/
@Service("flowRuleNacosPublisher")
public class FlowRuleNacosPublisher  implements DynamicRulePublisher<List<FlowRuleEntity>> {
    @Autowired
    private NacosProperties nacosPropertiesConfiguration;

    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<List<FlowRuleEntity>, String> converter;

    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app cannot be empty");
        if (rules == null) {
            return;
        }
        String dataId = new StringBuilder(app).append(NacosConstants.FLOW_DATA_ID_POSTFIX).toString();
        configService.publishConfig(dataId, nacosPropertiesConfiguration.getGroupId(), converter.convert(rules));
    }
}
