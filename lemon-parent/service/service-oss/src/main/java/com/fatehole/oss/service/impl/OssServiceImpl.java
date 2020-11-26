package com.fatehole.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.fatehole.oss.service.OssService;
import com.fatehole.oss.util.ConstantProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.attribute.FileTime;
import java.util.UUID;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/15/19:57
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantProperties.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantProperties.ACCESS_KEY_ID;
        String accessKeySecret = ConstantProperties.ACCESS_KEY_SECRET;
        String bucketName = ConstantProperties.BUCKET_NAME;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();

            // 在文件名中添加随机的唯一值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            // 文件按日期分类在
            String datePath = new DateTime().toString("yyyy/MM/dd");

            // 分类文件夹
            String classification = "picture";

            // 获取文件原名
            String originalFilename = file.getOriginalFilename();

            // 截取类型
            String fileType = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";

            // 获取文件名称
            String filename = classification + "/" + datePath + "/" + uuid + "file" + fileType;

            // 实现上传
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            return "https://" + bucketName + "." + endpoint + "/" + filename;

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }
}
