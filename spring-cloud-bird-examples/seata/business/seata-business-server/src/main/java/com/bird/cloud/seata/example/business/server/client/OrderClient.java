package com.bird.cloud.seata.example.business.server.client;

import com.bird.cloud.seata.example.order.api.OrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-17 18:39
 **/
@FeignClient(value = "seata-order-server")
public interface OrderClient extends OrderApi {}
