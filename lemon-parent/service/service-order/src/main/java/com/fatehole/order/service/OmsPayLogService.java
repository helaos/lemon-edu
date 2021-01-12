package com.fatehole.order.service;

import com.fatehole.order.entity.OmsPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-12-10
 */
public interface OmsPayLogService extends IService<OmsPayLog> {

    /**
     * 生成二维码以及相关信息
     * @param no 订单号
     * @return 维码以及相关信息
     */
    Map<String, Object> createNative(String no);

    /**
     * 根据订单号查询订单的支付状态
     * @param no 订单号
     * @return 状态
     */
    Map<String, String> queryPayStatus(String no);

    /**
     * 修改表中添加记录，并更新订单表中的支付转状态
     * @param map 状态信息
     */
    void updateOrderStatus(Map<String, String> map);
}
