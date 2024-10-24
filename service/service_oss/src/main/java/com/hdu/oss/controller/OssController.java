package com.hdu.oss.controller;

import com.hdu.commonutils.R;
import com.hdu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduOss/fileOss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        String url = ossService.uploadAvatar(file);
        return R.ok().data("url", url);
    }
}
