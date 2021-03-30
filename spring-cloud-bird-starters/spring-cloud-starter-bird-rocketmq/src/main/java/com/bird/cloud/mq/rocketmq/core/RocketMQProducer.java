package com.bird.cloud.mq.rocketmq.core;

import com.bird.cloud.mq.rocketmq.exception.MQException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Collection;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-10 21:30
 **/


@Slf4j
public class RocketMQProducer extends AbstractProducer<Message> implements InitializingBean , DisposableBean {


    @Setter
    private DefaultMQProducer producer;


    // hash key 分片
    private MessageQueueSelector messageQueueSelector = new SelectMessageQueueByHash();

    public DefaultMQProducer getProducer() {
        return producer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (producer!=null){
            producer.start();
        }
    }

    @Override
    public void destroy() throws Exception {
        if (producer!=null){
            producer.shutdown();
        }
    }

    @Override
    public SendResult syncSend(Message message) {
        try {
            return  producer.send(message);
        } catch (MQClientException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (RemotingException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (MQBrokerException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (InterruptedException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }

    }

    @Override
    public SendResult syncSend(Message message, long timeout) {
        try {
            return   producer.send(message, timeout);
        } catch (MQClientException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (RemotingException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (MQBrokerException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (InterruptedException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }

    }

    @Override
    public SendResult syncSend(Collection<Message> msgs) {
        try {
            return  producer.send(msgs);
        } catch (MQClientException e) {

            throw new MQException("消息发送失败，msgs :" + msgs + ",e:" + e.getMessage());
        } catch (RemotingException e) {

            throw new MQException("消息发送失败，msgs :" + msgs + ",e:" + e.getMessage());
        } catch (MQBrokerException e) {

            throw new MQException("消息发送失败，msgs :" + msgs + ",e:" + e.getMessage());
        } catch (InterruptedException e) {

            throw new MQException("消息发送失败，msgs :" + msgs + ",e:" + e.getMessage());
        }

    }

    @Override
    public SendResult syncSend(Collection<Message> msgs, long timeout) {
        try {
            return producer.send(msgs,timeout);
        } catch (MQClientException e) {
            throw new MQException("消息发送失败，msgs :" + msgs + ",e:" + e.getMessage());
        } catch (RemotingException e) {
            throw new MQException("消息发送失败，msgs :" + msgs + ",e:" + e.getMessage());
        } catch (MQBrokerException e) {
            throw new MQException("消息发送失败，msgs :" + msgs + ",e:" + e.getMessage());
        } catch (InterruptedException e) {
            throw new MQException("消息发送失败，msgs :" + msgs + ",e:" + e.getMessage());
        }

    }

    @Override
    public SendResult syncSend(Message message,  Object hashKey) {
        try {
            return producer.send(message, messageQueueSelector, hashKey);
        } catch (MQClientException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (RemotingException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (MQBrokerException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (InterruptedException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }

    }

    @Override
    public SendResult syncSend(Message message, Object arg, long timeout) {
        try {
            return producer.send(message, messageQueueSelector, arg,timeout);
        } catch (MQClientException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (RemotingException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (MQBrokerException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (InterruptedException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }
    }

    @Override
    public void asyncSend(Message message, SendCallback sendCallback) {
        try {
            producer.send(message, sendCallback);
        } catch (MQClientException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (RemotingException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (InterruptedException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }
    }

    @Override
    public void asyncSend(Message message, SendCallback sendCallback, long timeout) {
        try {
            producer.send(message, sendCallback,timeout);
        } catch (MQClientException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (RemotingException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (InterruptedException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }
    }

    @Override
    public void asyncSend(Collection<Message> msgs, SendCallback sendCallback) {
        try {
            producer.send(msgs, sendCallback);
        } catch (MQClientException e) {
            throw new MQException("消息发送失败，topic :"  + e.getMessage());
        } catch (RemotingException e) {
            throw new MQException("消息发送失败，topic :"  + e.getMessage());
        } catch (MQBrokerException e) {
            throw new MQException("消息发送失败，topic :"  + e.getMessage());
        } catch (InterruptedException e) {
            throw new MQException("消息发送失败，topic :"  + e.getMessage());
        }
    }

    @Override
    public void asyncSend(Collection<Message> messages, SendCallback sendCallback, long timeout) {
        try {
            producer.send(messages, sendCallback,timeout);
        } catch (MQClientException e) {
            throw new MQException("消息发送失败，topic :"  + e.getMessage());
        } catch (RemotingException e) {
            throw new MQException("消息发送失败，topic :"  + e.getMessage());
        } catch (MQBrokerException e) {
            throw new MQException("消息发送失败，topic :"  + e.getMessage());
        } catch (InterruptedException e) {
            throw new MQException("消息发送失败，topic :"  + e.getMessage());
        }
    }

    @Override
    public void asyncSend(Message message, Object arg, SendCallback sendCallback) {
        try {
            producer.send(message, messageQueueSelector,arg,sendCallback);
        } catch (MQClientException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (RemotingException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (InterruptedException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }
    }

    @Override
    public void asyncSend(Message msg, Object arg, SendCallback sendCallback, long timeout) {
        try {
            producer.send(msg, messageQueueSelector,arg,sendCallback,timeout);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendOneway(Message message) {
        try {
            producer.sendOneway(message);
        } catch (MQClientException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (RemotingException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (InterruptedException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }
    }

    @Override
    public void sendOneway(Message message, Object arg) {
        try {
            producer.sendOneway(message, messageQueueSelector, arg);
        } catch (MQClientException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (RemotingException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        } catch (InterruptedException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }
    }

    @Override
    public TransactionSendResult sendMessageInTransaction(Message message, Object arg) {
        try {
            return producer.sendMessageInTransaction(message, arg);
        } catch (MQClientException e) {
            log.error("消息发送失败，topic : {}, msgObj {}", message.getTopic(), message);
            throw new MQException("消息发送失败，topic :" + message.getTopic() + ",e:" + e.getMessage());
        }
    }
}
