package com.aling.common.service.impl;

import com.aling.common.entity.User;
import com.aling.common.mapper.UserMapper;
import com.aling.common.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author wangly
 * @since 2020-01-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
