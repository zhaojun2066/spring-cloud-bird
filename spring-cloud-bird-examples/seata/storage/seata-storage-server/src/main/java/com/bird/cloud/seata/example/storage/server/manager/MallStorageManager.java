package com.bird.cloud.seata.example.storage.server.manager;


import com.bird.cloud.common.annotation.Manager;
import com.bird.cloud.seata.example.storage.server.dao.MallStorageMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-17 18:18
 **/
@Manager
public class MallStorageManager {
    @Autowired
    private MallStorageMapper storageMapper;
}
