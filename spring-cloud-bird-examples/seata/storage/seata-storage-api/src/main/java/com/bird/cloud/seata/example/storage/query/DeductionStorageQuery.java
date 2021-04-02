package com.bird.cloud.seata.example.storage.query;

import lombok.Data;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-17 17:49
 **/
@Data
public class DeductionStorageQuery {
    private String commodityCode;
    private int count;
}
