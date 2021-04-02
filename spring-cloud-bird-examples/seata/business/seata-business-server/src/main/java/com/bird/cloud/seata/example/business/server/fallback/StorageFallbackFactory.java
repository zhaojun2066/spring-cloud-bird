package com.bird.cloud.seata.example.business.server.fallback;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.seata.example.business.server.client.StorageClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-07 16:26
 **/

@Component
public class StorageFallbackFactory implements FallbackFactory<StorageClient> {
    @Override
    public StorageClient create(Throwable throwable) {
        throwable.printStackTrace();
        System.out.println("=================》fallback reason was:  " + throwable.getMessage());
        return deductionStorageQuery -> Response.ok(true);
    }
}
