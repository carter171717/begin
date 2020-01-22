package com.aling.common.service.impl;

import com.aling.common.entity.Application;
import com.aling.common.mapper.ApplicationMapper;
import com.aling.common.service.ApplicationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用表 服务实现类
 * </p>
 *
 * @author wangly
 * @since 2020-01-20
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

}
