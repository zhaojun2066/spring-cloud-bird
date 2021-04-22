package com.bird.cloud.sentinel.service.consumer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-22 14:37
 **/
@RestController
public class Controller {
    @Autowired
    private EchoService echoService;

    @GetMapping("/hello/{name}")
    public User echo(@PathVariable("name") String name){
        Object o =  echoService.echo(name);
        System.out.println("==========="+JSONObject.toJSONString(o));
        return (User)o;
    }
}
