package com.bird.cloud.monitor.cat.support;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 16:11
 **/
public class CETransactionCAT {

    private static ThreadLocal<Transaction> threadLocal = new ThreadLocal<Transaction>();

    public static void start(String type,String name){
        Transaction transaction = Cat.newTransaction("MethodCustTimes","courseConverter.toCourseDtoList");
        threadLocal.set(transaction);
    }

    public static void stopSuccess(){
        Transaction transaction = threadLocal.get();
        transaction.setSuccessStatus();
        transaction.complete();
    }

    public static void stopFall(Throwable throwable){
        Transaction transaction = threadLocal.get();
        transaction.setStatus(throwable);
        Cat.logError(throwable);
        transaction.complete();
    }

    public static void stopStatus(String status){
        Transaction transaction = threadLocal.get();
        transaction.setStatus(status);
        transaction.complete();
    }

    private static void removeEvent(){
        threadLocal.remove();
    }

}
