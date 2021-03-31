package com.bird.cloud.example.course.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-23 18:01
 **/
@Data
public class CourseDTO {
    private Long id;// 主键id
    private String name; // 课程名字
    private String teacher;// 教师
    private LocalDateTime startDate;//开始时间
    private LocalDateTime endDate;//结束时间
}
