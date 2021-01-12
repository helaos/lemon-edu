package com.fatehole.statistics.schedule;

import com.fatehole.statistics.service.StatisticsDailyService;
import com.fatehole.statistics.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2020/12/18/14:22
 */
@Component
public class ScheduledTask {

    private StatisticsDailyService dailyService;

    @Autowired
    public void setDailyService(StatisticsDailyService dailyService) {
        this.dailyService = dailyService;
    }

    /**
     * 每天凌晨1点执行定时
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        dailyService.createStatisticsByDay(day);
    }

}
