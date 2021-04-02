package com.bird.cloud.mq.rocketmq.example.producer;

import com.bird.cloud.mq.rocketmq.annotation.RocketMQTransactionListener;
import com.bird.cloud.mq.rocketmq.core.RocketMQLocalTransactionListener;
import com.bird.cloud.mq.rocketmq.core.RocketMQProducer;
import com.bird.cloud.mq.rocketmq.example.domain.User;
import com.bird.cloud.mq.rocketmq.utils.MessageBuilder;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-12 23:18
 **/
@SpringBootApplication
public class App  implements CommandLineRunner {

    @Value("${demo.rocketmq.topic}")
    private String springTopic;

    @Value("${demo.rocketmq.userTopic}")
    private String userTopic;

    @Resource(name = "defaultRocketMQProducer")
    private RocketMQProducer rocketMQProducer;

    @Resource(name = "myProducer")
    private RocketMQProducer myProducer;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        // send string
        sendString();
        batchSendString();
        // send user object
        sendUser();
        asyncSendUser();
        sendTransactionMessage();
        sendTransactionMessage02();
    }


    private void sendString(){
        SendResult sendResult = rocketMQProducer.syncSend(MessageBuilder.of("hello-world000005").topic(springTopic).tag("hhh").build());
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", springTopic, sendResult);

        sendResult = rocketMQProducer.syncSend(MessageBuilder.of("hello-world000006").topic(springTopic).tag("hhh").build());
        System.out.printf("syncSend2 to topic %s sendResult=%s %n", springTopic, sendResult);

        sendResult = rocketMQProducer.syncSend(MessageBuilder.of("hello-world000007").topic(springTopic).tag("hhh").build());
        System.out.printf("syncSend3 to topic %s sendResult=%s %n", springTopic, sendResult);
    }

    private void sendUser(){
        User user = new User();
        user.setAddress("廊坊");
        user.setId(1000);
        user.setName("飓风");
        user.setAge(33);
        user.setBirthday(LocalDate.now());

        SendResult sendResult = rocketMQProducer.syncSend(MessageBuilder.of(user).topic(userTopic).build());
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", userTopic, sendResult);

    }
    private void asyncSendUser(){
        User user = new User();
        user.setAddress("廊坊");
        user.setId(1000);
        user.setName("飓风");
        user.setAge(33);
        user.setBirthday(LocalDate.now());

        rocketMQProducer.asyncSend(MessageBuilder.of(user).topic(userTopic).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("syncSend1 to topic %s sendResult=%s %n", userTopic, sendResult);
            }

            @Override
            public void onException(Throwable e) {
             e.printStackTrace();
            }
        });
   //     System.out.printf("syncSend1 to topic %s sendResult=%s %n", userTopic, sendResult);

    }

    private void  batchSendString(){
        List list = Arrays.asList(
                MessageBuilder.of("hello-world000008").topic(springTopic).tag("hhh").build(),
                MessageBuilder.of("hello-world000009").topic(springTopic).tag("hhh").build()
                );
        SendResult sendResult = rocketMQProducer.syncSend(list);
        System.out.printf("batchSendString to topic %s sendResult=%s %n", springTopic, sendResult);
    }

    private void sendTransactionMessage(){
        TransactionSendResult result = rocketMQProducer.sendMessageInTransaction(MessageBuilder.of("transactionMessage").topic("transactionTopic").build(),1);
        System.out.printf("sendTransactionMessage to topic %s sendResult=%s %n", "transactionMessage", result);
    }

    private void sendTransactionMessage02(){
        TransactionSendResult result = myProducer.sendMessageInTransaction(MessageBuilder.of("transactionMessage_ext_000").topic("transactionTopic").build(),1);
        System.out.printf("sendTransactionMessage02 to topic %s sendResult=%s %n", "transactionMessage", result);
    }


    @RocketMQTransactionListener
    static class TransactionListenerImpl implements RocketMQLocalTransactionListener {


        private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

        @Override
        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            // 执行本地业务逻辑，根据自己本地 事物的状态，返回mq 的状态，这里只是模拟返回中间状态unknown，让mq 会查
            localTrans.put("msgId", (Integer) arg);
            return LocalTransactionState.UNKNOW;
        }

        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
            Integer status = localTrans.get("msgId");
            if (null != status) {
                switch (status) {
                    case 0:
                        return LocalTransactionState.UNKNOW;
                    case 1:
                        return LocalTransactionState.COMMIT_MESSAGE;
                    case 2:
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
            return LocalTransactionState.COMMIT_MESSAGE;
        }
    }


    @RocketMQTransactionListener(rocketMQProducerBeanName = "myProducer")
    static class EXTTransactionListenerImpl implements RocketMQLocalTransactionListener {


        private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

        @Override
        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            // 执行本地业务逻辑，根据自己本地 事物的状态，返回mq 的状态，这里只是模拟返回中间状态unknown，让mq 会查
            localTrans.put("msgId", (Integer) arg);
            return LocalTransactionState.UNKNOW;
        }

        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
            Integer status = localTrans.get("msgId");
            if (null != status) {
                switch (status) {
                    case 0:
                        return LocalTransactionState.UNKNOW;
                    case 1:
                        return LocalTransactionState.COMMIT_MESSAGE;
                    case 2:
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
            return LocalTransactionState.COMMIT_MESSAGE;
        }
    }



}
