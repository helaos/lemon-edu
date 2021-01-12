package com.fatehole.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.educms.entity.CrmBanner;
import com.fatehole.educms.entity.vo.BannerQuery;
import com.fatehole.educms.mapper.CrmBannerMapper;
import com.fatehole.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-11-27
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(key = "'selectIndexList'", value = "banner")
    @Override
    public List<CrmBanner> selectAllBanner() {

        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();

        // 拼接语句
        wrapper
            .orderByDesc("id")
            .last("limit 3");



        return baseMapper.selectList(wrapper);
    }

    @Override
    public void pageQuery(Page<CrmBanner> pageParam, BannerQuery bannerQuery) {

        // 构造条件
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();

        // 排序
        wrapper.orderByAsc("sort");

        if (bannerQuery == null){
            baseMapper.selectPage(pageParam, wrapper);
            return;
        }

        // 获取条件参数
        String title = bannerQuery.getTitle();
        String linkUrl = bannerQuery.getLinkUrl();

        // 多条件组合查询
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(linkUrl)) {
            wrapper.like("link_url", linkUrl);
        }

        baseMapper.selectPage(pageParam, wrapper);
    }
}
