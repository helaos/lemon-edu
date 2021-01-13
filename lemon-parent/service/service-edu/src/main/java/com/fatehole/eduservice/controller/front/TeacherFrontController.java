package com.fatehole.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.entity.EduCourse;
import com.fatehole.eduservice.entity.EduTeacher;
import com.fatehole.eduservice.service.EduCourseService;
import com.fatehole.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/06/15:58
 */
// @CrossOrigin
@RestController
@Api(tags = "前台的教师页面")
@RequestMapping("/eduservice/front/teacher")
public class TeacherFrontController {

    private EduTeacherService teacherService;

    private EduCourseService courseService;

    @Autowired
    public void setCourseService(EduCourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setTeacherService(EduTeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ApiOperation(value = "获取前台教师分页数据")
    @GetMapping("/{page}/{limit}")
    public Result getTeacherFrontList(@ApiParam(name = "page", value = "当前页码", required = true)
                                      @PathVariable Long page,
                                      @ApiParam(name = "limit", value = "每页记录数", required = true)
                                      @PathVariable Long limit) {

        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(teacherPage);

        return Result.ok().data(map);
    }

    @GetMapping("/info/{id}")
    public Result getTeacherInfo(@ApiParam(name = "id", value = "讲师ID")
                                 @PathVariable("id") String id) {

        // 根据讲师ID查询信息
        EduTeacher teacherInfo = teacherService.getById(id);

        // 根据讲师ID查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        wrapper.eq("teacher_id", id);

        List<EduCourse> courseList = courseService.list(wrapper);

        return Result.ok().data("teacher", teacherInfo).data("courseList", courseList);
    }
}
