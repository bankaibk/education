package com.hdu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.eduservice.entity.EduChapter;
import com.hdu.eduservice.entity.EduVideo;
import com.hdu.eduservice.entity.chapter.Chapter;
import com.hdu.eduservice.entity.chapter.Video;
import com.hdu.eduservice.mapper.EduChapterMapper;
import com.hdu.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.eduservice.service.EduVideoService;
import com.hdu.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2024-07-13
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;

    @Override
    public List<Chapter> getChapterVideoByCourseId(String courseId) {
        // 查询章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = this.list(wrapperChapter);
        // 查询小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
        ArrayList<Chapter> finalChapter = new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            Chapter chapter = new Chapter();
            BeanUtils.copyProperties(eduChapter, chapter);
            ArrayList<Video> videoList = new ArrayList<>();
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo = eduVideoList.get(j);
                if (eduChapter.getId().equals(eduVideo.getChapterId())) {
                    Video video = new Video();
                    BeanUtils.copyProperties(eduVideo, video);
                    videoList.add(video);
                }
            }
            chapter.setChildren(videoList);
            finalChapter.add(chapter);
        }
        return finalChapter;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        if (count == 0)
            return this.removeById(chapterId);
        return false;
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        this.remove(chapterWrapper);
    }

}
