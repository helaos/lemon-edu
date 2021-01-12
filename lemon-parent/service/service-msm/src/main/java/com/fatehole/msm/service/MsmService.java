package com.fatehole.msm.service;

import java.util.Map;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/29/20:40
 */
public interface MsmService {

    /**
     * 发送验证码到阿里云进行发送短信
     * @param phone 手机号
     * @param param 验证码集合
     * @return 是否成功发送
     */
    boolean send(String phone, Map<String, Object> param);
}
