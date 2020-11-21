package com.fatehole.eduservice.service;

import com.fatehole.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fatehole.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程信息表 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseInfoVo 封装的表单对象
     * @return 新生成的课程id
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);
}
