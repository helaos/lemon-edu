package com.fatehole.order.client;

import com.fatehole.commonutil.vo.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/10/20:41
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    /**
     * 获取信息
     * @param id 课程ID
     * @return 课程信息
     */
    @PostMapping("/eduservice/front/course/info/{id}")
    CourseOrderVo getCourseInfoToOrder(@PathVariable("id") String id);
}
