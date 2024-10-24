package com.hdu.eduservice.service;

import com.hdu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2024-07-13
 */
public interface EduVideoService extends IService<EduVideo> {
    void removeVideoByCourseId(String courseId);
}
