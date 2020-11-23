package com.fatehole.eduservice.mapper;

import com.fatehole.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fatehole.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程信息表 Mapper 接口
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 根据课程ID查询
     * @param id 课程ID
     * @return 课程确认信息
     */
    CoursePublishVo selectCoursePublishVoById(String id);

}
