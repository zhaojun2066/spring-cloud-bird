package com.bird.cloud.sentinel.service.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-21 14:50
 **/
@FeignClient(name = "sentinel-seivice-provider")
public interface EchoService {

    @RequestMapping(method = RequestMethod.GET,value = "/echo/{name}")
    User echo(@PathVariable(value = "name") String name);

    @RequestMapping(method = RequestMethod.GET,value = "/hello")
    String hello(String name);
}
