package com.fatehole.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/16/17:01
 */

@Component
@FeignClient(value = "service-order", fallback = OrderDegradeFeignClient.class)
public interface OrderClient {

    /**
     * 判断是否支付
     * @param courseId 课程ID
     * @param memberId 用户ID
     * @return 是与否
     */
    @GetMapping("/eduorder/order/buy/status/{courseId}/{memberId}")
    Boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
