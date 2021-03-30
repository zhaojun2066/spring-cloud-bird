package com.bird.cloud.mq.rocketmq.core;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;

import java.util.Collection;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-10 21:51
 **/
public  abstract class AbstractProducer<Message> {

    public abstract SendResult syncSend(Message message);
    public abstract SendResult syncSend(Message message, long timeout);

    public abstract SendResult syncSend(Collection<Message> messages);
    public abstract SendResult syncSend(Collection<Message> messages, long timeout);

    public abstract SendResult syncSend(Message message, Object hashKey);
    public abstract SendResult syncSend(Message message,Object hashKey, long timeout);


    public abstract void asyncSend(Message message, SendCallback sendCallback);
    public abstract void asyncSend(Message message, SendCallback sendCallback, long timeout);
    public abstract void asyncSend(Collection<Message> messages, SendCallback sendCallback);
    public abstract void asyncSend(Collection<Message> messages, SendCallback sendCallback, long timeout);
    public abstract void asyncSend(Message message,Object hashKey, SendCallback sendCallback);
    public abstract void asyncSend(Message message,Object hashKey, SendCallback sendCallback, long timeout);

    public abstract void sendOneway(Message message);
    public abstract void sendOneway(Message message, Object hashKey);


    public abstract TransactionSendResult sendMessageInTransaction(org.apache.rocketmq.common.message.Message msg,
                                                          Object arg);

}
