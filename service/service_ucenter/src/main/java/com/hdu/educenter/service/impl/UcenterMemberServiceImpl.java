package com.hdu.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.commonutils.JwtUtils;
import com.hdu.commonutils.MD5;
import com.hdu.educenter.entity.UcenterMember;
import com.hdu.educenter.entity.vo.RegisterVo;
import com.hdu.educenter.mapper.UcenterMemberMapper;
import com.hdu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.management.Query;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2024-07-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 登录
    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        // 需要将输入的密码进行加密
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password))
            throw new GuliException(20001, "字段有空值");
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember member = this.getOne(wrapper);
        if (member == null)
            throw new GuliException(20001, "用户不存在");
        if (!member.getPassword().equals(MD5.encrypt(password)))
            throw new GuliException(20001, "密码错误");
        if (member.getIsDisabled())
            throw new GuliException(20001, "用户已失效");
        // 生成token
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    // 注册
    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code))
            throw new GuliException(20001, "字段有空值");
        // 判断验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode))
            throw new GuliException(20001, "验证码错误");
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        if (this.count(wrapper) != 0)
            throw new GuliException(20001, "手机号已存在");
        UcenterMember ucenterMember = new UcenterMember();
        registerVo.setPassword(MD5.encrypt(password));
        BeanUtils.copyProperties(registerVo, ucenterMember);
        ucenterMember.setAvatar("https://lay0812.oss-cn-hangzhou.aliyuncs.com/2024/07/208abb59ea338549a09acfa241f7adc417naruto.jpg");
        this.save(ucenterMember);
    }

    // 根据openid查询，确保用户唯一
    @Override
    public UcenterMember getOpenId(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        return this.getOne(wrapper);
    }
}
