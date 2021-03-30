package com.bird.cloud.seata.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.common.util.StringUtils;
import io.seata.core.context.RootContext;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-17 16:30
 **/
public class SeataRequestHeaderInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String xid = RootContext.getXID();
        System.out.println("xid============>" + xid);
        if (StringUtils.isNotBlank(xid)) {
            template.header(RootContext.KEY_XID, xid);
        }
    }
}
