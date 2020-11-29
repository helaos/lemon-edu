package com.fatehole.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.entity.EduCourse;
import com.fatehole.eduservice.entity.EduTeacher;
import com.fatehole.eduservice.service.EduCourseService;
import com.fatehole.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/28/23:26
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/index")
public class IndexFrontController {

    private EduCourseService courseService;

    private EduTeacherService teacherService;

    @Autowired
    public void setCourseService(EduCourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setTeacherService(EduTeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("")
    public Result index() {

        // 查询前8门热门课程
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();

        wrapperCourse
            .orderByDesc("id")
            .last("limit 8");

        List<EduCourse> courses = courseService.selectList(wrapperCourse);


        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();

        wrapperTeacher
                .orderByDesc("id")
                .last("limit 4");

        List<EduTeacher> teachers = teacherService.selectList(wrapperTeacher);

        return Result.ok().data("courseList", courses).data("teacherList", teachers);
    }
}
