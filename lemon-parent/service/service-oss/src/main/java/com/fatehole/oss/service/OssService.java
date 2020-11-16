package com.fatehole.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/15/19:57
 */
public interface OssService {

    /**
     * 文件上传
     * @param file 文件
     * @return 返回oss的上传路径
     */
    String uploadFileAvatar(MultipartFile file);
}
