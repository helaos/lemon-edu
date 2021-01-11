package com.fatehole.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.fatehole.aclservice.service.IndexService;
import com.fatehole.commonutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2021/01/10/20:05
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {

    private IndexService indexService;

    @Autowired
    public void setIndexService(IndexService indexService) {
        this.indexService = indexService;
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/info")
    public Result info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return Result.ok().data(userInfo);
    }

    /**
     * 获取菜单
     */
    @GetMapping("/menu")
    public Result getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return Result.ok().data("permissionList", permissionList);
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }

}
