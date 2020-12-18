package com.fatehole.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.fatehole.commonutil.Result;
import com.fatehole.servicebase.exception.LemonException;
import com.fatehole.vod.service.VodService;
import com.fatehole.vod.util.AliyunVodSdkUtil;
import com.fatehole.vod.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/26/10:53
 */
@CrossOrigin
@RestController
@Api(tags = "阿里云视频点播微服务")
@RequestMapping("/eduvod/video")
public class VodController {

    private VodService vodService;

    @Autowired
    public void setVodService(VodService vodService) {
        this.vodService = vodService;
    }

    @ApiOperation(value = "上传视频文件到阿里云")
    @PostMapping("/upload")
    public Result uploadVideo(@ApiParam(name = "file", value = "文件", required = true)
                              @RequestParam("file") MultipartFile file) {

        String videoId = vodService.uploadVideo(file);

        return Result.ok().message("视频上传成功！").data("videoId", videoId);
    }

    @ApiOperation(value = "根据视频ID删除阿里云中的视频文件")
    @DeleteMapping("/{id}")
    public Result removeAliVideoById(@ApiParam(name = "id", value = "云端视频id", required = true)
                                     @PathVariable("id") String id) {

        vodService.removeVideo(id);

        return Result.ok().message("视频删除成功");
    }

    @DeleteMapping("/batch")
    public Result removeBatch(@ApiParam(name = "videoIdList", value = "云端视频id", required = true)
                              @RequestParam("videoIdList") List<String> videoIdList) {

        vodService.removeMoreAliVideo(videoIdList);

        return Result.ok().message("视频删除成功");
    }

    @GetMapping("/{id}")
    public Result getVideoPlayAuth(@ApiParam(name = "id", value = "云端视频id", required = true)
                                   @PathVariable("id") String id) {
        try {
            // 获取初始化对象
            DefaultAcsClient client = AliyunVodSdkUtil.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 创建获取凭证request和response对象
            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);

            // 响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            String playAuth = response.getPlayAuth();

            return Result.ok().message("获取凭证成功").data("playAuth", playAuth);

        } catch (ClientException e) {
            throw new LemonException(20001, "获取凭证失败");
        }
    }
}
