package com.bird.cloud.monitor.cat.support;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 16:11
 **/
public class CEEventCAT {

    private static ThreadLocal<Event> threadLocal = new ThreadLocal<Event>();

    public static void start(String type,String name){
        Event event = Cat.newEvent(type,name);
        threadLocal.set(event);
    }

    public static void success(){
        Event event = threadLocal.get();
        event.setSuccessStatus();
    }

    public static void stop(){
        Event event = threadLocal.get();
        event.complete();
        removeEvent();
    }

    public static void fall(Throwable throwable){
        Event event = threadLocal.get();
        event.setStatus(throwable);
    }

    public static void status(String status){
        Event event = threadLocal.get();
        event.setStatus(status);
    }

    private static void removeEvent(){
        threadLocal.remove();
    }

}
