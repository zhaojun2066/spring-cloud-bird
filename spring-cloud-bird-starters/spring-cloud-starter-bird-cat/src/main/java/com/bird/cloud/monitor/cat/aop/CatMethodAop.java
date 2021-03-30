package com.bird.cloud.monitor.cat.aop;


import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;


/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-10-09 14:19
 **/

@Aspect
public class CatMethodAop {
    @Around(value = "@annotation(CatMethodAnnotation)")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
        Method method = joinPointObject.getMethod();
        //System.out.println(pjp.getTarget().getClass().getName());
        String value = pjp.getTarget().getClass().getName() +"." +method.getName();
        Transaction t = Cat.newTransaction("Method", value);

        try {
            Object res = pjp.proceed();
            t.setSuccessStatus();
            return res;
        } catch (Throwable e) {
            t.setStatus(e);
            Cat.logError(e);
            throw e;
        } finally {
            t.complete();
        }

    }
}
