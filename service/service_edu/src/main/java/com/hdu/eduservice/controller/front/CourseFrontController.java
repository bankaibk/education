package com.hdu.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdu.commonutils.R;
import com.hdu.commonutils.ordervo.CourseWebOrderVo;
import com.hdu.eduservice.entity.EduCourse;
import com.hdu.eduservice.entity.chapter.Chapter;
import com.hdu.eduservice.entity.frontvo.CourseFrontVo;
import com.hdu.eduservice.entity.frontvo.CourseWebVo;
import com.hdu.eduservice.service.EduChapterService;
import com.hdu.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduService/courseFront")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;

    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@RequestBody(required = false) CourseFrontVo courseFrontVo, @PathVariable long page, @PathVariable long limit) {
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getFrontCourseList(coursePage, courseFrontVo);
        return R.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        CourseWebVo webVo = courseService.getBaseCourseInfo(courseId);
        List<Chapter> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("courseWebVo", webVo).data("chapterVideoList", chapterVideoList);
    }

    // 根据课程id查询信息
    @GetMapping("getCourseInfoOrder/{id}")
    public CourseWebOrderVo getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebOrderVo webOrderVo = new CourseWebOrderVo();
        BeanUtils.copyProperties(courseInfo, webOrderVo);
        return webOrderVo;
    }
}
