package com.fatehole.statistics.client;

import com.fatehole.commonutil.Result;
import org.springframework.stereotype.Component;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/18/13:10
 */
@Component
public class UcenterDegradeFeignClient implements UcenterClient{
    @Override
    public Result registerCount(String day) {
        return Result.error().message("调用失败");
    }
}
