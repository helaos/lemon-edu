package com.fatehole.eduservice.client;

import com.fatehole.commonutil.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/27/14:06
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Result removeAliVideoById(String id) {
        return Result.error().message("删除视频出错了！");
    }

    @Override
    public Result removeBatch(List<String> videoIdList) {
        return Result.error().message("删除多个视频出错了！");
    }
}
