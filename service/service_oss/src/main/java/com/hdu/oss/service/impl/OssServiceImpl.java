package com.hdu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hdu.oss.service.OssService;
import com.hdu.oss.utils.PropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadAvatar(MultipartFile file) {
        String endpoint = PropertiesUtil.END_POINT;
        String bucketName = PropertiesUtil.BUCKET_NAME;
        String keyId = PropertiesUtil.KEY_ID;
        String keySecret = PropertiesUtil.KEY_SECRET;
        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid + filename;
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath + filename;
            ossClient.putObject(bucketName, filename, inputStream);
            ossClient.shutdown();
            return "https://" + bucketName + "." + endpoint + "/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
