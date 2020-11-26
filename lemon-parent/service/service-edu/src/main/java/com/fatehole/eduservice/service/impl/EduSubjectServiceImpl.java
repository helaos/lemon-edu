package com.fatehole.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatehole.eduservice.entity.EduSubject;
import com.fatehole.eduservice.entity.excel.SubjectData;
import com.fatehole.eduservice.entity.subject.OneSubject;
import com.fatehole.eduservice.entity.subject.TwoSubject;
import com.fatehole.eduservice.listener.SubjectExcelListener;
import com.fatehole.eduservice.mapper.EduSubjectMapper;
import com.fatehole.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-11-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 文件输入流
            InputStream inputStream = file.getInputStream();
            // 调用方法进行读取
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        // 查询所有的一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 查询所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建List集合，用于储存分装数据
        List<OneSubject> finalSubjectAll = new ArrayList<>();

        // 封装一级费雷
        for (EduSubject eduSubject : oneSubjectList) {
            // 得到oneSubjectList的每一个eduSubject对象
            // 将取出来的值放入对象中
            OneSubject oneSubject = new OneSubject();
            // 注入
            BeanUtils.copyProperties(eduSubject, oneSubject);
            // 多个oneSubject放入finalSubjectAll
            finalSubjectAll.add(oneSubject);

            // 创建一个list封装每一个一级分类的二级分类
            List<TwoSubject> twoFinalSubjects = new ArrayList<>();

            // 历遍二级分类
            for (EduSubject mEduSubject : twoSubjectList) {
                if (mEduSubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(mEduSubject, twoSubject);
                    twoFinalSubjects.add(twoSubject);
                }
            }
            // 封装二级分类
            oneSubject.setChildren(twoFinalSubjects);
        }
        return finalSubjectAll;
    }
}
