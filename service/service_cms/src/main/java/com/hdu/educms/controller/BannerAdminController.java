package com.hdu.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdu.educms.entity.CrmBanner;
import com.hdu.educms.service.CrmBannerService;
import com.hdu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2024-07-22
 */
@RestController
@RequestMapping("/eduCms/banner")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        crmBannerService.page(pageBanner, null);
        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    @PostMapping("addBanner")
    public R save(@RequestBody CrmBanner banner) {
        crmBannerService.save(banner);
        return R.ok();
    }

    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return R.ok();
    }

    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }
}

