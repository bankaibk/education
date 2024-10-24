package com.hdu.educenter.controller;


import com.hdu.commonutils.JwtUtils;
import com.hdu.commonutils.R;
import com.hdu.commonutils.ordervo.UcenterMemberOrder;
import com.hdu.educenter.entity.UcenterMember;
import com.hdu.educenter.entity.vo.RegisterVo;
import com.hdu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2024-07-27
 */
@RestController
@RequestMapping("/eduCenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    // 登录
    @PostMapping("login")
    public R login(@RequestBody UcenterMember ucenterMember) {
        String token = memberService.login(ucenterMember);
        System.out.println(token);
        return R.ok().data("token", token);
    }

    // 注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    // 显示昵称和头像
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("memberInfo", member);
    }

    @GetMapping("getMemberInfoOrder/{id}")
    public UcenterMemberOrder getMemberInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder memberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, memberOrder);
        return memberOrder;
    }
}

