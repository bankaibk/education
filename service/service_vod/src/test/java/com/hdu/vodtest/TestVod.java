package com.hdu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    // 上传视频
    public static void main(String[] args) {
        String accessKeyId = "";
        String accessKeySecret = "";
        String title = "testVideoUpload";
        String fileName = "C:\\Users\\lay\\Downloads\\AliyunVodDemoVideo.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        // 分片大小每片大小
        request.setPartSize(2 * 1024 * 1024L);
        request.setTaskNum(1);
        // 上传接口
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    // 获取视频auth
    public static void getPlayAuth() throws ClientException {
        // 初始化对象
        DefaultAcsClient client = InitObject.initVodClient("", "");
        // 创建获取视频auth的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("d0e5f95f451471ef802b0764b3ec0102");
        response = client.getAcsResponse(request);
        System.out.println(response.getPlayAuth());
    }

    // 获取视频url
    public static void getPlayUrl() throws ClientException {
        // 初始化对象
        DefaultAcsClient client = InitObject.initVodClient("", "");
        // 创建获取视频url的request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        request.setVideoId("d0e5f95f451471ef802b0764b3ec0102");
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println(playInfo.getPlayURL());
        }
        System.out.println(response.getVideoBase().getTitle());
    }
}
