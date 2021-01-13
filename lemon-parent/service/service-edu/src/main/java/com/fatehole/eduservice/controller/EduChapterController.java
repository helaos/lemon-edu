package com.fatehole.eduservice.controller;

import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.entity.EduChapter;
import com.fatehole.eduservice.entity.vo.ChapterVo;
import com.fatehole.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.awt.geom.AreaOp;

import java.util.List;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
@Api(tags = "课程章节管理")
// @CrossOrigin
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

    @ApiOperation(value = "新增章节")
    @PostMapping("")
    public Result addChapter(@ApiParam(name = "chapterVo", value = "章节对象", required = true)
                             @RequestBody EduChapter eduChapter) {

        chapterService.save(eduChapter);

        return Result.ok();
    }

    @ApiOperation(value = "根据ID获取章节信息")
    @GetMapping("/info/{chapterId}")
    public Result getChapterInfo(@ApiParam(name = "chapterId", value = "章节ID", required = true)
                                 @PathVariable("chapterId") String chapterId) {

        EduChapter chapter = chapterService.getById(chapterId);

        return Result.ok().data("item", chapter);
    }

    @ApiOperation(value = "根据ID修改章节")
    @PutMapping("/{id}")
    public Result updateById(@ApiParam(name = "id", value = "章节ID", required = true)
                             @PathVariable("id") String id,
                             @ApiParam(name = "chapter", value = "章节对象", required = true)
                             @RequestBody EduChapter chapter) {

        chapter.setId(id);
        boolean flag = chapterService.updateById(chapter);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error().message("修改失败！");
        }
    }

    @DeleteMapping("/{id}")
    public Result removeById(@ApiParam(name = "id", value = "章节ID", required = true)
                             @PathVariable("id") String id) {

        boolean flag = chapterService.removeChapterById(id);

        if (flag) {
            return Result.ok();
        } else {
            return Result.error().message("删除失败！");
        }
    }
}

