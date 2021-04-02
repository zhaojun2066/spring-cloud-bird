package com.bird.cloud.seata.example.storage.api;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.seata.example.storage.query.DeductionStorageQuery;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-15 22:49
 **/
public interface StorageApi {

    String root_path = "/api-storage";

    @RequestMapping(method = RequestMethod.POST,value = root_path)
    Response<Boolean> deductionStorage(@RequestBody DeductionStorageQuery deductionStorageQuery);


}
