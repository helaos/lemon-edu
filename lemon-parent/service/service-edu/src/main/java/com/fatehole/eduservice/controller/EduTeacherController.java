package com.fatehole.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.entity.EduTeacher;
import com.fatehole.eduservice.entity.vo.TeacherQuery;
import com.fatehole.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-11-08
 */
@Api(tags = "教师管理" )
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询教师的所有数据
     * @return 教师信息集合
     */
    @ApiOperation(value = "所有教师列表")
    @GetMapping("")
    public Result findAllTeacher() {
        // 调用service的方法实现查询所有的操作
        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.ok().data("items", list);
    }

    /**
     * 根据id删除教师
     * @param id 教师id
     * @return 是否删除
     */
    @ApiOperation(value = "逻辑删除教师")
    @DeleteMapping("/{id}")
    public Result removeTeacher(@ApiParam(name = "id", value = "教师ID", required = true) @PathVariable("id") String id) {

        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }

    }

    /**
     * 教师信息分页
     * @param current 当前页
     * @param limit 每页记录数
     * @return 数据
     */
    @ApiOperation(value = "分页教师信息列表")
    @GetMapping("/{current}/{limit}")
    public Result pageList(@ApiParam(name = "current", value = "当前页码", required = true)
                           @PathVariable("current") Long current,
                           @ApiParam(name = "limit", value = "每页记录数", required = true)
                           @PathVariable("limit") Long limit) {

        // 创建page对象
        Page<EduTeacher> teacherPage = new Page<>(current, limit);

        // 调用方法实现分页
        eduTeacherService.page(teacherPage, null);

        // 总记录数
        long total = teacherPage.getTotal();

        // 数据集
        List<EduTeacher> records = teacherPage.getRecords();

        // 封装结果
        Map<String, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("rows", records);

        // 返回统一返回结果
        return Result.ok().data(map);
    }

    /**
     * 带条件的分页查询
     * @param current 当前页
     * @param limit 每页记录数
     * @param teacherQuery 条件封装
     * @return 统一结果
     */
    @ApiOperation(value = "带条件的分页教师信息列表")
    @PostMapping("/condition/{current}/{limit}")
    public Result pageQuery(@ApiParam(name = "current", value = "当前页码", required = true)
                            @PathVariable("current") Long current,
                            @ApiParam(name = "limit", value = "每页记录数", required = true)
                            @PathVariable("limit") Long limit,
                            @ApiParam(name = "query", value = "查询条件")
                            @RequestBody(required = false) TeacherQuery teacherQuery) {

        // 创建page对象
        Page<EduTeacher> teacherPage = new Page<>(current, limit);

        // 调用实现分页
        eduTeacherService.pageQuery(teacherPage, teacherQuery);

        // 封装结果
        Map<String, Object> map = new HashMap<>(2);
        map.put("total", teacherPage.getTotal());
        map.put("rows", teacherPage.getRecords());

        // 返回统一返回结果
        return Result.ok().data(map);
    }

    /**
     * 新增讲师
     * @param eduTeacher 讲师对象
     * @return 统一结果
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping("")
    public Result addTeacher(@ApiParam(name = "teacher", value = "讲师对象", required = true)
                                 @RequestBody EduTeacher eduTeacher) {

        boolean flag = eduTeacherService.save(eduTeacher);

        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    /**
     * 根据ID查询讲师
     * @param id 讲师ID
     * @return 统一结果
     */
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/{id}")
    public Result getTeacherById(@ApiParam(name = "id", value = "讲师ID", required = true)
                                 @PathVariable("id") String id) {

        EduTeacher teacher = eduTeacherService.getById(id);

        return Result.ok().data("item", teacher);
    }

    /**
     * 根据ID修改讲师
     * @param id 讲师ID
     * @param eduTeacher 修改信息
     * @return 统一结果
     */
    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("/{id}")
    public Result updateTeacherById(@ApiParam(name = "id", value = "讲师ID", required = true)
                                    @PathVariable("id") String id,
                                    @ApiParam(name = "teacher", value = "讲师对象", required = true)
                                    @RequestBody EduTeacher eduTeacher) {

        eduTeacher.setId(id);
        boolean flag = eduTeacherService.updateById(eduTeacher);

        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

