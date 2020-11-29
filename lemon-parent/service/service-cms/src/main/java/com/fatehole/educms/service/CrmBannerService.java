package com.fatehole.educms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fatehole.educms.entity.vo.BannerQuery;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-11-27
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有的Banner
     * @return 包含所有Banner的集合
     */
    List<CrmBanner> selectAllBanner();

    /**
     * 待条件的分页查询
     * @param pageParam 分页
     * @param bannerQuery 分页条件
     */
    void pageQuery(Page<CrmBanner> pageParam, BannerQuery bannerQuery);
}
