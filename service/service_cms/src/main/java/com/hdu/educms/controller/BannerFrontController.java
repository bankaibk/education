package com.hdu.educms.controller;


import com.hdu.educms.entity.CrmBanner;
import com.hdu.educms.service.CrmBannerService;
import com.hdu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduCms/bannerFront")
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list", list);
    }
}
