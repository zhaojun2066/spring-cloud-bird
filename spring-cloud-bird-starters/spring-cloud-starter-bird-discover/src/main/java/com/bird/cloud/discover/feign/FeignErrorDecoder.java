package com.bird.cloud.discover.feign;

import com.bird.cloud.common.dto.ResponseCode;
import com.bird.cloud.common.exception.BusinessException;
import feign.Response;
import feign.codec.ErrorDecoder;


/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-10 23:23
 **/
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response != null && response.status() == 404) {
            return new BusinessException("404 not found!");
        } else {
            return new BusinessException(ResponseCode.SYS0002.getDesc());
        }
    }

}
