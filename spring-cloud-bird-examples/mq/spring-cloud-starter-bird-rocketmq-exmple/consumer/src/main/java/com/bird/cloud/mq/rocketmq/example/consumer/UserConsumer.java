package com.bird.cloud.mq.rocketmq.example.consumer;


import com.bird.cloud.mq.rocketmq.annotation.RocketMQMessageListener;
import com.bird.cloud.mq.rocketmq.core.RocketMQListener;
import com.bird.cloud.mq.rocketmq.example.domain.User;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-13 09:02
 **/
@RocketMQMessageListener(topic="${demo.rocketmq.userTopic}",consumerGroup = "user_test")
public class UserConsumer  implements RocketMQListener<User> {
    @Override
    public void onMessage(User message) {
        System.out.println("consumer user=> " + message);
    }
}
