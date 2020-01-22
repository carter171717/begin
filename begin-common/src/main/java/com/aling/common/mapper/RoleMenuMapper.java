package com.aling.common.mapper;

import com.aling.common.entity.RoleMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
