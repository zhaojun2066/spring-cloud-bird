package com.bird.cloud.seata.example.business.server.controller;

import com.bird.cloud.seata.example.business.server.client.OrderClient;
import com.bird.cloud.seata.example.business.server.client.StorageClient;
import com.bird.cloud.seata.example.order.query.AddOrderQuery;
import com.bird.cloud.seata.example.storage.query.DeductionStorageQuery;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-17 18:31
 **/
@RestController
public class BusinessController {


    @Autowired
    private OrderClient orderClient;

    @Autowired
    private StorageClient storageClient;

    @GlobalTransactional(name = "tx_create_order",timeoutMills = 6000000,rollbackFor = Exception.class)
    @GetMapping("/test")
    public String test(){
        AddOrderQuery query = new AddOrderQuery();
        query.setCommodityCode("1000");
        query.setCount(10);
        query.setMoney(100);
        query.setUserId("1");
        orderClient.add(query);
        storageClient.deductionStorage(getDeductionStorageQuery(query));
        return  "success";
    }

    private DeductionStorageQuery getDeductionStorageQuery(AddOrderQuery query){
        DeductionStorageQuery q = new DeductionStorageQuery();
        q.setCommodityCode(query.getCommodityCode());
        q.setCount(query.getCount());
        return q;
    }
}
