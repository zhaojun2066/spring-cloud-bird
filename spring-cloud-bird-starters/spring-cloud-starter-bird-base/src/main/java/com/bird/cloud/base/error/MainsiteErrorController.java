package com.bird.cloud.base.error;


import com.alibaba.fastjson.JSONArray;
import com.bird.cloud.common.dto.Response;
import com.bird.cloud.common.dto.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: spring-boot-example
 * @description:  改变 SpringBoot的默认异常处理映射为“/error”
 * @author: JuFeng(ZhaoJun)
 * @create: 2019-08-26 14:06
 **/
@Controller
public class MainsiteErrorController implements ErrorController{

    @Autowired
    private ErrorAttributes errorAttributes;

    /**
     * JSON格式错误信息
     */
    @RequestMapping(value = "/error")
    @ResponseBody
    public Response error(HttpServletRequest request, HttpServletResponse response) {
        return new Response(HttpStatus.NOT_FOUND, ResponseCode.SYS0001, new JSONArray(),"not found");
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}
