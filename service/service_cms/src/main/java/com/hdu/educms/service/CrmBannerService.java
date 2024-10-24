package com.hdu.educms.service;

import com.hdu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2024-07-22
 */
public interface CrmBannerService extends IService<CrmBanner> {
    List<CrmBanner> selectAllBanner();
}
