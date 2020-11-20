package com.fatehole.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fatehole.eduservice.entity.EduSubject;
import com.fatehole.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/17/22:36
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 接受上传上垒的excel表格，将分类保存
     * @param file excel表格文件
     * @param eduSubjectService 传入的service
     */
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    /**
     * 查询出所有课程分类的树形结构
     * list中的泛型是一级分类，因为一级分类中含有二级分类
     * @return 包含数据的list
     */
    List<OneSubject> getAllOneTwoSubject();
}
