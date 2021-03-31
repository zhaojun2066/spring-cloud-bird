package com.bird.cloud.example.student.server.converters;

import com.bird.cloud.example.student.dto.StudentDTO;
import com.bird.cloud.example.student.server.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

import static org.mapstruct.factory.Mappers.*;
import static org.mapstruct.factory.Mappers.getMapper;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-23 21:23
 **/
@Mapper(componentModel = "spring")
public abstract class StudentConverter {
    public static StudentConverter MAPPER = getMapper(StudentConverter.class);

    //@Mappings({@Mapping(),@Mapping()}) 多个属性
    // 属性不同才会写 映射关系，
    public abstract StudentDTO toStudentDto(Student student);


    public abstract Student toStudent(StudentDTO studentDto);

    @Mapping(target = "updatedTime", expression = "java(java.time.LocalDateTime.now())")
    public abstract Student totoStudentWithUpdated(StudentDTO studentDto);

    //@InheritInverseConfiguration
    public abstract List<StudentDTO> totoStudentDtoList(List<Student> students);


    public Student toCourseWithTime(StudentDTO studentDto, boolean deleted){
        Student student = toStudent(studentDto);
        if (student!=null){
            student.setDeleted(deleted);
            student.setUpdatedTime(LocalDateTime.now());
            student.setCreateTime(LocalDateTime.now());
        }
        return student;
    }
}
