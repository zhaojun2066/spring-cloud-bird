package com.bird.cloud.example.student.server.service;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.common.dto.ResponseCode;
import com.bird.cloud.example.course.dto.CourseDTO;
import com.bird.cloud.example.student.dto.StudentDTO;
import com.bird.cloud.example.student.server.client.CourseClient;
import com.bird.cloud.example.student.server.converters.StudentConverter;
import com.bird.cloud.example.student.server.dao.StudentCourseMapper;
import com.bird.cloud.example.student.server.dao.StudentMapper;
import com.bird.cloud.example.student.server.entity.Student;
import com.bird.cloud.example.student.server.entity.StudentCourse;
import com.bird.cloud.example.student.server.entity.StudentCourseExample;
import com.bird.cloud.example.student.server.entity.StudentExample;
import com.bird.cloud.example.student.server.manager.StudentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-24 09:54
 **/
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private StudentConverter studentConverter;

    @Autowired
    private CourseClient courseConsumer;

    @Autowired
    private StudentManager studentManager;
   
    public List<StudentDTO> getAll() {
        List<Student> students = studentMapper.selectByExample(new StudentExample().createCriteria().andDeletedEqualTo(false).example());
        return studentConverter.totoStudentDtoList(students);
    }

   
    public StudentDTO getOne(Long id) {
        return studentManager.getOne(id);
    }

   
    public Boolean update(StudentDTO studentDto) {
        return studentManager.update(studentDto);
    }

   
    public Boolean updatePart(StudentDTO studentDto) {
        int rows = studentMapper.updateByPrimaryKeySelective(studentConverter.toStudent(studentDto));
        if(rows>0) return true;
        return false;
    }

   
    public Boolean add(StudentDTO studentDto) {
        return studentManager.add(studentDto);
    }

   
    public Boolean delete(Long id) {
        return studentManager.delete(id);
    }

   
    public Boolean deleteCourses(Long studentId) {
        int rows = studentCourseMapper.logicalDeleteByExample(new StudentCourseExample().createCriteria()
                .andStudentIdEqualTo(studentId).example());
        if (rows>0) {
            return  true;
        }

        return  false;
    }

   
    public List<CourseDTO> getCourse(Long id) {
        List<CourseDTO> resultList = new ArrayList<>(20);
        List<StudentCourse> studentCourseList =studentCourseMapper.selectByExample(
                new StudentCourseExample().createCriteria()
                .andStudentIdEqualTo(id).andDeletedEqualTo(false)
                .example()
        );
        if (studentCourseList!=null && !studentCourseList.isEmpty()){
            for (StudentCourse s: studentCourseList){
               Response<CourseDTO> courseDtoResponse = courseConsumer.getOne(s.getCourseId());
               if (courseDtoResponse!=null && courseDtoResponse.getStatus()==200 && courseDtoResponse.getCode().equals(ResponseCode.SYS0000.getCode())){
                   resultList.add(courseDtoResponse.getData());
               }
            }
        }
        return resultList;
    }

   
    public Boolean addCoureses(Long studentId, CourseDTO courseDto) {
        StudentDTO studentDto = this.getOne(studentId);
        if (studentDto==null){
            return false;
        }
        Long courseId = courseDto.getId();
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId(courseId);
        studentCourse.setStudentId(studentId);
        studentCourse.setDeleted(false);
        studentCourse.setCreateTime(LocalDateTime.now());
        studentCourse.setUpdatedTime(LocalDateTime.now());
        studentCourseMapper.insert(studentCourse);
        return true;
    }
}
