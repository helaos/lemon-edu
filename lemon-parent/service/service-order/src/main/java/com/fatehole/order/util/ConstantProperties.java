package com.fatehole.order.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/11/15/19:17
 */
@Component
public class ConstantProperties implements InitializingBean {

    // 读取配置文件中的内容

    @Value("${weixin.pay.appid}")
    private String appid;

    @Value("${weixin.pay.partner}")
    private String partner;

    @Value("${weixin.pay.partnerkey}")
    private String partnerkey;

    @Value("${weixin.pay.notifyurl}")
    private String notifyurl;

    public static String APP_ID;
    public static String PARTNER;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;


    @Override
    public void afterPropertiesSet() {
        APP_ID = appid;
        PARTNER = partner;
        PARTNER_KEY = partnerkey;
        NOTIFY_URL = notifyurl;
    }
}
