package com.hdu.eduorder.service;

import com.hdu.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2024-07-28
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String memberId);
}
