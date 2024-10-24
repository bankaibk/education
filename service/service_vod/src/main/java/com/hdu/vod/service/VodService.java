package com.hdu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file) throws IOException;

    void removeMoreVideo(List videoIdList);
}
