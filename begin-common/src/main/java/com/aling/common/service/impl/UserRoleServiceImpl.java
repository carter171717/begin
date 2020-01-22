package com.aling.common.service.impl;

import com.aling.common.entity.UserRole;
import com.aling.common.mapper.UserRoleMapper;
import com.aling.common.service.UserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
