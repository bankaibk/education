package com.hdu.eduservice.client.impl;

import com.hdu.commonutils.R;
import com.hdu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeVideo(String id) {
        return R.error().message("time out");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("time out");
    }
}
