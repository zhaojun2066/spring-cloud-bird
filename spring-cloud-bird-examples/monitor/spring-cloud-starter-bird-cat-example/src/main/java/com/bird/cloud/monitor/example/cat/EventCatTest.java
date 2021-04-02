package com.bird.cloud.monitor.example.cat;

import cn.ce.cloud.monitor.cat.support.CEEventCAT;
import org.springframework.stereotype.Component;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 19:55
 **/

@Component
public class EventCatTest {



    public String createOrderSuccess(){
        CEEventCAT.start("CREATE_ORDER","CHECK_STORAGE");
        try {
            // 自己的业务
            int a = 10;
            int b = a-1;
            CEEventCAT.success();
        }finally {
            CEEventCAT.stop();
        }
        return "createOrder";
    }

    public String createOrderFall(){
        CEEventCAT.start("CREATE_ORDER","CHECK_STORAGE");
        try {
            // 自己的业务 模拟异常
            int a = 10;
            int b = a-1/0;
        }catch (Exception e){
            CEEventCAT.fall(e);
        } finally {
            CEEventCAT.stop();
        }
        return "createOrder";
    }

    public String createOrder2Success(){
        CEEventCAT.start("CREATE_ORDER","CHECK_ACCOUNT");
        try {
            // 自己的业务
            int a = 10;
            int b = a-1;
            CEEventCAT.success();
        }finally {
            CEEventCAT.stop();
        }
        return "createOrder";
    }


}
