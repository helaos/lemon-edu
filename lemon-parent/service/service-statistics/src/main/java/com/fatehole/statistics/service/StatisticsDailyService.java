package com.fatehole.statistics.service;

import com.fatehole.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-12-18
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 根据日期统计信息
     * @param day 日期
     */
    void createStatisticsByDay(String day);

    /**
     * 图表显示，返回两部分数据，日期json数组，数量json数组
     * @param type 类型
     * @param begin 开始时间
     * @param end 结束时间
     * @return 数据
     */
    Map<String, Object> getShowData(String type, String begin, String end);
}
