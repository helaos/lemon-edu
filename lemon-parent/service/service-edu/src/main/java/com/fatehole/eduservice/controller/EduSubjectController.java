package com.fatehole.eduservice.controller;

import com.fatehole.commonutil.Result;
import com.fatehole.eduservice.entity.subject.OneSubject;
import com.fatehole.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/17/22:34
 */
@Api(tags="课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    private EduSubjectService eduSubjectService;

    @Autowired
    public void setEduSubjectService(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("")
    public Result addSubject(MultipartFile file) {

        // 上传过来的excel
        eduSubjectService.saveSubject(file, eduSubjectService);

        return Result.ok();
    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("")
    public Result getAllOneTwoSubject() {

        // list中的泛型是一级分类，因为一级分类中含有二级分类
        List<OneSubject> list =  eduSubjectService.getAllOneTwoSubject();

        return Result.ok().data("list", list);
    }
}
