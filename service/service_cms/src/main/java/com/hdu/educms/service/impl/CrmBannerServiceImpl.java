package com.hdu.educms.service.impl;

import com.hdu.educms.entity.CrmBanner;
import com.hdu.educms.mapper.CrmBannerMapper;
import com.hdu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2024-07-22
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectBannerList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        return this.list(null);
    }
}
