package com.fatehole.oss.controller;

import com.fatehole.commonutil.Result;
import com.fatehole.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/15/19:56
 */
@Api(tags="阿里云文件管理")
// @CrossOrigin
@RestController
@RequestMapping("/eduoss/file")
public class OssController {

    private OssService ossService;

    @Autowired
    public void setOssService(OssService ossService) {
        this.ossService = ossService;
    }

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public Result upload(@ApiParam(name = "file", value = "文件", required = true)
                         @RequestParam("file") MultipartFile file) {

        String uploadUrl = ossService.uploadFileAvatar(file);
        // 返回对象
        return Result.ok().message("文件上传成功").data("url", uploadUrl);
    }
}
