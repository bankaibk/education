package com.hdu.eduorder.client;

import com.hdu.commonutils.ordervo.CourseWebOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {
    @GetMapping("/eduService/courseFront/getCourseInfoOrder/{id}")
    public CourseWebOrderVo getCourseInfoOrder(@PathVariable("id") String id);
}
