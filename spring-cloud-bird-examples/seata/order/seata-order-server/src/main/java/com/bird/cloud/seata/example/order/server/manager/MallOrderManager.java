package com.bird.cloud.seata.example.order.server.manager;

import com.bird.cloud.common.annotation.Manager;
import com.bird.cloud.seata.example.order.server.dao.MallOrderMapper;
import com.bird.cloud.seata.example.order.server.entity.MallOrder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-16 00:01
 **/

@Manager
public class MallOrderManager {


    @Autowired
    private MallOrderMapper orderMapper;

    private void add(MallOrder order){
        orderMapper.insert(order);
    }
}
