package com.bird.cloud.example.student.dto;

import com.bird.cloud.example.course.dto.CourseDTO;
import lombok.Data;

import java.util.List;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-24 09:38
 **/
@Data
public class StudentDTO {
    private Long id;
    private String name;
    private int age;
    private List<CourseDTO> courseDtoList;
}
