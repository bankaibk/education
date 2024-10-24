package com.hdu.eduservice.service;

import com.hdu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.eduservice.entity.chapter.Chapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2024-07-13
 */
public interface EduChapterService extends IService<EduChapter> {

    List<Chapter> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
