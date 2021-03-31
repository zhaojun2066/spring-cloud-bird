package com.bird.cloud.example.course.api;

import com.bird.cloud.common.dto.Response;
import com.bird.cloud.example.course.dto.CourseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @program: spring-cloud-bird
 * @description: restful 风格
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-23 18:00
 **/
public interface CourseApi {
    String root_path = "/api-courses";

    /***
     * 获取全部
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = root_path)
    Response<List<CourseDTO>> getList(int page);

    /**
     * 根据id 获取详情
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = root_path+"/{id}")
    Response<CourseDTO> getOne(@PathVariable(value = "id") Long id);


    /**
     * 更新
     * @param courseDto
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = root_path)
    Response<Boolean> update(@RequestBody CourseDTO courseDto);

    // 更新部分字段
    @RequestMapping(method = RequestMethod.PATCH,value = root_path)
    Response<Boolean> updatePart(@RequestBody CourseDTO courseDto);

    /**
     * 添加
     * @param courseDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = root_path)
    Response<Boolean> add(@RequestBody CourseDTO courseDto);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,value = root_path+"/{id}")
    Response<Boolean> delete(@PathVariable(value = "id") Long id);


}
