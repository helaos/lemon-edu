package com.fatehole.statistics.controller;


import com.fatehole.commonutil.Result;
import com.fatehole.statistics.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-12-18
 */
@Api(tags = "统计数据管理")
@RestController
// @CrossOrigin
@RequestMapping("/statistics/daily")
public class StatisticsDailyController {

    private StatisticsDailyService dailyService;

    @Autowired
    public void setDailyService(StatisticsDailyService dailyService) {
        this.dailyService = dailyService;
    }

    @ApiOperation(value = "统计某一天注册的人数生成统计数据")
    @PostMapping("/{day}")
    public Result createStatisticsByDate(@PathVariable("day") String day) {
        // 统计信息
        dailyService.createStatisticsByDay(day);
        return Result.ok();
    }

    @ApiOperation(value = "图表显示数据")
    @GetMapping("/chart/{type}/{begin}/{end}")
    public Result showChart(@PathVariable("type") String type,
                            @PathVariable("begin") String begin,
                            @PathVariable("end") String end) {

        Map<String, Object> result =  dailyService.getShowData(type, begin, end);

        return Result.ok().data(result);
    }
}

