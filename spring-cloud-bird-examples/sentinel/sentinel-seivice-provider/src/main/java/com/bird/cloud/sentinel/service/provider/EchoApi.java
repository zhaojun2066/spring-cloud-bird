package com.bird.cloud.sentinel.service.provider;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-21 14:50
 **/
public interface EchoApi {

    @RequestMapping(method = RequestMethod.GET,value = "/echo/{name}")
    String echo(@PathVariable(value = "name") String name);

    @RequestMapping(method = RequestMethod.GET,value = "/hello")
    String hello( String name);
}
