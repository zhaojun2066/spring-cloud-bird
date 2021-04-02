package com.bird.cloud.seata.example.storage.server.service;

import com.bird.cloud.seata.example.storage.query.DeductionStorageQuery;
import com.bird.cloud.seata.example.storage.server.dao.MallStorageMapper;
import com.bird.cloud.seata.example.storage.server.entity.MallStorage;
import com.bird.cloud.seata.example.storage.server.entity.MallStorageExample;
import com.bird.cloud.seata.example.storage.server.manager.MallStorageManager;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-17 18:17
 **/
@Service
@Transactional
public class MallStorageService {

    @Autowired
    private MallStorageMapper storageMapper;

    @Autowired
    private MallStorageManager storageManager;

    private MallStorageManager storageManager2;

    @Transactional
    public void  updateCount(DeductionStorageQuery deductionStorageQuery){
        System.out.println("XID==>" + RootContext.getXID());
        storageMapper.updateByExampleSelective(MallStorage.builder().count(deductionStorageQuery.getCount()).build(),new MallStorageExample().createCriteria().andCommodityCodeEqualTo(deductionStorageQuery.getCommodityCode()).example());
        storageManager2.hashCode();
    }
}
