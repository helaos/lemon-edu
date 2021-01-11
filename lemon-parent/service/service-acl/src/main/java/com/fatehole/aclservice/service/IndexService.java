package com.fatehole.aclservice.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2021/01/11/14:01
 */
public interface IndexService {

    /**
     * 根据用户名获取用户登录信息
     * @param username 用户名
     * @return 登录信息
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     * @param username 用户名
     * @return 菜单列表
     */
    List<JSONObject> getMenu(String username);
}
