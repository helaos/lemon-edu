package com.fatehole.aclservice.helper;

import com.fatehole.aclservice.entity.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据权限数据构建菜单数据
 * @author helaos
 * @version 1.0.0
 * @date Create in 2021/01/11/15:36
 */
public class PermissionHelper {

    /**
     * 使用递归方法建菜单
     * @param treeNodes 节点
     * @return 菜单
     */
    public static List<Permission> build(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes 节点
     * @return 结果
     */
    public static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<>());

        for (Permission it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
}
