package com.bird.cloud.mq.rocketmq.config;

import com.bird.cloud.mq.rocketmq.annotation.ExtRocketMQProducer;
import com.bird.cloud.mq.rocketmq.core.RocketMQProducer;
import com.bird.cloud.mq.rocketmq.utils.MQUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 22:33
 **/
@Slf4j
@Configuration
public class ExtRocketMQAutoConfiguration implements ApplicationContextAware, SmartInitializingSingleton {

    private ConfigurableApplicationContext applicationContext;

    private StandardEnvironment environment;

    private RocketMQProperties rocketMQProperties;

    public ExtRocketMQAutoConfiguration( StandardEnvironment environment, RocketMQProperties rocketMQProperties) {
        this.environment = environment;
        this.rocketMQProperties = rocketMQProperties;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(ExtRocketMQProducer.class)
                .entrySet().stream().filter(entry -> !ScopedProxyUtils.isScopedTarget(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        beans.forEach(this::registerRocketMQProducer);
    }

    private void registerRocketMQProducer(String beanName, Object bean) {
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);

        if (!RocketMQProducer.class.isAssignableFrom(bean.getClass())) {
            throw new IllegalStateException(clazz + " is not instance of " + RocketMQProducer.class.getName());
        }

        ExtRocketMQProducer annotation = clazz.getAnnotation(ExtRocketMQProducer.class);
        GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;
        validate(annotation, genericApplicationContext);

        DefaultMQProducer mqProducer = createProducer(annotation);
        // Set instanceName same as the beanName
        mqProducer.setInstanceName(beanName);
        try {
            mqProducer.start();
        } catch (MQClientException e) {
            throw new BeanDefinitionValidationException(String.format("Failed to startup MQProducer for RocketMQProducer {}",
                    beanName), e);
        }
        RocketMQProducer rocketMQProducer = (RocketMQProducer) bean;
        rocketMQProducer.setProducer(mqProducer);
        log.info("Set real producer to :{} {}", beanName, annotation.value());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }


    private DefaultMQProducer createProducer(ExtRocketMQProducer annotation) {

        RocketMQProperties.ProducerProperties producerConfig = rocketMQProperties.getProducer();
        if (producerConfig == null) {
            producerConfig = new RocketMQProperties.ProducerProperties();
        }
        String nameServer = environment.resolvePlaceholders(annotation.nameServer());
        String groupName = environment.resolvePlaceholders(annotation.group());
        groupName = StringUtils.isEmpty(groupName) ? producerConfig.getGroup() : groupName;

        String ak = environment.resolvePlaceholders(annotation.accessKey());
        ak = StringUtils.isEmpty(ak) ? producerConfig.getAccessKey() : ak;
        String sk = environment.resolvePlaceholders(annotation.secretKey());
        sk = StringUtils.isEmpty(sk) ? producerConfig.getSecretKey() : sk;
        boolean isEnableMsgTrace = annotation.enableMsgTrace();
        String customizedTraceTopic = environment.resolvePlaceholders(annotation.customizedTraceTopic());
        customizedTraceTopic = StringUtils.isEmpty(customizedTraceTopic) ? producerConfig.getCustomizedTraceTopic() : customizedTraceTopic;

        DefaultMQProducer producer = MQUtil.createDefaultMQProducer(groupName, ak, sk, isEnableMsgTrace, customizedTraceTopic);

        producer.setNamesrvAddr(nameServer);
        producer.setSendMsgTimeout(annotation.sendMessageTimeout() == -1 ? producerConfig.getSendMessageTimeout() : annotation.sendMessageTimeout());
        producer.setRetryTimesWhenSendFailed(annotation.retryTimesWhenSendFailed() == -1 ? producerConfig.getRetryTimesWhenSendFailed() : annotation.retryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendAsyncFailed(annotation.retryTimesWhenSendAsyncFailed() == -1 ? producerConfig.getRetryTimesWhenSendAsyncFailed() : annotation.retryTimesWhenSendAsyncFailed());
        producer.setRetryAnotherBrokerWhenNotStoreOK(annotation.retryAnotherBrokerWhenNotStoreOK());
        producer.setMaxMessageSize(annotation.maxMessageSize() == -1 ? producerConfig.getMaxMessageSize() : annotation.maxMessageSize());
        producer.setCompressMsgBodyOverHowmuch(annotation.compressMsgBodyOverHowmuch() == -1 ? producerConfig.getCompressMsgBodyOverHowmuch() : annotation.compressMsgBodyOverHowmuch());
        producer.setRetryAnotherBrokerWhenNotStoreOK(annotation.retryNextServer());

        return producer;
    }

    private void validate(ExtRocketMQProducer annotation,
                          GenericApplicationContext genericApplicationContext) {
        if (genericApplicationContext.isBeanNameInUse(annotation.value())) {
            throw new BeanDefinitionValidationException(String.format("Bean {} has been used in Spring Application Context, " +
                            "please check the @ExtRocketMQProducer",
                    annotation.value()));
        }
    }
}
