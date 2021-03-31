package com.bird.cloud.example.course.server.converters;

import com.bird.cloud.example.course.dto.CourseDTO;
import com.bird.cloud.example.course.server.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-23 21:23
 **/
@Mapper(componentModel = "spring")
public abstract class CourseConverter {
    public static CourseConverter MAPPER = Mappers.getMapper(CourseConverter.class);

    //@Mappings({@Mapping(),@Mapping()}) 多个属性
    // 属性不同才会写 映射关系，
    public abstract CourseDTO courseDto(Course course);

    public abstract Course toCourse(CourseDTO courseDto);

    @Mapping(target = "updatedTime", expression = "java(java.time.LocalDateTime.now())")
    public abstract Course toCourseWithUpdated(CourseDTO courseDto);

    //@InheritInverseConfiguration
    public abstract List<CourseDTO> toCourseDtoList(List<Course> courses);


    public Course toCourseWithTime(CourseDTO courseDto, boolean deleted){
        Course course = toCourse(courseDto);
        if (course!=null){
            course.setDeleted(deleted);
            course.setUpdatedTime(LocalDateTime.now());
            course.setCreateTime(LocalDateTime.now());

        }
        return course;
    }
}
