package com.fatehole.eduservice.service.impl;

import com.fatehole.eduservice.entity.EduCourse;
import com.fatehole.eduservice.entity.EduCourseDescription;
import com.fatehole.eduservice.entity.vo.CourseInfoVo;
import com.fatehole.eduservice.mapper.EduCourseMapper;
import com.fatehole.eduservice.service.EduCourseDescriptionService;
import com.fatehole.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fatehole.servicebase.exception.LemonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程信息表 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    public void setCourseDescriptionService(EduCourseDescriptionService courseDescriptionService) {
        this.courseDescriptionService = courseDescriptionService;
    }

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 向课程表添加课程的基本信息

        // 将CourseInfoVo对象转换为eduCourse对象
        EduCourse course = new EduCourse();
        course.setStatus(EduCourse.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoVo, course);
        boolean resultCourseInfo = this.save(course);

        if(!resultCourseInfo){
            // 失败
            throw new LemonException(20001, "添加课程信息失败!");
        }

        // 像课程简介表添加课程简介信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, courseDescription);
        courseDescription.setId(course.getId());
        boolean resultDescription = courseDescriptionService.save(courseDescription);

        if (!resultDescription) {
            throw new LemonException(20001, "课程详情信息保存失败!");
        }

        return course.getId();
    }
}
