package com.bird.cloud.mq.rocketmq.annotation;

import com.bird.cloud.mq.rocketmq.enums.ConsumeMode;
import com.bird.cloud.mq.rocketmq.enums.MessageModel;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-11 21:37
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RocketMQMessageListener {
    String NAME_SERVER_PLACEHOLDER = "${ce.cloud.mq.rocketmq.name-server:}";
    String ACCESS_KEY_PLACEHOLDER = "${ce.cloud.mq.rocketmq.consumer.access-key:}";
    String SECRET_KEY_PLACEHOLDER = "${ce.cloud.mq.rocketmq.consumer.secret-key:}";
    String TRACE_TOPIC_PLACEHOLDER = "${ce.cloud.mq.rocketmq.consumer.customized-trace-topic:}";
    String ACCESS_CHANNEL_PLACEHOLDER = "${ce.cloud.mq.rocketmq.access-channel:}";

    String consumerGroup();


    String topic();


    String tags() default "*";

    ConsumeMode consumeMode() default ConsumeMode.CONCURRENTLY;

    MessageModel messageModel() default MessageModel.CLUSTERING;


    int consumeThreadMax() default 64;

    /**
     * Maximum amount of time in minutes a message may block the consuming thread.
     */
    long consumeTimeout() default 15L;

    /**
     * The property of "access-key".
     */
    String accessKey() default ACCESS_KEY_PLACEHOLDER;

    /**
     * The property of "secret-key".
     */
    String secretKey() default SECRET_KEY_PLACEHOLDER;

    /**
     * Switch flag instance for message trace.
     */
    boolean enableMsgTrace() default true;

    /**
     * The name value of message trace topic.If you don't config,you can use the default trace topic name.
     */
    String customizedTraceTopic() default TRACE_TOPIC_PLACEHOLDER;

    /**
     * The property of "name-server".
     */
    String nameServer() default NAME_SERVER_PLACEHOLDER;

    /**
     * The property of "access-channel".
     */
    String accessChannel() default ACCESS_CHANNEL_PLACEHOLDER;
}
