package com.aling.common.service.impl;

import com.aling.common.entity.Role;
import com.aling.common.mapper.RoleMapper;
import com.aling.common.service.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
