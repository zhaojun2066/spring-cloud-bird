package com.bird.cloud.example.student.server.controller;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.example.course.dto.CourseDTO;
import com.bird.cloud.example.student.api.StudentApi;
import com.bird.cloud.example.student.dto.StudentDTO;
import com.bird.cloud.example.student.server.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-24 09:55
 **/
@RestController
public class StudentController implements StudentApi {

    @Autowired
    private StudentService studentService;

    @Override
    public Response<List<StudentDTO>> getAll() {
        return Response.ok(studentService.getAll());
    }

    @Override
    public Response<StudentDTO> getOne(@PathVariable(value = "id") Long id) {
        return Response.ok(studentService.getOne(id));
    }

    @Override
    public Response<Boolean> update(@RequestBody StudentDTO studentDto) {
        return Response.ok(studentService.update(studentDto));
    }

    @Override
    public Response<Boolean> updatePart(@RequestBody StudentDTO studentDto) {
        return Response.ok(studentService.updatePart(studentDto));
    }

    @Override
    public Response<Boolean> add(@RequestBody StudentDTO studentDto) {
        return Response.ok(studentService.add(studentDto));
    }

    @Override
    public Response<Boolean> delete(@PathVariable(value = "id") Long id) {
        return Response.ok(studentService.delete(id));
    }

    @Override
    public Response<Boolean> deleteCourses(@PathVariable(value = "id") Long id) {
        return Response.ok(studentService.deleteCourses(id));
    }

    @Override
    public Response<List<CourseDTO>> getCourse(@PathVariable(value = "id") Long id) {
        return Response.ok(studentService.getCourse(id));
    }

    @Override
    public Response<Boolean> addCoureses(@PathVariable(value = "id") Long id, @RequestBody CourseDTO courseDto) {
        return Response.ok(studentService.addCoureses(id,courseDto));
    }
}
