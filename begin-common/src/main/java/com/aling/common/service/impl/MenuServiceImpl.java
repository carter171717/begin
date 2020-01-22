package com.aling.common.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.aling.common.cache.JetCacheKey;
import com.aling.common.entity.Menu;
import com.aling.common.mapper.MenuMapper;
import com.aling.common.service.MenuService;
import com.aling.common.service.RoleMenuService;
import com.aling.core.dto.system.SysMenuRes;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    RoleMenuService roleMenuService;


    @Cached(name = JetCacheKey.NAME_T_ROLE_CACHE, key = "#roleId", cacheType = CacheType.REMOTE, expire = 5, timeUnit = TimeUnit.MINUTES)
    public List<SysMenuRes> queryByRoleId(String roleId) {
        List<String> menuIds = roleMenuService.queryByRoleId(roleId);
        List<SysMenuRes> sysMenuList = queryMenuList("0");

        List<SysMenuRes> filterMenuList = new ArrayList<>();
        filterMenuList = filterList(sysMenuList, menuIds, filterMenuList);
        return filterMenuList;
    }

    private List<SysMenuRes> filterList(List<SysMenuRes> list, List<String> menuIds, List<SysMenuRes> result) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysMenuRes res : list) {
                if (menuIds.contains(res.getMenuId())) {
                    if (CollectionUtils.isNotEmpty(res.getMenuList())) {
                        List<SysMenuRes> child = new ArrayList<>();
                        res.setMenuList(filterList(res.getMenuList(), menuIds, child));
                        result.add(res);
                    } else {
                        result.add(res);
                    }
                }
            }
        }
        return result;
    }


    @Cached(name = JetCacheKey.NAME_MENU_CACHE, key = "#parentMenuId", cacheType = CacheType.REMOTE, expire = 5, timeUnit = TimeUnit.MINUTES)
    public List<SysMenuRes> queryMenuList(String parentMenuId) {
        List<SysMenuRes> sysMenuList = null;
        EntityWrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper.eq("PID", parentMenuId);
        wrapper.ne("MENU_TYPE","2");
        wrapper.orderBy("SORT", true);
        List<Menu> sysMenus = selectList(wrapper);
        if (CollectionUtils.isNotEmpty(sysMenus)) {
            sysMenuList = new ArrayList<>();
            for (Menu sysMenu : sysMenus) {

                SysMenuRes res = new SysMenuRes();
                BeanUtils.copyProperties(sysMenu, res);
                res.setMenuId(sysMenu.getId());
                res.setMenuList(queryMenuList(sysMenu.getId()));
                if(sysMenu.getMenuType().equals("1")){ //如果是菜单的话，还要查询该菜单下面的权限

                    EntityWrapper<Menu> btnWrapper = new EntityWrapper<>();
                    btnWrapper.eq("PID", sysMenu.getId());
                    btnWrapper.eq("MENU_TYPE","2");
                    btnWrapper.orderBy("SORT", true);
                    List<Menu> btnList = selectList(btnWrapper);
                    if(btnList != null && btnList.size()>0){
                        List<SysMenuRes> btnMenuList = new ArrayList<SysMenuRes>();
                        for (Menu btn : btnList) {
                            SysMenuRes btnRes = new SysMenuRes();
                            BeanUtils.copyProperties(btn, btnRes);
                            btnMenuList.add(btnRes);
                        }
                        res.setButtonAuthList(btnMenuList);
                    }
                }

                sysMenuList.add(res);
            }
        }
        return sysMenuList;
    }
}
