package com.hdu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hdu.commonutils.R;
import com.hdu.servicebase.handler.GuliException;
import com.hdu.vod.service.VodService;
import com.hdu.vod.utils.InitVodClient;
import com.hdu.vod.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/eduVod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) {
        String videoId = null;
        try {
            videoId = vodService.uploadVideo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok().data("videoId", videoId);
    }

    // 根据视频id删除阿里云视频
    @DeleteMapping("removeVideo/{id}")
    public R removeVideo(@PathVariable String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(PropertiesUtil.KEY_ID, PropertiesUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreVideo(videoIdList);
        return R.ok();
    }

    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            // 初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(PropertiesUtil.KEY_ID, PropertiesUtil.KEY_SECRET);
            // 创建获取视频auth的request和response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            request.setVideoId(id);
            response = client.getAcsResponse(request);
            String auth = response.getPlayAuth();
            return R.ok().data("auth", auth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }
}
