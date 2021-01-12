package com.fatehole.order.controller;


import com.fatehole.commonutil.Result;
import com.fatehole.order.service.OmsPayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-12-10
 */
@CrossOrigin
@RestController
@RequestMapping("/eduorder/pay")
public class OmsPayLogController {

    private OmsPayLogService payLogService;

    @Autowired
    public void setPayLogService(OmsPayLogService payLogService) {
        this.payLogService = payLogService;
    }

    @ApiOperation(value = "生成微信支付的二维码")
    @GetMapping("/{no}")
    public Result createNative(@PathVariable("no") String no) {
        // 返回信息，包含二维码地址以及其他信息
        Map<String, Object> result = payLogService.createNative(no);
        System.out.println("返回的map" + result);
        return Result.ok().data(result);
    }

    @ApiOperation(value = "查询订单的支付状态")
    @GetMapping("/status/{no}")
    public Result queryPayStatus(@PathVariable("no") String no) {
        Map<String, String> map =  payLogService.queryPayStatus(no);
        System.out.println("查询订单状态的Map" + map);
        if (map == null) {
            return Result.error().message("支付出差哟·~");
        }

        // 如果返回的map里面不为空，通过map获取订单状态
        if ("SUCCESS".equals(map.get("trade_state"))) {
            // 添加记录到支付表，更新订单支付状态
            payLogService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }
        return Result.ok().code(25000).message("订单还未支付~");
    }
}

