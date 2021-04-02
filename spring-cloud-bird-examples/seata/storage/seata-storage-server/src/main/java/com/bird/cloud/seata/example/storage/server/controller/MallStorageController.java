package com.bird.cloud.seata.example.storage.server.controller;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.seata.example.storage.api.StorageApi;
import com.bird.cloud.seata.example.storage.query.DeductionStorageQuery;
import com.bird.cloud.seata.example.storage.server.service.MallStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-17 18:16
 **/
@RestController
public class MallStorageController implements StorageApi {

    @Autowired
    private MallStorageService storageService;

    @Override
    public Response<Boolean> deductionStorage(@RequestBody DeductionStorageQuery deductionStorageQuery) {
        try {
            storageService.updateCount(deductionStorageQuery);
        }catch (Exception e){
            e.printStackTrace();
            return Response.ok(true);
        }
        return Response.ok(true);
    }
}
