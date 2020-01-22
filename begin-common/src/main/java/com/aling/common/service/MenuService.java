package com.aling.common.service;

import com.aling.common.entity.Menu;
import com.aling.core.dto.system.SysMenuRes;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
public interface MenuService extends IService<Menu> {

    List<SysMenuRes> queryByRoleId(String roleId);

}
