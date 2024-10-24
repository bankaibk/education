package com.hdu.educenter.controller;

import com.google.gson.Gson;
import com.hdu.commonutils.JwtUtils;
import com.hdu.educenter.entity.UcenterMember;
import com.hdu.educenter.service.UcenterMemberService;
import com.hdu.educenter.utils.HttpClientUtil;
import com.hdu.educenter.utils.PropertiesUtil;
import com.hdu.servicebase.handler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService memberService;

    // 微信扫码登录
    @GetMapping("callback")
    public String callback(String code, String state) {
        try {
            // 获取code，临时票据
            // 使用code请求微信固定地址，获取access_token、openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    PropertiesUtil.WX_OPEN_APP_ID,
                    PropertiesUtil.WX_OPEN_APP_SECRET,
                    code);
            // 请求accessTokenUrl
            // 使用httpclient发送请求，得到结果
            String accessTokenInfo = HttpClientUtil.get(accessTokenUrl);
            // 从字符串中获取access_token、openid
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");
            UcenterMember member = memberService.getOpenId(openid);
            if (member == null) {// 根据access_token、openid访问地址，获取用户信息
                String baseUserUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseUserUrl,
                        access_token,
                        openid);
                String userInfo = HttpClientUtil.get(userInfoUrl);
                HashMap user = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) user.get("nickname");
                String headimgurl = (String) user.get("headimgurl");
                member = new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            // 登录
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token=" + token;
        } catch (Exception e) {
            throw new GuliException(20001, "登陆失败");
        }
    }

    @GetMapping("login")
    public String getWxCode() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redict";
        String redirectUrl = PropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(baseUrl,
                PropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "hdu");
        // 请求wx地址，重定向
        return "redirect:" + url;
    }
}
