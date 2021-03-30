package com.bird.cloud.mq.rocketmq.config;

import lombok.Data;
import org.apache.rocketmq.common.topic.TopicValidator;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-10 22:46
 **/

@Data
@ConfigurationProperties(prefix = "bird.cloud.mq.rocketmq")
public class RocketMQProperties {
    private String nameServer;
    private String accessChannel;
    private ProducerProperties producer;
    private ConsumerProperties consumer;


    @Data
    public static class ProducerProperties{
        private String group;
        private int sendMessageTimeout = 3000;//Millis of send message timeout.
        private int compressMsgBodyOverHowmuch = 1024 * 4;
        private int retryTimesWhenSendFailed = 2;
        private int retryTimesWhenSendAsyncFailed = 2;
        private boolean retryAnotherBrokerWhenNotStoreOK = false;
        private String accessKey;
        private String secretKey;
        private boolean enableMsgTrace = true;
        private String customizedTraceTopic = TopicValidator.RMQ_SYS_TRACE_TOPIC;
        private int maxMessageSize;
    }


    @Data
    private static class ConsumerProperties{
        private String group;
        private String topic;
        private String tags;
       // private String  messageModel = "CLUSTERING";
        private String accessKey;
        private String secretKey;
    }

}
