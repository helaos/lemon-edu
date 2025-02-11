package com.fatehole.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.fatehole.servicebase.exception.LemonException;
import com.fatehole.vod.service.VodService;
import com.fatehole.vod.util.AliyunVodSdkUtil;
import com.fatehole.vod.util.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/26/10:55
 */

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();

            String originalFilename = file.getOriginalFilename();

            assert originalFilename != null;
            // title上传之后显示的名称
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();

            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();

            if (!response.isSuccess()) {

                String errorMessage = "阿里云上传错误：" + "code：" +
                        response.getCode() + ", message：" + response.getMessage();

                if(StringUtils.isEmpty(videoId)){

                    throw new LemonException(20001, errorMessage);

                }
            }
            return videoId;

        } catch (IOException e) {

            throw new LemonException(20001, "Lemon vod 服务上传失败");

        }
    }

    @Override
    public void removeVideo(String id) {
        try{
            DefaultAcsClient client = AliyunVodSdkUtil.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(id);

            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

        }catch (ClientException e){

            throw new LemonException(20001, "视频删除失败");

        }
    }

    @Override
    public void removeMoreAliVideo(List<String> videoIdList) {
        try {
            //初始化
            DefaultAcsClient client = AliyunVodSdkUtil.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //创建请求对象
            //一次只能批量删20个
            String str = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");

            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(str);

            //获取响应
            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

        } catch (ClientException e) {

            throw new LemonException(20001, "视频删除失败");

        }
    }
}
