package com.bird.cloud.seata.example.order.server.service;

import com.bird.cloud.seata.example.order.query.AddOrderQuery;
import com.bird.cloud.seata.example.order.server.converters.MallOrderConverter;
import com.bird.cloud.seata.example.order.server.dao.MallOrderMapper;
import com.bird.cloud.seata.example.order.server.entity.MallOrder;
import com.bird.cloud.seata.example.order.server.manager.MallOrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-16 00:52
 **/
@Service
public class MallOrderService {

    @Autowired
    private MallOrderManager orderManager;

    @Autowired
    private MallOrderMapper orderMapper;

    @Autowired
    private MallOrderConverter converter;

    @Transactional
    public void addOrder(AddOrderQuery orderQuery){
        MallOrder order =  converter.toOrder(orderQuery);
        orderMapper.insert(order);
        //throw new RuntimeException("se===================");
    }
}
