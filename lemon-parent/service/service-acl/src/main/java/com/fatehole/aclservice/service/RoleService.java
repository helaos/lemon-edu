package com.fatehole.aclservice.service;

import com.fatehole.aclservice.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-12-22
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户获取角色数据
     * @param userId 用户ID
     * @return 角色数据
     */
    Map<String, Object> findRoleByUserId(String userId);

    /**
     * 根据用户分配角色
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void saveUserRoleRelationship(String userId, String[] roleId);

    /**
     *
     * @param id
     * @return
     */
    List<Role> selectRoleByUserId(String id);
}
