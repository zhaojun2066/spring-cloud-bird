package com.bird.cloud.common.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-09 21:36
 **/
@Data
public class Response<T> {
    private int status;  // http status
    private String code; // response code
    private String msg; // response message
    private T data; // response data

    public Response(){}

    public Response(HttpStatus status, ResponseCode resultCode, T data, String msg) {
        this.status = status.value();
        this.code = resultCode.getCode();
        this.msg = msg;
        this.data = data;
    }

    public Response(HttpStatus status, ResponseCode resultCode, T data) {
        this.status = status.value();
        this.code = resultCode.getCode();
        this.msg = resultCode.getDesc();
        this.data = data;
    }

    public Response(T data) {
        this.status = 200;
        this.code = ResponseCode.SYS0000.getCode();
        this.msg = ResponseCode.SYS0000.getDesc();
        this.data = data;
    }


    public static <T> Response<T> ok(T data){
        Response response = new Response();
        response.setCode(ResponseCode.SYS0000.getCode());
        response.setMsg(ResponseCode.SYS0000.getDesc());
        response.setStatus(200);
        response.setData(data);
        return  response;
    }

    public static Response  me(HttpStatus status, ResponseCode resultCode, Object data){
        Response response = new Response();
        response.setCode(resultCode.getCode());
        response.setMsg(resultCode.getDesc());
        response.setStatus(status.value());
        response.setData(data);
        return response;
    }

    public static Response me(HttpStatus status, String  code, String msg, Object data){
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        response.setStatus(status.value());
        response.setData(data);
        return response;
    }
}
