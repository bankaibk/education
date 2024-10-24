package com.hdu.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdu.commonutils.R;
import com.hdu.eduservice.entity.EduCourse;
import com.hdu.eduservice.entity.EduTeacher;
import com.hdu.eduservice.service.EduCourseService;
import com.hdu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduService/teacherFront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    // 讲师分页
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(map);
    }

    // 讲师详情
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        EduTeacher teacher = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courses = courseService.list(wrapper);
        return R.ok().data("teacher", teacher).data("courses", courses);
    }

}
