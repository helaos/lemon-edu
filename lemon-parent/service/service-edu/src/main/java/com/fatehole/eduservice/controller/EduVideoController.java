package com.fatehole.eduservice.controller;


import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.entity.EduVideo;
import com.fatehole.eduservice.entity.vo.VideoInfoVo;
import com.fatehole.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频表 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
@Api(tags = "课程小节管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    private EduVideoService videoService;

    @Autowired
    public void setVideoService(EduVideoService videoService) {
        this.videoService = videoService;
    }

    @ApiOperation(value = "新增小节")
    @PostMapping("")
    public Result addVideo(@ApiParam(name = "videoInfoVo", value = "课时对象", required = true)
                           @RequestBody VideoInfoVo videoInfoVo) {

        videoService.saveVideoInfo(videoInfoVo);

        return Result.ok();
    }

    @ApiOperation(value = "根据ID删除小节")
    @DeleteMapping("/{id}")
    public Result removeVideo(@ApiParam(name = "id", value = "小节ID", required = true)
                              @PathVariable("id") String id) {

        boolean flag = videoService.removeVideoById(id);

        if (flag) {
            return Result.ok();
        } else {
            return Result.error().message("删除失败！");
        }
    }

    @ApiOperation(value = "根据ID查询小节")
    @GetMapping("/{id}")
    public Result getVideoInfoById(@ApiParam(name = "id", value = "小节ID", required = true)
                                   @PathVariable("id") String id) {

        VideoInfoVo videoInfoVo = videoService.getVideoInfoFormById(id);

        return Result.ok().data("item", videoInfoVo);
    }

    @ApiOperation(value = "更新小节")
    @PutMapping("/{id}")
    public Result updateCourseInfoById(@ApiParam(name = "VideoInfoForm", value = "小节基本信息", required = true)
                                       @RequestBody VideoInfoVo videoInfoVo,
                                       @ApiParam(name = "id", value = "小节ID", required = true)
                                       @PathVariable("id") String id) {

        videoService.updateVideoInfoById(videoInfoVo);

        return Result.ok().data("id", id);
    }
}

