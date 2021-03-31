package com.bird.cloud.example.student.server.fallback;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.common.dto.ResponseCode;
import com.bird.cloud.example.course.dto.CourseDTO;
import com.bird.cloud.example.student.server.client.CourseClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: spring-cloud-ce
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-07 16:26
 **/

@Component
public class CourseFallbackFactory implements FallbackFactory<CourseClient> {
    @Override
    public CourseClient create(Throwable throwable) {
        throwable.printStackTrace();
        System.out.println("=================》fallback reason was:  " + throwable.getMessage());
        return new CourseClient() {
            @Override
            public Response<List<CourseDTO>> getList(int page) {
                return null;
            }

            @Override
            public Response<CourseDTO> getOne(Long id) {
                Response<CourseDTO> re = new Response();
                re.setCode(ResponseCode.SYS0000.getCode());
                return re;
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
        };
    }
}
