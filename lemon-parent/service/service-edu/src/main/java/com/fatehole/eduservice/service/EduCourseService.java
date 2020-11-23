package com.fatehole.eduservice.service;

import com.fatehole.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fatehole.eduservice.entity.vo.CourseInfoVo;
import com.fatehole.eduservice.entity.vo.CoursePublishVo;

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

    /**
     * 根域课程ID查询课程的基本信息
     * @param courseId 课程ID
     * @return 返回基本信息
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 根据课程信息修改课程信息
     * @param courseInfoVo 课程信息
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据ID查询课程确认信息
     * @param id 课程ID
     * @return 封装的课程确认信息
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 根据课程ID修改课程状态
     * @param id 课程ID
     * @return 是否成功
     */
    boolean publishCourse(String id);
}
