package com.bird.cloud.seata.example.order.server.converters;

import com.bird.cloud.seata.example.order.query.AddOrderQuery;
import com.bird.cloud.seata.example.order.server.entity.MallOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-23 21:23
 **/
@Mapper(componentModel = "spring")
public abstract class MallOrderConverter {
    public static MallOrderConverter MAPPER = Mappers.getMapper(MallOrderConverter.class);


    public abstract MallOrder toOrder(AddOrderQuery addOrderQuery);

}
