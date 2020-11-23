package com.fatehole.eduservice.controller;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.entity.vo.CourseInfoVo;
import com.fatehole.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程信息表 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-11-19
 */
@Api(tags = "课程信息管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    private EduCourseService courseService;

    @Autowired
    public void setCourseService(EduCourseService courseService) {
        this.courseService = courseService;
    }

    @ApiOperation(value = "添加课程信息")
    @PostMapping("")
    public Result addCourseInfo(@ApiParam(name = "CourseInfoVo", value = "课程基本信息", required = true)
                                    @RequestBody CourseInfoVo courseInfoVo) {

        String courseId = courseService.saveCourseInfo(courseInfoVo);

        if (!StringUtils.isEmpty(courseId)) {
            return Result.ok().data("courseId", courseId);
        } else {
            return Result.error().message("保存失败！");
        }
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("/{courseId}")
    public Result getCourseInfo(@ApiParam(name = "courseId", value = "课程ID", required = true)
                                @PathVariable("courseId") String courseId) {

        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);

        return Result.ok().data("item", courseInfoVo);
    }

    @ApiOperation(value = "更新课程")
    @PutMapping("/{id}")
    public Result updateCourseInfoById(@ApiParam(name = "CourseInfoVo", value = "课程基本信息", required = true)
                                       @RequestBody CourseInfoVo courseInfoVo,
                                       @ApiParam(name = "id", value = "课程ID", required = true)
                                       @PathVariable("id") String id) {

        courseService.updateCourseInfo(courseInfoVo);

        return Result.ok().data("courseId", id);
    }
}

