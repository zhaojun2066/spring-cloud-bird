package com.bird.cloud.example.student.server.fallback;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.example.course.dto.CourseDTO;
import com.bird.cloud.example.student.server.client.CourseClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-07 12:12
 **/
@Component
public class CourseFallback implements CourseClient {
    @Override
    public Response<List<CourseDTO>> getList(int page) {
        return null;
    }

    @Override
    public Response<CourseDTO> getOne(Long id) {
        System.out.println("======================================");
        return null;
    }

    @Override
    public Response<Boolean> update(CourseDTO courseDto) {
        return null;
    }

    @Override
    public Response<Boolean> updatePart(CourseDTO courseDto) {
        return null;
    }

    @Override
    public Response<Boolean> add(CourseDTO courseDto) {
        return null;
    }

    @Override
    public Response<Boolean> delete(Long id) {
        return null;
    }
}
