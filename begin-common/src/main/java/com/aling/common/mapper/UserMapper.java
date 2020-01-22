package com.aling.common.mapper;

import com.aling.common.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
