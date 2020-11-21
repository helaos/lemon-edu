package com.fatehole.eduservice.controller;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.entity.vo.CourseInfoVo;
import com.fatehole.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
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
}

