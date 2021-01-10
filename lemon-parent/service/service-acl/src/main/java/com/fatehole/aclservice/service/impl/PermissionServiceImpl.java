package com.fatehole.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fatehole.aclservice.entity.Permission;
import com.fatehole.aclservice.entity.RolePermission;
import com.fatehole.aclservice.mapper.PermissionMapper;
import com.fatehole.aclservice.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fatehole.aclservice.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-12-22
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private static final String NODE_ID = "0";

    private RolePermissionService rolePermissionService;

    @Autowired
    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @Override
    public List<Permission> queryAllMenu() {

        // 查询菜单中的所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);

        // 把查出的list集合进行封装
        return buildPermission(permissionList);

    }

    @Override
    public void removeChildrenById(String id) {
        // 创建list集合，用于封装所有删除菜单ID值
        List<String> idList = new ArrayList<>();

        // 向idList集合设置删除菜单ID
        this.selectPermissionChildrenById(id, idList);

        // 把当前ID封装到list里
        idList.add(id);

        baseMapper.deleteBatchIds(idList);
    }

    @Override
    public void saveRolePermissionRelationship(String roleId, String[] permissionList) {

        // 创建list集合，用于封装添加数据
        List<RolePermission> rolePermissionList = new ArrayList<>();

        // 历遍数组
        for (String perId : permissionList) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId).setPermissionId(perId);
            // 封装到list集合
            rolePermissionList.add(rolePermission);
        }
        // 添加到数据库
        rolePermissionService.saveBatch(rolePermissionList);
    }

    /**
     * 根据当前菜单id,查询菜单里的子菜单ID,封装到list集合
     * @param id 当前ID
     * @param idList id集合
     */
    private void selectPermissionChildrenById(String id, List<String> idList) {
        // 查询菜单里面的ID
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();

        wrapper.eq("pid", id);

        wrapper.select("id");

        List<Permission> childrenIdList = baseMapper.selectList(wrapper);

        // 把childrenIdList里面菜单ID值取出来，封装idList里面，左递归查询
        childrenIdList.forEach(item -> {
            // 封装idList里面
            idList.add(item.getId());
            // 递归查询
            this.selectPermissionChildrenById(item.getId(), idList);
        });

    }

    /**
     * 把所有的菜单list进行封装操作
     * @param permissionList 菜单集合
     * @return 封装结果集
     */
    public static List<Permission> buildPermission(List<Permission> permissionList) {

        // 创建list集合，用于数据封装
        List<Permission> finalNode = new ArrayList<>();

        // 把所有菜单list集合历遍，得到顶级菜单 pid = 0，设置位 level = 1
        permissionList.forEach(item -> {
            // 得到顶层菜单 pid = 0
            if (NODE_ID.equals(item.getPid())) {
                // 设置顶层菜单的level是1
                item.setLevel(1);
                // 根据顶层菜单，向里面进行查询子菜单，封装到finalNode
                finalNode.add(selectChildren(item, permissionList));
            }
        });

        return finalNode;
    }

    private static Permission selectChildren(Permission item, List<Permission> permissionList) {

        // 因为向一层菜单里面放二级菜单，二层里面放三层，把对象初始化
        item.setChildren(new ArrayList<>());

        // 遍历所有的菜单list集合，进行判断比较，比较id和pid值是否相同
        permissionList.forEach(it -> {
            // 判断pid和id是否相同
            if (item.getId().equals(it.getPid())) {
                // 把父菜单的level +1
                int level = item.getLevel() + 1;
                it.setLevel(level);

                // 如为空，初始化
                if (item.getChildren() == null) {
                    item.setChildren(new ArrayList<>());
                }

                // 把查询出来的子菜单放到父菜单中去
                item.getChildren().add(selectChildren(it, permissionList));
            }
        });

        return item;
    }
}
