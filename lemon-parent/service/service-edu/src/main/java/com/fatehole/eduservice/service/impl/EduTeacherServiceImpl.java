package com.fatehole.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.eduservice.entity.EduTeacher;
import com.fatehole.eduservice.entity.vo.TeacherQuery;
import com.fatehole.eduservice.mapper.EduTeacherMapper;
import com.fatehole.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-11-08
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        // 构造条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        // 排序
        wrapper.orderByAsc("sort");

        if (teacherQuery == null){
            baseMapper.selectPage(pageParam, wrapper);
            return;
        }

        // 获取条件参数
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // 多条件组合查询
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("create_time", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            wrapper.le("create_time", end);
        }

        baseMapper.selectPage(pageParam, wrapper);
    }

    @Cacheable(key = "'IndexTeacher'", value = "teacher")
    @Override
    public List<EduTeacher> selectList(Wrapper<EduTeacher> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }
}
