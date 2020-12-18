package com.fatehole.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.commonutil.JwtUtils;
import com.fatehole.commonutil.Result;
import com.fatehole.commonutil.vo.CourseOrderVo;
import com.fatehole.eduservice.client.OrderClient;
import com.fatehole.eduservice.entity.EduCourse;
import com.fatehole.eduservice.entity.vo.ChapterVo;
import com.fatehole.eduservice.entity.vo.CourseQueryVo;
import com.fatehole.eduservice.entity.vo.CourseWebVo;
import com.fatehole.eduservice.service.EduChapterService;
import com.fatehole.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/06/23:24
 */
@CrossOrigin
@RestController
@Api(tags = "前台的课程页面")
@RequestMapping("/eduservice/front/course")
public class CourseFrontController {

    private EduCourseService courseService;

    private EduChapterService chapterService;

    private OrderClient orderClient;

    @Autowired
    public void setOrderClient(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @Autowired
    public void setChapterService(EduChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @Autowired
    public void setCourseService(EduCourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/{page}/{limit}")
    public Result getCoursePageList(@ApiParam(name = "page", value = "当前页码", required = true)
                                    @PathVariable Long page,
                                    @ApiParam(name = "limit", value = "每页记录数", required = true)
                                    @PathVariable Long limit,
                                    @ApiParam(name = "courseQuery", value = "查询对象")
                                    @RequestBody(required = false) CourseQueryVo courseQuery) {

        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(coursePage, courseQuery);

        // 传回分页数据
        return Result.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("/{id}")
    public Result getFrontCourseInfo(@ApiParam(name = "id", value = "课程ID", required = true)
                                     @PathVariable("id") String id,
                                     HttpServletRequest request) {

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(id);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVos = chapterService.getChapterVideoByCourseId(id);

        // 根据课程ID和用户ID查询当前的课程是否已经支付过了
        Boolean isBuy = orderClient.isBuyCourse(id, JwtUtils.getMemberIdByJwtToken(request));

        return Result.ok().data("course", courseWebVo).data("chapterVoList", chapterVos).data("isBuy", isBuy);
    }

    @PostMapping("/info/{id}")
    public CourseOrderVo getCourseInfoToOrder(@PathVariable("id") String id) {

        CourseWebVo info = courseService.getBaseCourseInfo(id);
        CourseOrderVo courseInfo = new CourseOrderVo();
        BeanUtils.copyProperties(info, courseInfo);
        return courseInfo;
    }
}
