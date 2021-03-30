package com.bird.cloud.mq.rocketmq.config;

import com.bird.cloud.mq.rocketmq.core.RocketMQProducer;
import com.bird.cloud.mq.rocketmq.utils.MQUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-10 22:50
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "bird.cloud.mq.rocketmq", value = "name-server", matchIfMissing = true)
@EnableConfigurationProperties(RocketMQProperties.class)
@Import({ListenerContainerConfiguration.class,ExtRocketMQAutoConfiguration.class, RocketMQTransactionConfiguration.class})
@AutoConfigureBefore({RocketMQTransactionConfiguration.class})
public class RocketMQAutoConfiguration implements ApplicationContextAware {

    @Autowired
    private Environment environment;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void checkProperties() {
        String nameServer = environment.getProperty("bird.cloud.mq.rocketmq.name-server", String.class);
        log.debug("rocketmq.nameServer = {}", nameServer);
        if (nameServer == null) {
            log.warn("The necessary spring property 'rocketmq.name-server' is not defined, all rockertmq beans creation are skipped!");
        }
    }

    @Bean("defaultMQProducer")
    @ConditionalOnMissingBean(DefaultMQProducer.class)
    @ConditionalOnProperty(prefix = "bird.cloud.mq.rocketmq", value = {"name-server", "producer.group"})
    public DefaultMQProducer defaultMQProducer(RocketMQProperties rocketMQProperties){
        RocketMQProperties.ProducerProperties producerProperties = rocketMQProperties.getProducer();
        String nameServer = rocketMQProperties.getNameServer();
        String groupName = producerProperties.getGroup();
        Assert.hasText(nameServer, "[ce.cloud.mq.rocketmq.name-server] must not be null");
        Assert.hasText(groupName, "[ce.cloud.mq.rocketmq.producer.group] must not be null");
        String accessChannel = rocketMQProperties.getAccessChannel();

        String ak = rocketMQProperties.getProducer().getAccessKey();
        String sk = rocketMQProperties.getProducer().getSecretKey();
        boolean isEnableMsgTrace = rocketMQProperties.getProducer().isEnableMsgTrace();
        String customizedTraceTopic = rocketMQProperties.getProducer().getCustomizedTraceTopic();

        DefaultMQProducer producer = MQUtil.createDefaultMQProducer(groupName, ak, sk, isEnableMsgTrace, customizedTraceTopic);
        producer.setNamesrvAddr(nameServer);
        if (!StringUtils.isEmpty(accessChannel)) {
            producer.setAccessChannel(AccessChannel.valueOf(accessChannel));
        }

        producer.setSendMsgTimeout(producerProperties.getSendMessageTimeout());
        producer.setRetryTimesWhenSendFailed(producerProperties.getRetryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendAsyncFailed(producerProperties.getRetryTimesWhenSendAsyncFailed());
        producer.setMaxMessageSize(producerProperties.getMaxMessageSize());
        producer.setCompressMsgBodyOverHowmuch(producerProperties.getCompressMsgBodyOverHowmuch());
        producer.setRetryAnotherBrokerWhenNotStoreOK(producerProperties.isRetryAnotherBrokerWhenNotStoreOK());

        return producer;
    }


    @Bean(destroyMethod = "destroy",value="defaultRocketMQProducer")
    @ConditionalOnMissingBean(name = "defaultRocketMQProducer")
    public RocketMQProducer defaultRocketMQProducer(DefaultMQProducer producer){
        RocketMQProducer rocketMQProducer = new RocketMQProducer();
        rocketMQProducer.setProducer(producer);
        return rocketMQProducer;
    }
}
