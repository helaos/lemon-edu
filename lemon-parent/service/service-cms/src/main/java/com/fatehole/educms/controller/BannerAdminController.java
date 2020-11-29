package com.fatehole.educms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fatehole.commonutil.Result;
import com.fatehole.educms.entity.CrmBanner;
import com.fatehole.educms.entity.vo.BannerQuery;
import com.fatehole.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台首页banner表 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-11-27
 */
@Api(tags = "管理Banner")
@CrossOrigin
@RestController
@RequestMapping("/educms/admin/banner")
public class BannerAdminController {

    private CrmBannerService bannerService;

    @Autowired
    public void setBannerService(CrmBannerService bannerService) {
        this.bannerService = bannerService;
    }

    @ApiOperation(value = "获取Banner分页列表")
    @PostMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "limit", value = "每页记录数")
                        @RequestBody BannerQuery bannerQuery) {

        Page<CrmBanner> pageParam = new Page<>(page, limit);

        bannerService.pageQuery(pageParam, bannerQuery);

        Map<String, Object> map = new HashMap<>(2);

        // 封装结果集
        map.put("total", pageParam.getTotal());
        map.put("rows", pageParam.getRecords());

        return Result.ok().data(map);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("")
    public Result addBanner(@ApiParam(name = "banner", value = "CrmBanner", required = true)
                            @RequestBody CrmBanner banner) {

        boolean flag = bannerService.save(banner);

        if (flag) {
            return Result.ok();
        } else {
            return Result.error().message("添加失败！");
        }
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/{id}")
    public Result removeBanner(@ApiParam(name = "id", value = "BannerId", required = true)
                               @PathVariable("id") String id) {

        boolean flag = bannerService.removeById(id);

        if (flag) {
            return Result.ok();
        } else {
            return Result.error().message("删除失败");
        }
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("")
    public Result updateBanner(@ApiParam(name = "banner", value = "修改信息", required = true)
                               @RequestBody CrmBanner banner) {

        boolean flag = bannerService.updateById(banner);

        if (flag) {
            return Result.ok();
        } else {
            return Result.error().message("修改失败");
        }
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("/{id}")
    public Result obtainBanner(@ApiParam(name = "id", value = "BannerID", required = true)
                               @PathVariable("id") String id) {

        CrmBanner banner = bannerService.getById(id);

        return Result.ok().data("item", banner);
    }

}

