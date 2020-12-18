package com.fatehole.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.eduservice.entity.EduCourse;
import com.fatehole.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fatehole.eduservice.entity.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-11-08
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 根据具体的条件和信息分页查询
     * @param pageParam 分页信息
     * @param teacherQuery 查询条件
     */
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    /**
     * 根据条件查询讲师数据
     * @param queryWrapper 条件集
     * @return 包含数据的结果集
     */
    List<EduTeacher> selectList(Wrapper<EduTeacher> queryWrapper);

    /**
     * 前台页面的教师分页方法
     * @param teacherPage 分页对象
     * @return map集合
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
