package com.fatehole.aclservice.service.impl;

import com.fatehole.aclservice.entity.Role;
import com.fatehole.aclservice.mapper.RoleMapper;
import com.fatehole.aclservice.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author helaos
 * @since 2020-12-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
