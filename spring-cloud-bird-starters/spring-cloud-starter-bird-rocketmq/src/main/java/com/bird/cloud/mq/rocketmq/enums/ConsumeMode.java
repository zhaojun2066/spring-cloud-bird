
package com.bird.cloud.mq.rocketmq.enums;

public enum ConsumeMode {
    /**
     * Receive asynchronously delivered messages concurrently
     */
    CONCURRENTLY,

    /**
     * Receive asynchronously delivered messages orderly. one queue, one thread
     */
    ORDERLY
}
