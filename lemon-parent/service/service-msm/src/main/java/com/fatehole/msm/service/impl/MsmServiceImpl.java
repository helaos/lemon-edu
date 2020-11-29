package com.fatehole.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fatehole.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/29/20:45
 */

@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(String phone, Map<String, Object> param) {

        if (StringUtils.isEmpty(phone)) {
            return false;
        }

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "<accessKeyId>", "<accessSecret>");

        IAcsClient client = new DefaultAcsClient(profile);

        // 设置相关固定参数
        CommonRequest request = new CommonRequest();

        request.setSysMethod(MethodType.POST);

        request.setSysDomain("dysmsapi.aliyuncs.com");

        request.setSysVersion("2017-05-25");

        request.setSysAction("SendSms");

        // 设置发送相关的参数
        // 设置手机号
        request.putQueryParameter("PhoneNumbers", phone);

        // 短信签名名称。请在控制台签名管理页面签名名称一列查看。
        request.putQueryParameter("SignName", "<your SignName>");

        // 短信模板ID。请在控制台模板管理页面模板CODE一列查看。
        request.putQueryParameter("TemplateCode", "<your TemplateCode>");

        // 短信模板变量对应的实际值，JSON格式 例：{"code":"1111"}
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);

            return response.getHttpResponse().isSuccess();

        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
