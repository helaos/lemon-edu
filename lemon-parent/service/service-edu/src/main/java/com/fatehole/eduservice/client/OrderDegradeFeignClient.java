package com.fatehole.eduservice.client;

import org.springframework.stereotype.Component;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/16/17:04
 */
@Component
public class OrderDegradeFeignClient implements OrderClient {
    @Override
    public Boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
