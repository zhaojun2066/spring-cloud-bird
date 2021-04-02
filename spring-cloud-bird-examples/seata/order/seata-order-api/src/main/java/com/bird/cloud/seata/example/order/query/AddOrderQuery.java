package com.bird.cloud.seata.example.order.query;

import lombok.Data;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-15 22:59
 **/
@Data
public class AddOrderQuery {
    private String userId;
    private String commodityCode;
    private int count;
    private int money;
}
