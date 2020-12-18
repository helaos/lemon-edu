package com.fatehole.order.client;

import com.fatehole.commonutil.vo.CommentInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/09/8:31
 */

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    /**
     * 根据token字符串获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @PostMapping("/educenter/member/{id}")
    CommentInfoVo getMemberInfo(@PathVariable("id") String id);
}
