package com.bird.cloud.example.student.server.client;

import com.bird.cloud.example.course.api.CourseApi;
import com.bird.cloud.example.student.server.fallback.CourseFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-24 10:32
 **/
@FeignClient(value = "course-server"/*,fallback = CourseFallback.class*/,fallbackFactory = CourseFallbackFactory.class)
public interface CourseClient extends CourseApi {}
