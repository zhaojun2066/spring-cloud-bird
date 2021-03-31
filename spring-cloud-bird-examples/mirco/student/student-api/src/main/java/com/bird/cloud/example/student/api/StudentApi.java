package com.bird.cloud.example.student.api;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.example.course.dto.CourseDTO;
import com.bird.cloud.example.student.dto.StudentDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-24 09:37
 **/
public interface StudentApi {

    String root_path = "/api-students";

    @RequestMapping(method = RequestMethod.GET,value = root_path)
    Response<List<StudentDTO>> getAll();

    @RequestMapping(method = RequestMethod.GET,value = root_path+"/{id}")
    Response<StudentDTO> getOne(@PathVariable(value = "id") Long id);

    @RequestMapping(method = RequestMethod.PUT,value = root_path)
    Response<Boolean> update(@RequestBody StudentDTO studentDto);

    // 更新部分字段
    @RequestMapping(method = RequestMethod.PATCH,value = root_path)
    Response<Boolean> updatePart(@RequestBody StudentDTO studentDto);

    @RequestMapping(method = RequestMethod.POST,value = root_path)
    Response<Boolean> add(@RequestBody StudentDTO studentDto);

    @RequestMapping(method = RequestMethod.DELETE,value = root_path+"/{id}")
    Response<Boolean> delete(@PathVariable(value = "id") Long id);

    @RequestMapping(method = RequestMethod.DELETE,value = root_path+"/{id}/courses")
    Response<Boolean> deleteCourses(@PathVariable(value = "id") Long id);

    @RequestMapping(method = RequestMethod.GET,value = root_path+"/{id}/courses")
    Response<List<CourseDTO>> getCourse(@PathVariable(value = "id") Long id);

    @RequestMapping(method = RequestMethod.POST,value = root_path+"/{id}/courses")
    Response<Boolean> addCoureses(@PathVariable(value = "id") Long id, @RequestBody CourseDTO courseDto);

}
