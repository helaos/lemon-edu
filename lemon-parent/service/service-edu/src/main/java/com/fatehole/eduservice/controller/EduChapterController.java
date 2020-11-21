package com.fatehole.eduservice.controller;


import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.entity.vo.ChapterVo;
import com.fatehole.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
@CrossOrigin
@Api(tags = "课程章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    private EduChapterService chapterService;

    @Autowired
    public void setChapterService(EduChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("/{courseId}")
    public Result getChapterVideo(@PathVariable("courseId")
                                  @ApiParam(name = "courseId", value = "课程ID", required = true)
                                  String courseId) {

        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);

        return Result.ok().data("items", chapterVoList);
    }
}

