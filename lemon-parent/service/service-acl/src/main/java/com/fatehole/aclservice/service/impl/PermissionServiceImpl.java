package com.fatehole.aclservice.service.impl;

import com.fatehole.aclservice.entity.Permission;
import com.fatehole.aclservice.mapper.PermissionMapper;
import com.fatehole.aclservice.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
