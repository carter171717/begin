package com.aling.common.service;

import com.aling.common.entity.RoleMenu;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
public interface RoleMenuService extends IService<RoleMenu> {

    List<String> queryByRoleId(String roleId);

}
