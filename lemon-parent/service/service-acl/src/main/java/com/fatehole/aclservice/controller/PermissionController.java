package com.fatehole.aclservice.controller;


import com.fatehole.aclservice.entity.Permission;
import com.fatehole.aclservice.service.PermissionService;
import com.fatehole.commonutil.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author helaos
 * @since 2020-12-22
 */
// @CrossOrigin
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    private PermissionService permissionService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiOperation("查询所有的菜单")
    @GetMapping("")
    public Result indexAllPermission() {
        List<Permission> list = permissionService.queryAllMenu();
        return Result.ok().data("children", list);
    }

    @ApiOperation("递归删除菜单")
    @DeleteMapping("/{id}")
    public Result removePermission(@PathVariable("id") String id) {
        permissionService.removeChildrenById(id);
        return Result.ok();
    }

    @ApiOperation("角色添加权限")
    @PostMapping("/doAssign")
    public Result doAssign(String roleId, String[] permissionList) {
        permissionService.saveRolePermissionRelationship(roleId, permissionList);
        return Result.ok();
    }

    @ApiOperation("根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable("roleId") String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return Result.ok().data("children", list);
    }

    @ApiOperation("新增菜单")
    @PostMapping("")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok();
    }

    @ApiOperation("修改菜单")
    @PutMapping("")
    public Result updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok();
    }

}

