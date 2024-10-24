package com.hdu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.eduservice.client.VodClient;
import com.hdu.eduservice.entity.EduChapter;
import com.hdu.eduservice.entity.EduVideo;
import com.hdu.eduservice.mapper.EduVideoMapper;
import com.hdu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2024-07-13
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    // TODO 删除小节，同时删除对应视频
    @Override
    public void removeVideoByCourseId(String courseId) {
        // 根据课程id查询所有小节中视频id
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        videoWrapper.select("video_source_id");
        List<EduVideo> videos = this.list(videoWrapper);
        ArrayList<String> videoIds = new ArrayList<>();
        for (EduVideo video : videos) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId))
                videoIds.add(videoSourceId);
        }
        if (videoIds.size() > 0)
            vodClient.deleteBatch(videoIds);

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        this.remove(wrapper);
    }
}
