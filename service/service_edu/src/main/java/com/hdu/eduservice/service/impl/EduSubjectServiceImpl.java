package com.hdu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.eduservice.entity.EduSubject;
import com.hdu.eduservice.entity.excel.SubjectData;
import com.hdu.eduservice.entity.subject.OneSubject;
import com.hdu.eduservice.entity.subject.TwoSubject;
import com.hdu.eduservice.listener.SubjectExcelListener;
import com.hdu.eduservice.mapper.EduSubjectMapper;
import com.hdu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author testjava
 * @since 2024-07-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private EduSubjectService eduSubjectService;

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        // 一级课程
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);
        List<EduSubject> oneSubjects = eduSubjectService.list(wrapperOne);
        // 二级课程
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> twoSubjects = eduSubjectService.list(wrapperTwo);
        ArrayList<OneSubject> finalSubject = new ArrayList<>();
        for (int i = 0; i < oneSubjects.size(); i++) {
            EduSubject eduOneSubject = oneSubjects.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduOneSubject, oneSubject);
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for (int j = 0; j < twoSubjects.size(); j++) {
                EduSubject eduTwoSubject = twoSubjects.get(j);
                if (eduTwoSubject.getParentId().equals(oneSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduTwoSubject, twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoSubjectList);
            finalSubject.add(oneSubject);
        }
        return finalSubject;
    }
}
