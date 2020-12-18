package com.fatehole.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatehole.statistics.client.UcenterClient;
import com.fatehole.statistics.entity.StatisticsDaily;
import com.fatehole.statistics.mapper.StatisticsDailyMapper;
import com.fatehole.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-12-18
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    private UcenterClient ucenterClient;

    @Autowired
    public void setUcenterClient(UcenterClient ucenterClient) {
        this.ucenterClient = ucenterClient;
    }

    @Override
    public void createStatisticsByDay(String day) {

        // 删除已存在的统计对象
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();

        wrapper.eq("date_calculated", day);

        baseMapper.delete(wrapper);

        // 获取统计信息
        Integer count = (Integer) ucenterClient.registerCount(day).getData().get("count");

        StatisticsDaily daily = new StatisticsDaily();
        daily
                .setRegisterNum(count)
                .setDateCalculated(day)
                // TODO: 以后改为真实数据
                .setVideoViewNum(RandomUtils.nextInt(100, 200))
                .setLoginNum(RandomUtils.nextInt(100, 200))
                .setCourseNum(RandomUtils.nextInt(100, 200));

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        // 根据条件查询你对应条件
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();

        wrapper.select(type, "date_calculated");
        wrapper.between("date_calculated", begin, end);


        List<StatisticsDaily> dailies = baseMapper.selectList(wrapper);

        List<String> calculated = new ArrayList<>();

        List<Integer> numDataList = new ArrayList<>();

        for (StatisticsDaily daily : dailies) {
            calculated.add(daily.getDateCalculated());

            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> result = new HashMap<>(16);
        result.put("calculated", calculated);
        result.put("numDataList", numDataList);
        return result;
    }
}
