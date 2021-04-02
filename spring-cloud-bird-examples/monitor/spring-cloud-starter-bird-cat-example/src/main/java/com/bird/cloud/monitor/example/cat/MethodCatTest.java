package com.bird.cloud.monitor.example.cat;

import cn.ce.cloud.monitor.cat.aop.CatMethodAnnotation;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 19:55
 **/

@Component
public class MethodCatTest {



    @CatMethodAnnotation
    public String getUser(){
        return "User Empty";
    }

    @CatMethodAnnotation
    public String getCourse(){
        return "Course Empty";
    }
}
