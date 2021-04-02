package com.bird.cloud.monitor.example.cat.controller;

import com.bird.cloud.monitor.example.cat.BusinesMertricTest;
import com.bird.cloud.monitor.example.cat.EventCatTest;
import com.bird.cloud.monitor.example.cat.MethodCatTest;
import com.bird.cloud.monitor.example.cat.dao.MallOrderMapper;
import com.bird.cloud.monitor.example.cat.entity.MallOrderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-19 18:29
 **/

@RestController
public class TestController {

    @Autowired
    private MethodCatTest methodCatTest;

    @Autowired
    private EventCatTest eventCatTest;

    @Autowired
    private BusinesMertricTest businesMertricTest;


    @Autowired
    private MallOrderMapper mallOrderMapper;

    @GetMapping("/test")
    public String test(){
        methodCatTest.getUser();
        methodCatTest.getCourse();
        eventCatTest.createOrder2Success();
        eventCatTest.createOrderSuccess();
        eventCatTest.createOrderFall();

        businesMertricTest.increMemberCount();
        businesMertricTest.increOrderCount();
        businesMertricTest.avg();
        businesMertricTest.sum();

        mallOrderMapper.selectByPrimaryKey(10);
        mallOrderMapper.selectByExample(MallOrderExample.newAndCreateCriteria().example());
        return "OK";
    }
}
