package com.fatehole.eduservice.service;

import com.fatehole.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fatehole.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程ID查询课程大纲列表
     * @param courseId 课程ID
     * @return 大纲列表
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 根据ID删除章节
     * @param id 章节ID
     * @return 是或否
     */
    boolean removeChapterById(String id);
}
