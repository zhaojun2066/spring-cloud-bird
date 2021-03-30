package com.bird.cloud.mq.rocketmq.core;

import com.bird.cloud.mq.rocketmq.annotation.RocketMQMessageListener;
import com.bird.cloud.mq.rocketmq.enums.ConsumeMode;
import com.bird.cloud.mq.rocketmq.enums.MessageModel;
import com.bird.cloud.mq.rocketmq.exception.MQException;
import com.bird.cloud.mq.rocketmq.utils.MQUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-11 21:51
 **/
@Slf4j
public class DefaultRocketMQListenerContainer implements InitializingBean,
        RocketMQListenerContainer, SmartLifecycle, ApplicationContextAware {

    protected static Gson gson = new Gson();

    private ApplicationContext applicationContext;

    /**
     * The name of the DefaultRocketMQListenerContainer instance
     */
    private String name;

    private long suspendCurrentQueueTimeMillis = 1000;

    /**
     * Message consume retry strategy<br> -1,no retry,put into DLQ directly<br> 0,broker control retry frequency<br>
     * >0,client control retry frequency.
     */
    private int delayLevelWhenNextConsume = 0;

    private String nameServer;

    private AccessChannel accessChannel = AccessChannel.LOCAL;

    private String consumerGroup;

    private String topic;

    private int consumeThreadMax = 64;

    private String charset = "UTF-8";

    private RocketMQMessageListener rocketMQMessageListenerAnnotation;

    private RocketMQListener rocketMQListener;

    private DefaultMQPushConsumer consumer;

    private Type messageType;

    private MethodParameter methodParameter;

    private boolean running;

    // The following properties came from @RocketMQMessageListener.
    private ConsumeMode consumeMode;
    private String tags;
    private MessageModel messageModel;
    private long consumeTimeout;




    @Override
    public void destroy() throws Exception {
        this.setRunning(false);
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
        }
        log.info("container destroyed, {}", this.toString());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initRocketMQPushConsumer();

        this.messageType = getMessageType();
        log.debug("RocketMQ messageType: {}", messageType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext  = applicationContext;

    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable runnable) {
        stop();
        runnable.run();
    }

    @Override
    public void start() {
        if (this.isRunning()) {
            throw new IllegalStateException("container already running. " + this.toString());
        }

        try {
            consumer.start();
        } catch (MQClientException e) {
            throw new IllegalStateException("Failed to start RocketMQ push consumer", e);
        }
        this.setRunning(true);

        log.info("running container: {}", this.toString());
    }

    @Override
    public void stop() {
        if (this.isRunning()) {
            if (Objects.nonNull(consumer)) {
                consumer.shutdown();
            }
            setRunning(false);
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        // Returning Integer.MAX_VALUE only suggests that
        // we will be the first bean to shutdown and last bean to start
        return Integer.MAX_VALUE;
    }

    private Object doConvertMessage(MessageExt messageExt) {
        if (messageExt == null || messageExt.getBody() == null) {
            return null;
        }
        if (Objects.equals(messageType, MessageExt.class)) {
            return messageExt;
        } else {
            String str = new String(messageExt.getBody(), Charset.forName(charset));
            if (Objects.equals(messageType, String.class)) {
                return str;
            } else {
                // If msgType not string, use objectMapper change it.
                try {
                    if (messageType instanceof Class) {
                        //if the messageType has not Generic Parameter
                        return gson.fromJson(new String(messageExt.getBody(),charset), messageType);
                    } else {
                        log.warn("Parse msg error. {}", messageExt);
                        throw new MQException("cannot convert message to " + messageType);
                    }
                } catch (Exception e) {
                    log.info("convert failed. str:{}, msgType:{}", str, messageType);
                    throw new MQException("cannot convert message to " + messageType, e);
                }
            }
        }

    }

    private void handleMessage(
            MessageExt messageExt) throws RuntimeException {
        if (rocketMQListener != null) {
            rocketMQListener.onMessage(doConvertMessage(messageExt));
        }
    }

    private Type getMessageType() {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(rocketMQListener);
        Type matchedGenericInterface = null;
        while (Objects.nonNull(targetClass)) {
            Type[] interfaces = targetClass.getGenericInterfaces();
            if (Objects.nonNull(interfaces)) {
                for (Type type : interfaces) {
                    if (type instanceof ParameterizedType &&
                            (Objects.equals(((ParameterizedType) type).getRawType(), RocketMQListener.class)/* || Objects.equals(((ParameterizedType) type).getRawType(), RocketMQReplyListener.class)*/)) {
                        matchedGenericInterface = type;
                        break;
                    }
                }
            }
            targetClass = targetClass.getSuperclass();
        }
        if (Objects.isNull(matchedGenericInterface)) {
            return Object.class;
        }

        Type[] actualTypeArguments = ((ParameterizedType) matchedGenericInterface).getActualTypeArguments();
        if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
            return actualTypeArguments[0];
        }
        return Object.class;
    }

    private void initRocketMQPushConsumer() throws MQClientException {
        if (rocketMQMessageListenerAnnotation == null /*&& rocketMQReplyListener == null*/) {
            throw new IllegalArgumentException("Property 'rocketMQListener' or 'rocketMQReplyListener' is required");
        }
        Assert.notNull(consumerGroup, "Property 'consumerGroup' is required");
        Assert.notNull(nameServer, "Property 'nameServer' is required");
        Assert.notNull(topic, "Property 'topic' is required");

        RPCHook rpcHook = MQUtil.getRPCHookByAkSk(applicationContext.getEnvironment(),
                this.rocketMQMessageListenerAnnotation.accessKey(), this.rocketMQMessageListenerAnnotation.secretKey());
        boolean enableMsgTrace = rocketMQMessageListenerAnnotation.enableMsgTrace();
        if (Objects.nonNull(rpcHook)) {
            consumer = new DefaultMQPushConsumer(consumerGroup, rpcHook, new AllocateMessageQueueAveragely(),
                    enableMsgTrace, this.applicationContext.getEnvironment().
                    resolveRequiredPlaceholders(this.rocketMQMessageListenerAnnotation.customizedTraceTopic()));
            consumer.setVipChannelEnabled(false);
        } else {
            log.debug("Access-key or secret-key not configure in " + this + ".");
            consumer = new DefaultMQPushConsumer(consumerGroup, enableMsgTrace,
                    this.applicationContext.getEnvironment().
                            resolveRequiredPlaceholders(this.rocketMQMessageListenerAnnotation.customizedTraceTopic()));
        }

        consumer.setInstanceName(MQUtil.getInstanceName(nameServer));

        String customizedNameServer = this.applicationContext.getEnvironment().resolveRequiredPlaceholders(this.rocketMQMessageListenerAnnotation.nameServer());
        if (customizedNameServer != null) {
            consumer.setNamesrvAddr(customizedNameServer);
        } else {
            consumer.setNamesrvAddr(nameServer);
        }
        if (!StringUtils.isEmpty(accessChannel)) {
            consumer.setAccessChannel(accessChannel);
        }
        consumer.setConsumeThreadMax(consumeThreadMax);
        if (consumeThreadMax < consumer.getConsumeThreadMin()) {
            consumer.setConsumeThreadMin(consumeThreadMax);
        }
        consumer.setConsumeTimeout(consumeTimeout);

        switch (messageModel) {
            case BROADCASTING:
                consumer.setMessageModel(org.apache.rocketmq.common.protocol.heartbeat.MessageModel.BROADCASTING);
                break;
            case CLUSTERING:
                consumer.setMessageModel(org.apache.rocketmq.common.protocol.heartbeat.MessageModel.CLUSTERING);
                break;
            default:
                throw new IllegalArgumentException("Property 'messageModel' was wrong.");
        }

        if (StringUtils.isEmpty(tags)){
            consumer.subscribe(topic, "*");
        }else {
            consumer.subscribe(topic, tags);
        }

        switch (consumeMode) {
            case ORDERLY:
                consumer.setMessageListener(new DefaultMessageListenerOrderly());
                break;
            case CONCURRENTLY:
                consumer.setMessageListener(new DefaultMessageListenerConcurrently());
                break;
            default:
                throw new IllegalArgumentException("Property 'consumeMode' was wrong.");
        }
    }


    /**
     * 顺序消费
     */
    public class DefaultMessageListenerOrderly implements MessageListenerOrderly {

        @SuppressWarnings("unchecked")
        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            for (MessageExt messageExt : msgs) {
                log.debug("received msg: {}", messageExt);
                try {
                    long now = System.currentTimeMillis();
                    handleMessage(messageExt);
                    long costTime = System.currentTimeMillis() - now;
                    log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", messageExt.getMsgId(), messageExt.getTopic(), messageExt.getReconsumeTimes(), e);
                    context.setSuspendCurrentQueueTimeMillis(suspendCurrentQueueTimeMillis);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
            }

            return ConsumeOrderlyStatus.SUCCESS;
        }
    }


    /**
     * 并发消费
     */
    public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {

        @SuppressWarnings("unchecked")
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            for (MessageExt messageExt : msgs) {
                log.debug("received msg: {}", messageExt);
                try {
                    long now = System.currentTimeMillis();
                    handleMessage(messageExt);
                    long costTime = System.currentTimeMillis() - now;
                    log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    log.warn("consume message failed. messageId:{}, topic:{}, reconsumeTimes:{}", messageExt.getMsgId(), messageExt.getTopic(), messageExt.getReconsumeTimes(), e);
                    context.setDelayLevelWhenNextConsume(delayLevelWhenNextConsume);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }




    public long getSuspendCurrentQueueTimeMillis() {
        return suspendCurrentQueueTimeMillis;
    }

    public void setSuspendCurrentQueueTimeMillis(long suspendCurrentQueueTimeMillis) {
        this.suspendCurrentQueueTimeMillis = suspendCurrentQueueTimeMillis;
    }

    public int getDelayLevelWhenNextConsume() {
        return delayLevelWhenNextConsume;
    }

    public void setDelayLevelWhenNextConsume(int delayLevelWhenNextConsume) {
        this.delayLevelWhenNextConsume = delayLevelWhenNextConsume;
    }

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public AccessChannel getAccessChannel() {
        return accessChannel;
    }

    public void setAccessChannel(AccessChannel accessChannel) {
        this.accessChannel = accessChannel;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getConsumeThreadMax() {
        return consumeThreadMax;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public RocketMQListener getRocketMQListener() {
        return rocketMQListener;
    }

    public void setRocketMQListener(RocketMQListener rocketMQListener) {
        this.rocketMQListener = rocketMQListener;
    }

    public RocketMQMessageListener getRocketMQMessageListenerAnnotation() {
        return rocketMQMessageListenerAnnotation;
    }

    public void setRocketMQMessageListenerAnnotation(RocketMQMessageListener anno) {
        this.rocketMQMessageListenerAnnotation = anno;

        this.consumeMode = anno.consumeMode();
        this.consumeThreadMax = anno.consumeThreadMax();
        this.messageModel = anno.messageModel();
        this.tags = anno.tags();
        this.consumeTimeout = anno.consumeTimeout();
    }
    public ConsumeMode getConsumeMode() {
        return consumeMode;
    }
    public DefaultMQPushConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
    }

    public MessageModel getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(MessageModel messageModel) {
        this.messageModel = messageModel;
    }

    public void setConsumeThreadMax(int consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }

    public void setMessageType(Type messageType) {
        this.messageType = messageType;
    }

    public void setConsumeMode(ConsumeMode consumeMode) {
        this.consumeMode = consumeMode;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getConsumeTimeout() {
        return consumeTimeout;
    }

    public void setConsumeTimeout(long consumeTimeout) {
        this.consumeTimeout = consumeTimeout;
    }

    private void setRunning(boolean running) {
        this.running = running;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
