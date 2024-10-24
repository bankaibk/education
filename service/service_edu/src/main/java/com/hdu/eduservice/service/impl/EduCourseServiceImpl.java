package com.hdu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdu.eduservice.entity.*;
import com.hdu.eduservice.entity.frontvo.CourseFrontVo;
import com.hdu.eduservice.entity.frontvo.CourseWebVo;
import com.hdu.eduservice.entity.vo.CoursePublishVo;
import com.hdu.eduservice.entity.vo.CourseVo;
import com.hdu.eduservice.mapper.EduCourseMapper;
import com.hdu.eduservice.service.EduChapterService;
import com.hdu.eduservice.service.EduCourseDescriptionService;
import com.hdu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.eduservice.service.EduVideoService;
import com.hdu.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2024-07-13
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveCourseInfo(CourseVo courseVo) {
        // 向课程表添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo, eduCourse);
        boolean flag = this.save(eduCourse);
        if (!flag)
            throw new GuliException(20001, "添加课程信息失败");
        String cid = eduCourse.getId();
        // 向课程简介表添加简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseVo.getDescription());
        descriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseVo getCourseInfo(String courseId) {
        EduCourse eduCourse = this.getById(courseId);
        EduCourseDescription courseDescription = descriptionService.getById(courseId);
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(eduCourse, courseVo);
        courseVo.setDescription(courseDescription.getDescription());
        return courseVo;
    }

    @Override
    public void updateCourseInfo(CourseVo courseVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo, eduCourse);
        boolean flag = this.updateById(eduCourse);
        if (!flag)
            throw new GuliException(20001, "修改课程信息失败");
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseVo, courseDescription);
        descriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public void removeCourse(String courseId) {
        // 根据id删除小节
        videoService.removeVideoByCourseId(courseId);
        // 根据id删除章节
        chapterService.removeChapterByCourseId(courseId);
        // 根据id删除描述
        descriptionService.removeById(courseId);
        // 根据id删除课程
        boolean flag = this.removeById(courseId);
        if (!flag)
            throw new GuliException(20001, "删除失败");
    }

    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id",
                    courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        this.page(coursePage, queryWrapper);
        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
