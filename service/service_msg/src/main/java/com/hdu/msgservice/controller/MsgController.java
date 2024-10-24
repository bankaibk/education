package com.hdu.msgservice.controller;

import com.hdu.commonutils.R;
import com.hdu.msgservice.service.MsgService;
import com.hdu.msgservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("eduMsg/msg")
@CrossOrigin
public class MsgController {
    @Autowired
    private MsgService msgService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("send/{phone}")
    public R sendMsg(@PathVariable String phone) {
        // 从redis获取验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        // 获取不到重新发送
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        // 调用发送短信方法
        boolean flag = msgService.send(param, phone);
        if (flag) {
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        }
        return R.error().message("短信发送失败");
    }
}
