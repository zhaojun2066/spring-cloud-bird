package com.bird.cloud.base.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-10 22:56
 **/
public class DefaultCeErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String,Object> map = new HashMap<>();
        map.put("status",200);
        map.put("code","SYS0001");
        map.put("msg","系统错误");
        map.put("data",new JSONObject());
        return  map;
    }
}
