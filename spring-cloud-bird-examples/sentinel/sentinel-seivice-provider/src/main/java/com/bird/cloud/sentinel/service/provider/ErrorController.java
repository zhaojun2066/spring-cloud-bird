package com.bird.cloud.sentinel.service.provider;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-21 14:52
 **/
@RestController
public class ErrorController{

    @GetMapping("/goerror")
    public String echo() {
        return  "echo error" ;
    }


}
