package com.fatehole.eduservice.service.impl;

import com.fatehole.eduservice.entity.EduCourse;
import com.fatehole.eduservice.entity.EduCourseDescription;
import com.fatehole.eduservice.entity.vo.CourseInfoVo;
import com.fatehole.eduservice.entity.vo.CoursePublishVo;
import com.fatehole.eduservice.mapper.EduCourseMapper;
import com.fatehole.eduservice.service.EduCourseDescriptionService;
import com.fatehole.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fatehole.servicebase.exception.LemonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = LemonException.class)
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

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        // 查询课程表
        EduCourse course = baseMapper.selectById(courseId);

        if (course == null) {
            throw new LemonException(20001, "数据不存在");
        }

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);

        // 查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);

        // 判断是否有值
        if (courseDescription != null) {
            courseInfoVo.setDescription(courseDescription.getDescription());
        }

        return courseInfoVo;
    }

    @Override
    @Transactional(rollbackFor = LemonException.class)
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程表
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);

        int row = baseMapper.updateById(course);

        if (row == 0) {
            throw new LemonException(20001, "修改课程信息失败");
        }

        // 修改描述信息表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId()).setDescription(courseInfoVo.getDescription());
        boolean flag = courseDescriptionService.updateById(courseDescription);

        if (!flag) {
            throw new LemonException(20001, "课程详情信息保存失败");
        }
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {

        // 调用mapper
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourse(String id) {

        EduCourse course = new EduCourse();

        // 修改课程状态
        course.setId(id).setStatus(EduCourse.COURSE_NORMAL);

        int row = baseMapper.updateById(course);

        return row > 0;
    }
}
