package com.bird.cloud.discover.feign;

import com.bird.cloud.common.utils.HttpContextUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-10 23:18
 **/
@Slf4j
public class FeignHeaderProcessInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {

        //当前feign远程调用环境不是由http接口发起，例如test单元测试中的feign调用或者项目启动后的feign调用
        HttpServletRequest request = null;
        try {
            request = HttpContextUtil.getHttpServletRequest();
        } catch (NullPointerException e) {
            log.info("=====header err");
            //被调环境中不存在request对象，则不往header里添加当前请求环境的header
            return;
        }
        if (request != null) {
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }
        }
    }

}
