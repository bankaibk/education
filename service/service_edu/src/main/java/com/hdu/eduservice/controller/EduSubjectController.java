package com.hdu.eduservice.controller;


import com.hdu.commonutils.R;
import com.hdu.eduservice.entity.subject.OneSubject;
import com.hdu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2024-07-07
 */
@RestController
@RequestMapping("/eduService/subject")
@CrossOrigin
//@CorsConfig
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.ok().message("上传文件成功");
    }

    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = eduSubjectService.getAllSubject();
        return R.ok().data("list", list);
    }
}

