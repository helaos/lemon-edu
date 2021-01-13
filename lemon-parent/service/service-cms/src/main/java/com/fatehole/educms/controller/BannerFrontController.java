package com.fatehole.educms.controller;


import com.fatehole.commonutil.Result;
import com.fatehole.educms.entity.CrmBanner;
import com.fatehole.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台首页banner表 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-11-27
 */
// @CrossOrigin
@RestController
@RequestMapping("/educms/front/banner")
public class BannerFrontController {

    private CrmBannerService bannerService;

    @Autowired
    public void setBannerService(CrmBannerService bannerService) {
        this.bannerService = bannerService;
    }

    @ApiOperation(value = "查询所有的Banner")
    @GetMapping("")
    public Result getAllBanner() {

        List<CrmBanner> banners = bannerService.selectAllBanner();

        return Result.ok().data("item", banners);
    }
}

