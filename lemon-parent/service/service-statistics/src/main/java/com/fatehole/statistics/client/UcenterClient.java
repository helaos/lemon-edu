package com.fatehole.statistics.client;

import com.baomidou.mybatisplus.extension.api.R;
import com.fatehole.commonutil.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/18/13:08
 */
@Component
@FeignClient(value = "service-ucenter", fallback = UcenterDegradeFeignClient.class)
public interface UcenterClient {

    /**
     * 根据日期查询当天注册人数
     * @param day 日期
     * @return 人数
     */
    @GetMapping(value = "/educenter/member/count/{day}")
    Result registerCount(@PathVariable("day") String day);

}
