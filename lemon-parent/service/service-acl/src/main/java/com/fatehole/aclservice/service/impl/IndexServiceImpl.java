package com.fatehole.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fatehole.aclservice.entity.Role;
import com.fatehole.aclservice.entity.User;
import com.fatehole.aclservice.service.IndexService;
import com.fatehole.aclservice.service.PermissionService;
import com.fatehole.aclservice.service.RoleService;
import com.fatehole.aclservice.service.UserService;

import com.fatehole.servicebase.exception.LemonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2021/01/11/14:02
 */
@Service
public class IndexServiceImpl implements IndexService {

    private UserService userService;

    private RoleService roleService;

    private PermissionService permissionService;

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>(16);
        User user = userService.selectByUsername(username);
        if (null == user) {
            throw new LemonException();
        }

        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        result.put("name", user.getUsername());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    @Override
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);

        //根据用户id获取用户菜单权限
        return permissionService.selectPermissionByUserId(user.getId());
    }
}
