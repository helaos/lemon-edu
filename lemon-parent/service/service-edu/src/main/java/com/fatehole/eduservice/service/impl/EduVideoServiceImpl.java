package com.fatehole.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.client.VodClient;
import com.fatehole.eduservice.entity.EduVideo;
import com.fatehole.eduservice.entity.vo.VideoInfoVo;
import com.fatehole.eduservice.mapper.EduVideoMapper;
import com.fatehole.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fatehole.servicebase.exception.LemonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频表 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    private VodClient vodClient;

    @Autowired
    public void setVodClient(VodClient vodClient) {
        this.vodClient = vodClient;
    }

    @Override
    public boolean getCountByChapterId(String id) {

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();

        wrapper.eq("chapter_id", id);

        Integer count = baseMapper.selectCount(wrapper);

        return count > 0;
    }

    @Override
    public void saveVideoInfo(VideoInfoVo videoInfoVo) {

        EduVideo video = new EduVideo();

        BeanUtils.copyProperties(videoInfoVo, video);

        boolean flag = this.save(video);

        if (!flag) {
            throw new LemonException(20001, "课时信息保存失败");
        }
    }

    @Override
    public boolean removeVideoById(String id) {

        EduVideo eduVideo = this.getById(id);

        String videoSourceId = eduVideo.getVideoSourceId();

        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeAliVideoById(videoSourceId);
        }

        int result = baseMapper.deleteById(id);

        return result > 0;
    }

    @Override
    public VideoInfoVo getVideoInfoFormById(String id) {

        // 从video表中取数据
        EduVideo video = this.getById(id);

        if (video == null) {
            throw new LemonException(20001, "数据不存在");
        }
        // 创建VideoInfoVo
        VideoInfoVo videoInfoVo = new VideoInfoVo();

        BeanUtils.copyProperties(video, videoInfoVo);

        return videoInfoVo;
    }

    @Override
    public void updateVideoInfoById(VideoInfoVo videoInfoVo) {

        EduVideo video = new EduVideo();

        BeanUtils.copyProperties(videoInfoVo, video);

        boolean flag = this.updateById(video);

        if (!flag) {
            throw new LemonException(20001, "课时信息保存失败");
        }
    }

    @Override
    public boolean removeByCourseId(String courseId) {

        // 根据课程ID查询课程所有的视频ID
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("course_id", courseId)
            .select("video_source_id");

        List<EduVideo> eduVideoList = baseMapper.selectList(queryWrapper);

        List<String> videoIdList = eduVideoList
                .stream()
                .map(EduVideo::getVideoSourceId)
                .collect(Collectors.toList());

        if (videoIdList.size() > 0) {
            vodClient.removeBatch(videoIdList);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();

        wrapper.eq("course_id", courseId);

        int row = baseMapper.delete(wrapper);

        return row > 0;
    }
}
