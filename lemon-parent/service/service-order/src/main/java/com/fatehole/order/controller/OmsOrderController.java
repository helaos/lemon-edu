package com.fatehole.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatehole.commonutil.JwtUtils;
import com.fatehole.commonutil.Result;
import com.fatehole.order.entity.OmsOrder;
import com.fatehole.order.service.OmsOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-12-10
 */
// @CrossOrigin
@RestController
@RequestMapping("/eduorder/order")
public class OmsOrderController {

    private OmsOrderService orderService;

    @Autowired
    public void setOrderService(OmsOrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "根据课程ID和用户ID创建订单，返回订单ID")
    @PostMapping("/{id}")
    public Result saveOrder(@ApiParam(name = "id", value = "课程ID", required = true)
                            @PathVariable("id") String id,
                            HttpServletRequest request) {

        // 创建订单，返回订单号
        String orderNo = orderService.createOrders(id, JwtUtils.getMemberIdByJwtToken(request));
        return Result.ok().data("no", orderNo);

    }

    @ApiOperation(value = "根据订单ID查询订单")
    @GetMapping("/{id}")
    public Result getOrderInfo(@PathVariable("id") String orderNo) {

        QueryWrapper<OmsOrder> wrapper = new QueryWrapper<>();

        wrapper.eq("order_no", orderNo);

        OmsOrder info = orderService.getOne(wrapper);

        return Result.ok().data("item", info);
    }

    @ApiOperation(value = "根据课程ID和用户ID查询订单表中的订单状态")
    @GetMapping("/buy/status/{courseId}/{memberId}")
    public Boolean isBuyCourse(@ApiParam(name = "courseId", value = "课程ID", required = true)
                               @PathVariable("courseId") String courseId,
                               @ApiParam(name = "memberId", value = "用户ID", required = true)
                               @PathVariable("memberId") String memberId) {

        QueryWrapper<OmsOrder> wrapper = new QueryWrapper<>();

        wrapper
                .eq("course_id", courseId)
                .eq("member_id", memberId)
                // 支付状态
                .eq("status", 1);

        int count = orderService.count(wrapper);
        System.out.println("????? + " + count);
        return count == 1;

    }
}

