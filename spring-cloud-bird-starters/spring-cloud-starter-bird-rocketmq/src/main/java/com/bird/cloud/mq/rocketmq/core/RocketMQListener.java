package com.bird.cloud.mq.rocketmq.core;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-11 09:34
 **/
public interface RocketMQListener<T> {
    void onMessage(T message);
}
