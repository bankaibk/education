package com.hdu.eduorder.client;

import com.hdu.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @GetMapping("/eduCenter/member/getMemberInfoOrder/{id}")
    public UcenterMemberOrder getMemberInfoOrder(@PathVariable("id") String id);
}
