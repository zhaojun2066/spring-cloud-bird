package com.bird.cloud.sentinel.service.provider;

import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-21 14:52
 **/
@RestController
public class EchoController implements EchoApi {
    @Override
    public String echo(String name) {
        return  "hello " + name ;
    }
}
