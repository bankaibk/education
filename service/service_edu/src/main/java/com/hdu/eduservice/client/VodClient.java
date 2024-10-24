package com.hdu.eduservice.client;

import com.hdu.commonutils.R;
import com.hdu.eduservice.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    // 定义调用方法路径
    @DeleteMapping("/eduVod/video/removeVideo/{id}")
    public R removeVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduVod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
