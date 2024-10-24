package com.hdu.eduorder.controller;


import com.hdu.commonutils.R;
import com.hdu.eduorder.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2024-07-28
 */
@RestController
@RequestMapping("/eduOrder/payLog")
@CrossOrigin
public class TPayLogController {
    @Autowired
    private TPayLogService payLogService;

    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("出错了");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().message("支付中");
    }
}

