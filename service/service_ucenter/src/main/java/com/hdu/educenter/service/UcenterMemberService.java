package com.hdu.educenter.service;

import com.hdu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2024-07-27
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getOpenId(String openid);
}
