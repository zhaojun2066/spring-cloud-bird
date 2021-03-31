package com.bird.cloud.example.course.server.controller;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.example.course.api.CourseApi;
import com.bird.cloud.example.course.dto.CourseDTO;
import com.bird.cloud.example.course.server.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-23 19:51
 **/
@Slf4j
@RestController
public class CourseController implements CourseApi {

    @Autowired
    private CourseService courseService;


    @Override
    public Response<List<CourseDTO>> getList(int page) {
        return Response.ok(courseService.getList(page));
    }

    @Override
    public Response<CourseDTO> getOne(@PathVariable(name = "id") Long id) {
        return Response.ok(courseService.getOne(id));
    }

    @Override
    public Response<Boolean> update(@RequestBody CourseDTO courseDto) {
        return Response.ok(courseService.update(courseDto));
    }

    @Override
    public Response<Boolean> updatePart(@RequestBody CourseDTO courseDto) {
        return Response.ok(courseService.updatePart(courseDto));
    }

    @Override
    public Response<Boolean> add(@RequestBody CourseDTO courseDto) {
        return Response.ok(courseService.add(courseDto));
    }

    @Override
    public Response<Boolean> delete(@PathVariable(name = "id")Long id) {
        return Response.ok(courseService.delete(id));
    }
}
