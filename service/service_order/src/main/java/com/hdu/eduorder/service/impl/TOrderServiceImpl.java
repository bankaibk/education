package com.hdu.eduorder.service.impl;

import com.hdu.commonutils.ordervo.CourseWebOrderVo;
import com.hdu.commonutils.ordervo.UcenterMemberOrder;
import com.hdu.eduorder.client.EduClient;
import com.hdu.eduorder.client.UcenterClient;
import com.hdu.eduorder.entity.TOrder;
import com.hdu.eduorder.mapper.TOrderMapper;
import com.hdu.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2024-07-28
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        // 通过远程调用，获取用户以及课程信息
        UcenterMemberOrder memberInfoOrder = ucenterClient.getMemberInfoOrder(memberId);
        CourseWebOrderVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(memberInfoOrder.getMobile());
        order.setNickname(memberInfoOrder.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        this.save(order);
        return order.getOrderNo();
    }
}
