package com.fatehole.eduservice.service;

import com.fatehole.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fatehole.eduservice.entity.vo.VideoInfoVo;

/**
 * <p>
 * 课程视频表 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据章节ID查询是否有视频存在
     * @param id 章节ID
     * @return 是或否
     */
    boolean getCountByChapterId(String id);

    /**
     * 添加课时信息
     * @param videoInfoVo 课时信息对象
     */
    void saveVideoInfo(VideoInfoVo videoInfoVo);

    /**
     * 根据ID删除可是新消息
     * @param id 课是ID
     * @return 是与否
     */
    boolean removeVideoById(String id);

    /**
     * 根据ID查询课时
     * @param id 课是ID
     * @return 结果
     */
    VideoInfoVo getVideoInfoFormById(String id);

    /**
     * 更新课时
     * @param videoInfoVo 更新信息
     */
    void updateVideoInfoById(VideoInfoVo videoInfoVo);

    /**
     * 根据课程ID删除课程小节
     * @param courseId 课程ID
     * @return 是与否
     */
    boolean removeByCourseId(String courseId);
}
