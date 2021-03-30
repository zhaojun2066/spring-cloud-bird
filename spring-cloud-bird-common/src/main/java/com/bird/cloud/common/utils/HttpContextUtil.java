package com.bird.cloud.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: ce-microservice
 * @description: request response  session operations
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-10 23:03
 **/
public class HttpContextUtil {

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }

    public static HttpSession getSession() {
        return getHttpServletRequest().getSession();
    }

    public static String getPara(String name) {
        return getHttpServletRequest().getParameter(name);
    }

    public static void setAttr(String name, Object value) {
        getHttpServletRequest().setAttribute(name, value);
    }
}
