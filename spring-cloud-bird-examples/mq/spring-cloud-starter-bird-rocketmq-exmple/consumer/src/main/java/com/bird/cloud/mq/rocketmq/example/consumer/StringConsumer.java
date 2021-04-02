package com.bird.cloud.mq.rocketmq.example.consumer;


import com.bird.cloud.mq.rocketmq.annotation.RocketMQMessageListener;
import com.bird.cloud.mq.rocketmq.core.RocketMQListener;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-12 23:36
 **/
@RocketMQMessageListener(topic="${demo.rocketmq.topic}",consumerGroup = "test")
public class StringConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("string=> " + message);
    }
}
