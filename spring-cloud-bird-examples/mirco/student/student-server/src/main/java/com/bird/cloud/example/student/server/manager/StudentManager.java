package com.bird.cloud.example.student.server.manager;

import com.bird.cloud.example.student.dto.StudentDTO;
import com.bird.cloud.example.student.server.converters.StudentConverter;
import com.bird.cloud.example.student.server.dao.StudentCourseMapper;
import com.bird.cloud.example.student.server.dao.StudentMapper;
import com.bird.cloud.example.student.server.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: service-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-05 11:27
 **/

@Component
public class StudentManager {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private StudentConverter studentConverter;

    public StudentDTO getOne(Long id) {
        Student student = studentMapper.selectByPrimaryKey(id);
        return studentConverter.toStudentDto(student);
    }


    public Boolean update(StudentDTO studentDto) {
        int rows = studentMapper.updateByPrimaryKey(studentConverter.toStudent(studentDto));
        if(rows>0) return true;
        return false;
    }


    public Boolean updatePart(StudentDTO studentDto) {
        int rows = studentMapper.updateByPrimaryKeySelective(studentConverter.toStudent(studentDto));
        if(rows>0) return true;
        return false;
    }


    public Boolean add(StudentDTO studentDto) {
        int rows = studentMapper.insert(studentConverter.toCourseWithTime(studentDto,false));
        if(rows>0) return true;
        return false;
    }


    public Boolean delete(Long id) {
        int rows = studentMapper.logicalDeleteByPrimaryKey(id);
        if(rows>0) return true;
        return false;
    }

}
