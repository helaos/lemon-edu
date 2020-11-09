package com.fatehole.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fatehole.eduservice.entity.vo.TeacherQuery;

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

}
