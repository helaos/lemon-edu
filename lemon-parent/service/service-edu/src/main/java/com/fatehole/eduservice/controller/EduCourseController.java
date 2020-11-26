package com.fatehole.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.entity.EduCourse;
import com.fatehole.eduservice.entity.vo.CourseInfoVo;
import com.fatehole.eduservice.entity.vo.CoursePublishVo;
import com.fatehole.eduservice.entity.vo.CourseQuery;
import com.fatehole.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @ApiOperation(value = "根据课程ID查询确认信息")
    @GetMapping("/publish/{id}")
    public Result getPublishCourseInfo(@ApiParam(name = "id", value = "课程ID", required = true)
                                       @PathVariable("id") String id) {

        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(id);

        return Result.ok().data("item", coursePublishVo);
    }

    @ApiOperation(value = "根据课程ID修改课程状态")
    @PutMapping("/status/{id}")
    public Result publishCourse(@PathVariable("id") String id) {

        boolean flag = courseService.publishCourse(id);

        if (flag) {
            return Result.ok();
        } else {
            return Result.error().message("发布失败，未知错误！");
        }
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("/condition/{page}/{limit}")
    public Result conditionPageQuery(@ApiParam(name = "page", value = "当前页码", required = true)
                                     @PathVariable("page") Long page,
                                     @ApiParam(name = "limit", value = "每页记录数", required = true)
                                     @PathVariable("limit") Long limit,
                                     @ApiParam(name = "courseQuery", value = "查询对象")
                                     CourseQuery courseQuery) {

        // 分页
        Page<EduCourse> coursePage = new Page<>(page, limit);

        courseService.pageQuery(coursePage, courseQuery);

        // 封装结果集
        Map<String, Object> map = new HashMap<>(2);
        map.put("total", coursePage.getTotal());
        map.put("rows", coursePage.getRecords());

        // 返回统一返回结果
        return Result.ok().data(map);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("/{id}")
    public Result deleteCourse(@ApiParam(name = "id", value = "课程ID", required = true)
                               @PathVariable("id") String id) {

        boolean result = courseService.removeCourseById(id);

        if (result) {
            return Result.ok();
        } else {
            return Result.error().message("删除失败！");
        }
    }
}

