package com.fatehole.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fatehole.eduservice.entity.vo.CourseInfoVo;
import com.fatehole.eduservice.entity.vo.CoursePublishVo;
import com.fatehole.eduservice.entity.vo.CourseQuery;

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

    /**
     * 根据具体的条件和信息分页查询
     * @param coursePage 分页信息
     * @param courseQuery 查询条件
     */
    void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery);

    /**
     * 根据课程ID删除课程
     * @param id 课程ID
     * @return 是与否
     */
    boolean removeCourseById(String id);
}
