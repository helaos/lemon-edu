package com.fatehole.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatehole.order.entity.OmsOrder;
import com.fatehole.order.entity.OmsPayLog;
import com.fatehole.order.mapper.OmsPayLogMapper;
import com.fatehole.order.service.OmsOrderService;
import com.fatehole.order.service.OmsPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fatehole.order.util.ConstantProperties;
import com.fatehole.order.util.HttpClient;
import com.fatehole.servicebase.exception.LemonException;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-12-10
 */
@Service
public class OmsPayLogServiceImpl extends ServiceImpl<OmsPayLogMapper, OmsPayLog> implements OmsPayLogService {

    private OmsOrderService orderService;

    // private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setOrderService(OmsOrderService orderService) {
        this.orderService = orderService;
    }

    // @Autowired
    // public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
    //     this.redisTemplate = redisTemplate;
    // }

    @Override
    public Map<String, Object> createNative(String no) {
        try {
            // 根据订单id获取订单信息
            QueryWrapper<OmsOrder> wrapper = new QueryWrapper<>();

            wrapper.eq("order_no", no);

            OmsOrder order = orderService.getOne(wrapper);

            // 设置支付参数
            Map<String, String> map = new HashMap<>(16);

            map.put("appid", ConstantProperties.APP_ID);
            map.put("mch_id", ConstantProperties.PARTNER);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", no);
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", ConstantProperties.NOTIFY_URL);
            map.put("trade_type", "NATIVE");

            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //client设置参数

            client.setXmlParam(WXPayUtil.generateSignedXml(map, ConstantProperties.PARTNER_KEY));

            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、封装返回结果集
            Map<String, Object> result = new HashMap<>(16);
            result.put("out_trade_no", no);
            result.put("course_id", order.getCourseId());
            result.put("total_fee", order.getTotalFee());
            result.put("result_code", resultMap.get("result_code"));
            result.put("code_url", resultMap.get("code_url"));
            // 微信支付二维码2小时过期，可采取2小时未支付取消订单
            // redisTemplate.opsForValue().set(no, result, 120,TimeUnit.MINUTES);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LemonException(20001, "生成二维码失败");
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String no) {

        try {
            // 封装参数
            Map<String, String> result = new HashMap<>(16);

            result.put("appid", ConstantProperties.APP_ID);
            result.put("mch_id", ConstantProperties.PARTNER);
            result.put("out_trade_no", no);
            result.put("nonce_str", WXPayUtil.generateNonceStr());

            // 发送httpClient请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(result, ConstantProperties.PARTNER_KEY));
            client.setHttps(true);
            client.post();

            // 得到返回的内容
            String xml = client.getContent();
            // 转换成map
            return WXPayUtil.xmlToMap(xml);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        // 获取map中的订单号
        String orderNo = map.get("out_trade_no");

        // 根据订单id获取订单信息
        QueryWrapper<OmsOrder> wrapper = new QueryWrapper<>();

        wrapper.eq("order_no", orderNo);

        OmsOrder order = orderService.getOne(wrapper);

        // 更新订单表中的订单状态
        if (order.getStatus().intValue() == 1) {
            return;
        }

        // 1；已支付
        order.setStatus(1);
        orderService.updateById(order);

        // 向支付记录中添加记录
        OmsPayLog log = new OmsPayLog();
        log
                .setOrderNo(orderNo)
                .setPayTime(new Date())
                //支付类型
                .setPayType(1)
                .setTotalFee(order.getTotalFee())
                // 支付状态
                .setTradeState(map.get("trade_state"))
                .setTransactionId(map.get("transaction_id"))
                .setAttribute(JSONObject.toJSONString(map));

        baseMapper.insert(log);

    }
}
