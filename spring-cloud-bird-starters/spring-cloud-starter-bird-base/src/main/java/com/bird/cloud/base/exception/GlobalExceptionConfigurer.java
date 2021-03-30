package com.bird.cloud.base.exception;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.common.dto.ResponseCode;
import com.bird.cloud.common.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-09 21:34
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionConfigurer {
    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class,
            BindException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public Response handleParamsException(HttpServletRequest request,
                                          Exception ex) {

        String msg = "";
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException t = (MethodArgumentNotValidException) ex;
            msg = getBindingResultMsg(t.getBindingResult());
        } else if (ex instanceof BindException) {
            BindException t = (BindException) ex;
            msg = getBindingResultMsg(t.getBindingResult());
        } else if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException t = (ConstraintViolationException) ex;
            msg = t.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(","));
        } else if (ex instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException t = (MissingServletRequestParameterException) ex;
            msg = t.getParameterName() + " 不能为空";
        } else if (ex instanceof MissingPathVariableException) {
            MissingPathVariableException t = (MissingPathVariableException) ex;
            msg = t.getVariableName() + " 不能为空";
        }/*else if (e instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException t = (HttpMessageNotReadableException) e;
            msg =  t.getMessage();
        } */else {
            msg = "必填参数缺失";
        }
        log(request, ex);
        return new Response<>(HttpStatus.OK, ResponseCode.SYS0002, null, msg);
    }

    private String getBindingResultMsg(BindingResult result) {
        return result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Response<?> handleBusinessException(HttpServletRequest request,

                                            BusinessException ex) {

        log(request, ex);
        if (ex.getResponse()!=null){
            return  ex.getResponse();
        }
        return new Response(HttpStatus.OK, ResponseCode.SYS0002,null, ex.getMessage());
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Response NoHandlerFoundException(HttpServletRequest request,
                                            NoHandlerFoundException ex) {
        log(request, ex);
        return new Response<>(HttpStatus.NOT_FOUND, ResponseCode.SYS0000,null, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public Response handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                                 HttpRequestMethodNotSupportedException ex) {
        log(request, ex);
        return new Response<>(HttpStatus.METHOD_NOT_ALLOWED, ResponseCode.SYS0002, null, ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleAllException(HttpServletRequest request,
                                       Exception ex) {
        log(request, ex);
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.SYS0001, null,ex.getMessage());
    }


    private void log(HttpServletRequest request,
                     Exception ex){
        log.error("  request method=" + request.getMethod() +
                "  url=" + request.getRequestURL() +
                "  request params=" +
                JSON.toJSONString(request.getParameterMap(), SerializerFeature.WriteMapNullValue,
                        SerializerFeature.DisableCircularReferenceDetect), ex);
    }
}
