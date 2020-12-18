package com.fatehole.order.service.impl;

import com.fatehole.commonutil.vo.CommentInfoVo;
import com.fatehole.commonutil.vo.CourseOrderVo;
import com.fatehole.order.client.EduClient;
import com.fatehole.order.client.UcenterClient;
import com.fatehole.order.entity.OmsOrder;
import com.fatehole.order.mapper.OmsOrderMapper;
import com.fatehole.order.service.OmsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fatehole.order.util.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-12-10
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {

    private EduClient eduClient;

    private UcenterClient ucenterClient;

    @Autowired
    public void setEduClient(EduClient eduClient) {
        this.eduClient = eduClient;
    }

    @Autowired
    public void setUcenterClient(UcenterClient ucenterClient) {
        this.ucenterClient = ucenterClient;
    }

    @Override
    public String createOrders(String id, String memberIdByJwtToken) {

        // 通过调用远程接口获取用户信息
        CommentInfoVo memberInfo = ucenterClient.getMemberInfo(memberIdByJwtToken);

        // 通过调用远程接口获取课程信息
        CourseOrderVo courseInfo = eduClient.getCourseInfoToOrder(id);

        OmsOrder order = new OmsOrder();

        // 设置值
        order
                .setOrderNo(OrderNoUtil.getOrderNo())
                .setCourseId(id)
                .setCourseTitle(courseInfo.getTitle())
                .setCourseCover(courseInfo.getCover())
                .setTeacherName(courseInfo.getTeacherName())
                .setTotalFee(courseInfo.getPrice())
                .setMemberId(memberIdByJwtToken)
                .setMobile(memberInfo.getMobile())
                .setNickname(memberInfo.getNickname())
                .setStatus(0)
                .setPayType(1);

        baseMapper.insert(order);


        return order.getOrderNo();
    }
}
