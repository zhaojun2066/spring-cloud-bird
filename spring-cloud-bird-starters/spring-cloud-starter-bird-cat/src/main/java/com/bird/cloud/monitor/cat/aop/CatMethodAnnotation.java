package com.bird.cloud.monitor.cat.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-10-21 09:18
 **/

@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface CatMethodAnnotation {

}
