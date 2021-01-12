package com.fatehole.order.service;

import com.fatehole.order.entity.OmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-12-10
 */
public interface OmsOrderService extends IService<OmsOrder> {

    /**
     * 生成订单
     * @param id 课程ID
     * @param memberIdByJwtToken 会员ID
     * @return 订单号
     */
    String createOrders(String id, String memberIdByJwtToken);
}
