package com.fatehole.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/26/10:55
 */
public interface VodService {

    /**
     * 接受上传的视频闭关返回ID
     * @param file 视频文件
     * @return ID
     */
    String uploadVideo(MultipartFile file);

    /**
     * 通过云端视频id删除视频
     * @param id 云端视频id
     */
    void removeVideo(String id);

    /**
     * 批量删除视频
     * @param videoIdList ID集合
     */
    void removeMoreAliVideo(List<String> videoIdList);
}
