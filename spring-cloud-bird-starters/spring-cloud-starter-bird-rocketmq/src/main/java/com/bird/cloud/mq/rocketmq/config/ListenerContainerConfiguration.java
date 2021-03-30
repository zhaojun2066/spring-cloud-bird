package com.bird.cloud.mq.rocketmq.config;

import com.bird.cloud.mq.rocketmq.annotation.RocketMQMessageListener;
import com.bird.cloud.mq.rocketmq.core.DefaultRocketMQListenerContainer;
import com.bird.cloud.mq.rocketmq.core.RocketMQListener;
import com.bird.cloud.mq.rocketmq.enums.ConsumeMode;
import com.bird.cloud.mq.rocketmq.enums.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.AccessChannel;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-12 21:30
 *
 *  实现SmartInitializingSingleton的接口后，当所有单例 bean 都初始化完成以后，
 *  Spring的IOC容器会回调该接口的 afterSingletonsInstantiated()方法。
 **/
@Slf4j
@Configuration
public class ListenerContainerConfiguration implements ApplicationContextAware, SmartInitializingSingleton {

    private AtomicLong counter = new AtomicLong(0);

    private ConfigurableApplicationContext applicationContext;

    private RocketMQProperties rocketMQProperties;

    private StandardEnvironment environment;

    public ListenerContainerConfiguration(StandardEnvironment environment, RocketMQProperties rocketMQProperties) {
        this.environment = environment;
        this.rocketMQProperties = rocketMQProperties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(RocketMQMessageListener.class)
                .entrySet().stream().filter(entry -> !ScopedProxyUtils.isScopedTarget(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        beans.forEach(this::registerContainer);
    }

    private void registerContainer(String beanName, Object bean){
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);

        if (!RocketMQListener.class.isAssignableFrom(bean.getClass()) ) {
            throw new IllegalStateException(clazz + " is not instance of " + RocketMQListener.class.getName() );
        }

        RocketMQMessageListener annotation = clazz.getAnnotation(RocketMQMessageListener.class);
        validate(annotation);
        String containerBeanName = String.format("%s_%s", DefaultRocketMQListenerContainer.class.getName(),
                counter.incrementAndGet());
        GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;

        genericApplicationContext.registerBean(containerBeanName, DefaultRocketMQListenerContainer.class,
                () -> createRocketMQListenerContainer(containerBeanName, bean, annotation));
        DefaultRocketMQListenerContainer container = genericApplicationContext.getBean(containerBeanName,
                DefaultRocketMQListenerContainer.class);
        if (!container.isRunning()) {
            try {
                container.start();
            } catch (Exception e) {
                log.error("Started container failed. {}", container, e);
                throw new RuntimeException(e);
            }
        }

        log.info("Register the listener to container, listenerBeanName:{}, containerBeanName:{}", beanName, containerBeanName);

    }


    private DefaultRocketMQListenerContainer createRocketMQListenerContainer(String name, Object bean,
                                                                             RocketMQMessageListener annotation) {
        DefaultRocketMQListenerContainer container = new DefaultRocketMQListenerContainer();

        container.setRocketMQMessageListenerAnnotation(annotation);

        String nameServer = environment.resolvePlaceholders(annotation.nameServer());
        nameServer = StringUtils.isEmpty(nameServer) ? rocketMQProperties.getNameServer() : nameServer;
        String accessChannel = environment.resolvePlaceholders(annotation.accessChannel());
        container.setNameServer(nameServer);
        if (!StringUtils.isEmpty(accessChannel)) {
            container.setAccessChannel(AccessChannel.valueOf(accessChannel));
        }
        container.setTopic(environment.resolvePlaceholders(annotation.topic()));
        String tags = environment.resolvePlaceholders(annotation.tags());
        if (!StringUtils.isEmpty(tags)) {
            container.setTags(tags);
        }
        container.setConsumerGroup(environment.resolvePlaceholders(annotation.consumerGroup()));
        container.setRocketMQListener((RocketMQListener) bean);
        container.setName(name);

        return container;
    }


    private void validate(RocketMQMessageListener annotation) {
        if (annotation.consumeMode() == ConsumeMode.ORDERLY &&
                annotation.messageModel() == MessageModel.BROADCASTING) {
            throw new BeanDefinitionValidationException(
                    "Bad annotation definition in @RocketMQMessageListenerAnnotation, messageModel BROADCASTING does not support ORDERLY message!");
        }
    }
}
