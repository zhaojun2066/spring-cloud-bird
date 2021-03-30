package com.bird.cloud.mq.rocketmq.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-12 22:09
 **/
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RocketMQTransactionListener {
    /**
     * Set ExecutorService params -- corePoolSize
     */
    int corePoolSize() default 1;

    /**
     * Set ExecutorService params -- maximumPoolSize
     */
    int maximumPoolSize() default 1;

    /**
     * Set ExecutorService params -- keepAliveTime
     */
    long keepAliveTime() default 1000 * 60; //60ms

    /**
     * Set ExecutorService params -- blockingQueueSize
     */
    int blockingQueueSize() default 2000;

    /**
     * Set rocketMQTemplate bean name, the default is defaultRocketMQProducer.
     * if use ExtRocketMQProducer, can set ExtRocketMQProducer bean name.
     */
    String rocketMQProducerBeanName() default "defaultRocketMQProducer";
}
