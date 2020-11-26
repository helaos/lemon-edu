package com.fatehole.vod.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/26/11:09
 */
public class AliyunVodSdkUtil {

    public static DefaultAcsClient initVodClient(String accessKeyId,
                                                 String accessKeySecret) throws ClientException {
        // 点播服务接入区域
        String regionId = "cn-shanghai";

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);

        return new DefaultAcsClient(profile);
    }
}
