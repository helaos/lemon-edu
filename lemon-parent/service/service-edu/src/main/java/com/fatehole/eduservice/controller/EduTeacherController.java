package com.fatehole.eduservice.controller;


import com.fatehole.eduservice.entity.EduTeacher;
import com.fatehole.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/findAll")
    public List<EduTeacher> findAllTeacher() {
        // 调用service的方法实现查询所有的操作
        return eduTeacherService.list(null);
    }

    /**
     * 根据id删除教师
     * @param id 教师id
     * @return 是否删除
     */
    @ApiOperation(value = "逻辑删除教师")
    @DeleteMapping("/{id}")
    public boolean removeTeacher(@ApiParam(name = "id", value = "教师ID", required = true) @PathVariable("id") String id) {
        return eduTeacherService.removeById(id);
    }
}

