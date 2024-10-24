package com.hdu.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.commonutils.R;
import com.hdu.eduservice.entity.EduCourse;
import com.hdu.eduservice.entity.EduTeacher;
import com.hdu.eduservice.service.EduCourseService;
import com.hdu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduService/indexFront")

public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @Cacheable(value = "indexInfo",key = "'indexInfoList'")
    @GetMapping("index")
    public R index() {
        // 查询前八条热门课程
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("view_count");
        courseWrapper.last("LIMIT 8");
        List<EduCourse> courses = courseService.list(courseWrapper);

        // 查询前四个名师
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("LIMIT 4");
        List<EduTeacher> teachers = teacherService.list(teacherWrapper);
        return R.ok().data("courses", courses).data("teachers", teachers);
    }
}
