package com.bird.cloud.mq.rocketmq.example.domain;

import lombok.Data;

import java.time.LocalDate;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-13 09:03
 **/

@Data
public class User {
    private int id;
    private String name;
    private int age;
    private LocalDate birthday;
    private String address;
}
