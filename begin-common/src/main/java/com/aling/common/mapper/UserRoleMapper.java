package com.aling.common.mapper;

import com.aling.common.entity.UserRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
