package com.aling.common.service.impl;

import com.aling.common.entity.RoleMenu;
import com.aling.common.mapper.RoleMenuMapper;
import com.aling.common.service.RoleMenuService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {


    @Override
    public List<String> queryByRoleId(String roleId) {
        EntityWrapper<RoleMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("ROLE_ID", roleId);
        List<String> list = new ArrayList<>();
        List<RoleMenu> menus = selectList(wrapper);
        if (CollectionUtils.isNotEmpty(menus)) {
            for(RoleMenu menu : menus) {
                list.add(menu.getMenuId());
            }
        }
        return list;
    }
}
