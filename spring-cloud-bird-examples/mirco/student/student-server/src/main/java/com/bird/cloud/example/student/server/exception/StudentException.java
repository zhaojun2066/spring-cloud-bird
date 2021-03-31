package com.bird.cloud.example.student.server.exception;


import com.bird.cloud.common.exception.BusinessException;

/**
 * @program: spring-cloud-ce
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-11 15:14
 **/
public class StudentException  extends BusinessException {
    public StudentException(String message) {
        super(message);
    }
}
