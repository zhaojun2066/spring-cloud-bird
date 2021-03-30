package com.bird.cloud.mq.rocketmq.utils;

import com.bird.cloud.mq.rocketmq.enums.DelayTimeLevel;
import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.Charset;

@Data
@Slf4j
public class MessageBuilder {

    private static Gson gson = new Gson();

    private static final String[] DELAY_ARRAY = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h".split(" ");

    private String topic;
    private String tag;
    private String key;
    private Object message;
    private Integer delayTimeLevel;
    private static final String charset = "utf-8";

    public static MessageBuilder of(String topic, String tag) {
        MessageBuilder builder = new MessageBuilder();
        builder.setTopic(topic);
        builder.setTag(tag);
        return builder;
    }

    public static MessageBuilder of(Object message) {
        MessageBuilder builder = new MessageBuilder();
        builder.setMessage(message);
        return builder;
    }

    public MessageBuilder topic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder tag(String tag) {
        this.tag = tag;
        return this;
    }

    public MessageBuilder key(String key) {
        this.key = key;
        return this;
    }

    public MessageBuilder delayTimeLevel(DelayTimeLevel delayTimeLevel) {
        this.delayTimeLevel = delayTimeLevel.getLevel();
        return this;
    }

    public Message build() {
        String str = gson.toJson(message);
        if(StringUtils.isEmpty(topic)) {
            if(StringUtils.isEmpty(getTopic())) {
                throw new RuntimeException("no topic defined to send this message");
            }
        }
        Message message = new Message(topic, str.getBytes(Charset.forName(charset)));
        if (!StringUtils.isEmpty(tag)) {
            message.setTags(tag);
        }
     /*   if(StringUtils.isNotEmpty(messageKey)) {
            message.setKeys(messageKey);
        }*/
        if(delayTimeLevel != null && delayTimeLevel > 0 && delayTimeLevel <= DELAY_ARRAY.length) {
            message.setDelayTimeLevel(delayTimeLevel);
        }
        return message;
    }



}
