package com.hdu.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdu.commonutils.R;
import com.hdu.eduservice.entity.EduCourse;
import com.hdu.eduservice.entity.vo.CoursePublishVo;
import com.hdu.eduservice.entity.vo.CourseQuery;
import com.hdu.eduservice.entity.vo.CourseVo;
import com.hdu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2024-07-13
 */
@RestController
@RequestMapping("/eduService/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    // 添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseVo courseVo) {
        String id = courseService.saveCourseInfo(courseVo);
        return R.ok().data("courseId", id);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseVo courseVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseVo", courseVo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseVo courseVo) {
        courseService.updateCourseInfo(courseVo);
        return R.ok();
    }

    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    @PostMapping("pageCourseCondition/{page}/{limit}")
    public R pageCourseCondition(@RequestBody CourseQuery courseQuery, @PathVariable Long limit, @PathVariable Long page) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        courseService.page(coursePage, wrapper);
        List<EduCourse> records = coursePage.getRecords();
        long total = coursePage.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

