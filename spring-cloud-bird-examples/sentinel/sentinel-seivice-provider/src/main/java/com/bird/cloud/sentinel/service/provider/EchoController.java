package com.bird.cloud.sentinel.service.provider;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-04-21 14:52
 **/
@RestController
public class EchoController implements EchoApi {

    @SentinelResource(value = "/echo/{name}",
            fallback = "fallback",
            fallbackClass = Handler.class,
            blockHandler = "block" ,blockHandlerClass = Handler.class // block 优先级大于 fallback
      )
    @Override
    public String echo(String name) {
        return  "echo " + name ;
    }


    @SentinelResource(value = "/hello",blockHandler = "echo" ,blockHandlerClass = Handler.class)
    @Override
    public String hello(String name) {
        return  "hello " + name ;
    }


    public static class Handler{
        public static String fallback(String s, BlockException ex){
            ex.printStackTrace();
            return " fallback " + s;
        }

        public static String block(String s, BlockException ex){
            ex.printStackTrace();
            return " block被限流了 " + s;
        }
    }
}
