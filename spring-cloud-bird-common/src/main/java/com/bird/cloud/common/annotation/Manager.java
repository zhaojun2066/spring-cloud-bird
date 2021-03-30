package com.bird.cloud.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-16 00:02
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Manager {
}
