package com.bird.cloud.mq.rocketmq.config;

import com.bird.cloud.mq.rocketmq.annotation.RocketMQTransactionListener;
import com.bird.cloud.mq.rocketmq.core.RocketMQLocalTransactionListener;
import com.bird.cloud.mq.rocketmq.core.RocketMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-12 22:11
 **/
@Slf4j
@Configuration
public class RocketMQTransactionConfiguration implements ApplicationContextAware, SmartInitializingSingleton {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(RocketMQTransactionListener.class)
                .entrySet().stream().filter(entry -> !ScopedProxyUtils.isScopedTarget(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        beans.forEach(this::registerTransactionListener);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    private void registerTransactionListener(String beanName, Object bean) {
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);

        if (!RocketMQLocalTransactionListener.class.isAssignableFrom(bean.getClass())) {
            throw new IllegalStateException(clazz + " is not instance of " + RocketMQLocalTransactionListener.class.getName());
        }
        RocketMQTransactionListener annotation = clazz.getAnnotation(RocketMQTransactionListener.class);
        RocketMQProducer rocketMQProducer = (RocketMQProducer) applicationContext.getBean(annotation.rocketMQProducerBeanName());
        if (((TransactionMQProducer) rocketMQProducer.getProducer()).getTransactionListener() != null) {
            throw new IllegalStateException(annotation.rocketMQProducerBeanName() + " already exists RocketMQLocalTransactionListener");
        }
        ((TransactionMQProducer) rocketMQProducer.getProducer()).setExecutorService(new ThreadPoolExecutor(annotation.corePoolSize(), annotation.maximumPoolSize(),
                annotation.keepAliveTime(), TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(annotation.blockingQueueSize())));
        ((TransactionMQProducer) rocketMQProducer.getProducer()).setTransactionListener((RocketMQLocalTransactionListener) bean);
        log.debug("RocketMQLocalTransactionListener {} register to {} success", clazz.getName(), annotation.rocketMQProducerBeanName());
    }

}
