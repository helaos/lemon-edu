package com.fatehole.aclservice.service;

import com.fatehole.aclservice.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author helaos
 * @since 2020-12-22
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 递归查询所有菜单
     * @return 所有菜单的集合
     */
    List<Permission> queryAllMenu();

    /**
     * 递归删除菜单
     * @param id 菜单ID
     */
    void removeChildrenById(String id);

    /**
     * 给角色分配权限
     * @param roleId 角色ID
     * @param permissionList 权限集合
     */
    void saveRolePermissionRelationship(String roleId, String[] permissionList);
}
