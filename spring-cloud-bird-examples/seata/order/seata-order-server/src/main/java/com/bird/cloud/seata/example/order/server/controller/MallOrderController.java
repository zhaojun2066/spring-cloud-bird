package com.bird.cloud.seata.example.order.server.controller;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.seata.example.order.api.OrderApi;
import com.bird.cloud.seata.example.order.query.AddOrderQuery;
import com.bird.cloud.seata.example.order.server.service.MallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-16 00:12
 **/

@RestController
public class MallOrderController implements OrderApi {

    @Autowired
    private MallOrderService orderService;

    @Override
    public Response<Boolean> add(@RequestBody AddOrderQuery addOrderQuery) {
        try {
            orderService.addOrder(addOrderQuery);
        }catch (Exception e){
            e.printStackTrace();
            return Response.ok(false);
        }
        return Response.ok(true);
    }
}
