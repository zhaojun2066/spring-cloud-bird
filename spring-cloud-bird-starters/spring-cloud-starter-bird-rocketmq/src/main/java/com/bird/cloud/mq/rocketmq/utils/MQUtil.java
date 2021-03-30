package com.bird.cloud.mq.rocketmq.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.trace.AsyncTraceDispatcher;
import org.apache.rocketmq.client.trace.TraceDispatcher;
import org.apache.rocketmq.client.trace.hook.SendMessageTraceHookImpl;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-10 23:13
 **/

@Slf4j
public class MQUtil {

    public static DefaultMQProducer createDefaultMQProducer(String groupName, String ak, String sk,
                                                            boolean isEnableMsgTrace, String customizedTraceTopic) {

        boolean isEnableAcl = !StringUtils.isEmpty(ak) && !StringUtils.isEmpty(sk);
        DefaultMQProducer producer = new TransactionMQProducer(groupName);
        if (isEnableMsgTrace) {
            try {
                AsyncTraceDispatcher dispatcher = new AsyncTraceDispatcher(groupName, TraceDispatcher.Type.PRODUCE, customizedTraceTopic, isEnableAcl ? new AclClientRPCHook(new SessionCredentials(ak, sk)) : null);
                dispatcher.setHostProducer(producer.getDefaultMQProducerImpl());
                Field field = DefaultMQProducer.class.getDeclaredField("traceDispatcher");
                field.setAccessible(true);
                field.set(producer, dispatcher);
                producer.getDefaultMQProducerImpl().registerSendMessageHook(
                        new SendMessageTraceHookImpl(dispatcher));
            } catch (Throwable e) {
                log.error("system trace hook init failed ,maybe can't send msg trace data");
            }
        }

        return producer;
    }

    public static RPCHook getRPCHookByAkSk(Environment env, String accessKeyOrExpr, String secretKeyOrExpr) {
        String ak, sk;
        try {
            ak = env.resolveRequiredPlaceholders(accessKeyOrExpr);
            sk = env.resolveRequiredPlaceholders(secretKeyOrExpr);
        } catch (Exception e) {
            // Ignore it
            ak = null;
            sk = null;
        }
        if (!StringUtils.isEmpty(ak) && !StringUtils.isEmpty(sk)) {
            return new AclClientRPCHook(new SessionCredentials(ak, sk));
        }
        return null;
    }

    public static String getInstanceName(String identify) {
        char separator = '@';
        StringBuilder instanceName = new StringBuilder();
        instanceName.append(identify)
                .append(separator).append(UtilAll.getPid());
        return instanceName.toString();
    }


}
