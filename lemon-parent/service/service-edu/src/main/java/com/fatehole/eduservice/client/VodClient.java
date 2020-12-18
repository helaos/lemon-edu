package com.fatehole.eduservice.client;

import com.fatehole.commonutil.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/26/20:52
 */
@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    /**
     * service-vod服务中的删除云服务视频
     * @param id 视频ID
     * @return 统一返回
     */
    @DeleteMapping("/eduvod/video/{id}")
    Result removeAliVideoById(@PathVariable("id") String id);

    /**
     * 批量删除视频
     * @param videoIdList 视频ID集合
     * @return 统一返回
     */
    @DeleteMapping("/eduvod/video/batch")
    Result removeBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
