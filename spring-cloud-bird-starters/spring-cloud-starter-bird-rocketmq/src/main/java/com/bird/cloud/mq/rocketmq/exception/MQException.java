package com.bird.cloud.mq.rocketmq.exception;


public class MQException extends RuntimeException {
    public MQException(String msg) {
        super(msg);
    }

    public MQException(String msg,Throwable throwable) {
        super(msg,throwable);
    }
}
