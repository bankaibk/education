package com.hdu.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.eduservice.entity.frontvo.CourseFrontVo;
import com.hdu.eduservice.entity.frontvo.CourseWebVo;
import com.hdu.eduservice.entity.vo.CoursePublishVo;
import com.hdu.eduservice.entity.vo.CourseVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2024-07-13
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseVo courseVo);

    CourseVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseVo courseVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getFrontCourseList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
