package com.bird.cloud.example.course.server.manager;

import com.bird.cloud.example.course.dto.CourseDTO;
import com.bird.cloud.example.course.server.converters.CourseConverter;
import com.bird.cloud.example.course.server.dao.CourseMapper;
import com.bird.cloud.example.course.server.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: service-example
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-05 10:23
 **/

@Component
public class CourseManager {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseConverter courseConverter;


    public CourseDTO getOne(Long id) {
        Course course =  courseMapper.selectByPrimaryKey(id);
        return courseConverter.courseDto(course);
    }

    public Boolean update(CourseDTO courseDto) {
        Course course = courseConverter.toCourseWithUpdated(courseDto);
        if (course!=null){
            int rows = courseMapper.updateByPrimaryKeySelective(course);
            if (rows>0){
                return  true;
            }
        }
        return false;
    }


    public Boolean add(CourseDTO courseDto) {
        Course course = courseConverter.toCourseWithTime(courseDto,false);
        if (course!= null){
            int rows = courseMapper.insert(course);
            if (rows>0){
                return  true;
            }
        }
        return false;
    }

    public Boolean delete(Long id) {
        int rows =  courseMapper.logicalDeleteByPrimaryKey(id);
        if (rows>0){
            return  true;
        }
        return false;
    }
}
