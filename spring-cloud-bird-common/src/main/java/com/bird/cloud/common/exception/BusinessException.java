package com.bird.cloud.common.exception;


import com.bird.cloud.common.dto.Response;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-09 21:33
 **/
public class BusinessException extends RuntimeException {


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }



   private Response response;

    public BusinessException(String message,Response response) {
        super(message);
        this.response = response;
    }

    public BusinessException(String message, Throwable cause,Response response) {
        super(message, cause);
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
