package com.fatehole.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatehole.eduservice.entity.EduChapter;
import com.fatehole.eduservice.entity.EduVideo;
import com.fatehole.eduservice.entity.vo.ChapterVo;
import com.fatehole.eduservice.entity.vo.VideoVo;
import com.fatehole.eduservice.mapper.EduChapterMapper;
import com.fatehole.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fatehole.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    private EduVideoService videoService;

    @Autowired
    public void setVideoService(EduVideoService videoService) {
        this.videoService = videoService;
    }

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        // 根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        // 根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        // 创建一个结合，用于封装最终数据
        List<ChapterVo> finalList = new ArrayList<>();

        // 遍历查询章节list集合进行封装
        for (EduChapter chapter : eduChapterList) {
            //创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            finalList.add(chapterVo);

            // 填充课时vo数据
            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                if (chapter.getId().equals(eduVideo.getChapterId())) {

                    // 创建课时vo对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }

        // 遍历查询小节list集合进行封装
        return finalList;
    }
}
