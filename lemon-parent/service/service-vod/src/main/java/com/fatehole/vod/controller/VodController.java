package com.fatehole.vod.controller;

import com.fatehole.commonutil.Result;
import com.fatehole.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
