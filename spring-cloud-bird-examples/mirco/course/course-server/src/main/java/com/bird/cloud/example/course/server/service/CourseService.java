package com.bird.cloud.example.course.server.service;

import com.bird.cloud.example.course.dto.CourseDTO;
import com.bird.cloud.example.course.server.converters.CourseConverter;
import com.bird.cloud.example.course.server.dao.CourseMapper;
import com.bird.cloud.example.course.server.entity.Course;
import com.bird.cloud.example.course.server.entity.CourseExample;
import com.bird.cloud.example.course.server.manager.CourseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-23 20:16
 **/
@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseManager courseManager;

    @Autowired
    private CourseConverter courseConverter;

    public List<CourseDTO> getList(int page) {

        if (page==0) page = 1;
        CourseExample example  = new CourseExample().createCriteria()
                .andDeletedEqualTo(false)
                .example().page(page,2);
        List<Course>  courseList =  courseMapper.selectByExample(
                example);
        return courseConverter.toCourseDtoList(courseList);
    }

    public CourseDTO getOne(Long id) {
        return  courseManager.getOne(id);
    }

    public Boolean update(CourseDTO courseDto) {
        return courseManager.update(courseDto);
    }

    public Boolean updatePart(CourseDTO courseDto) {
        Course course = courseConverter.toCourse(courseDto);
        if (course!=null){
            int rows = courseMapper.updateByPrimaryKeySelective(course);
            if (rows>0){
                return  true;
            }
        }
        return false;
    }

    public Boolean add(CourseDTO courseDto) {
        return courseManager.add(courseDto);
    }

    public Boolean delete(Long id) {
        return courseManager.delete(id);
    }
}
