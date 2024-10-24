package com.hdu.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdu.commonutils.JwtUtils;
import com.hdu.commonutils.R;
import com.hdu.eduorder.entity.TOrder;
import com.hdu.eduorder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2024-07-28
 */
@RestController
@RequestMapping("/eduOrder/order")
@CrossOrigin
public class TOrderController {
    @Autowired
    private TOrderService orderService;

    //根据课程id和用户id创建订单，返回订单id
    @GetMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        // 返回订单号
        String orderId = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    // 根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("order", order);
    }
}

