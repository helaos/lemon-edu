package com.fatehole.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.eduservice.entity.EduCourse;
import com.fatehole.eduservice.entity.EduCourseDescription;
import com.fatehole.eduservice.entity.vo.*;
import com.fatehole.eduservice.mapper.EduCourseMapper;
import com.fatehole.eduservice.service.EduChapterService;
import com.fatehole.eduservice.service.EduCourseDescriptionService;
import com.fatehole.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fatehole.eduservice.service.EduVideoService;
import com.fatehole.servicebase.exception.LemonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private EduVideoService videoService;

    private EduChapterService chapterService;

    @Autowired
    public void setCourseDescriptionService(EduCourseDescriptionService courseDescriptionService) {
        this.courseDescriptionService = courseDescriptionService;
    }

    @Autowired
    public void setVideoService(EduVideoService videoService) {
        this.videoService = videoService;
    }

    @Autowired
    public void setChapterService(EduChapterService chapterService) {
        this.chapterService = chapterService;
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

    @Override
    public void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 以创建时间降序排列
        wrapper.orderByDesc("create_time");

        // 如果条记为空，全部查询
        if (courseQuery == null) {
            baseMapper.selectPage(coursePage, wrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();

        // 验证条件，并封装参数
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.like("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.like("subject_id", subjectId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.like("subject_parent_id", subjectParentId);
        }

        // 分页查询
        baseMapper.selectPage(coursePage, wrapper);
    }

    @Override
    public boolean removeCourseById(String id) {

        //根据id删除所有视频
        videoService.removeByCourseId(id);
        //根据id删除所有章节
        chapterService.removeByCourseId(id);
        // 删除课程描述
        courseDescriptionService.removeById(id);

        int row = baseMapper.deleteById(id);

        return row > 0;
    }

    @Cacheable(key = "'IndexCourse'", value = "course")
    @Override
    public List<EduCourse> selectList(Wrapper<EduCourse> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseQueryVo courseQuery) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        // 获取条件参数
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String buyCountSort = courseQuery.getBuyCountSort();
        String gmtCreateSort = courseQuery.getGmtCreateSort();
        String priceSort = courseQuery.getPriceSort();

        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }

        if (!StringUtils.isEmpty(buyCountSort)) {
            wrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(gmtCreateSort)) {
            wrapper.orderByDesc("create_time");
        }

        if (!StringUtils.isEmpty(priceSort)) {
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(coursePage, wrapper);

        Map<String, Object> result = new HashMap<>(16);
        result.put("rows", coursePage.getRecords());
        result.put("current", coursePage.getCurrent());
        result.put("pages", coursePage.getPages());
        result.put("size", coursePage.getSize());
        result.put("total", coursePage.getTotal());
        result.put("hasNext", coursePage.hasNext());
        result.put("hasPrevious", coursePage.hasPrevious());

        return result;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String id) {
        // 增加浏览量
        this.updatePageViewCount(id);
        return baseMapper.getBaseCourseInfo(id);
    }

    @Override
    public void updatePageViewCount(String id) {
        // 根据ID查出课程具体信息
        EduCourse course = baseMapper.selectById(id);
        // 浏览量 +1
        course.setViewCount(course.getViewCount() + 1);
        // 执行
        baseMapper.updateById(course);
    }
}
