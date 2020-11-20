package com.fatehole.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatehole.eduservice.entity.EduSubject;
import com.fatehole.eduservice.entity.excel.SubjectData;
import com.fatehole.eduservice.service.EduSubjectService;
import com.fatehole.servicebase.exception.LemonException;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/18/12:50
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData data, AnalysisContext context) {

        if (data == null) {
            throw new LemonException(20001, "文件数据为空");
        }

        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, data.getOneSubjectName());
        if (existOneSubject == null) {
            // 没有相同一级分类，进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(data.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }

        String pid = existOneSubject.getId();

        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, data.getTwoSubjectName(), pid);
        // 判断二级分类是否重复
        if (existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(data.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }

    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    /**
     * 判断一级分类不能重复添加
     * @param eduSubjectService 传入service
     * @param name title名
     * @return ？
     */
    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();

        wrapper.eq("title", name)
                .eq("parent_id", "0");

        return eduSubjectService.getOne(wrapper);
    }


    /**
     * 判断二级分类不能重复添加
     * @param eduSubjectService 传入service
     * @param name title名
     * @param parentId 父级id
     * @return ?
     */
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService, String name, String parentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();

        wrapper.eq("title", name)
                .eq("parent_id", parentId);

        return eduSubjectService.getOne(wrapper);
    }
}
