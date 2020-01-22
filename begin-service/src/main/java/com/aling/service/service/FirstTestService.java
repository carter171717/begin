package com.aling.service.service;


import com.aling.common.base.BaseService;
import com.aling.common.cache.AppSessionCache;
import com.aling.common.cache.JetCacheFactory;
import com.aling.common.cache.SysUserId2SessionInfoCache;
import com.aling.common.config.properties.BeginProperties;
import com.aling.common.entity.User;
import com.aling.common.mapper.UserMapper;
import com.aling.common.util.RespUtil;
import com.aling.core.bean.RequestParams;
import com.aling.core.bean.ResponseParams;
import com.aling.core.dto.system.UserReq;
import com.aling.core.dto.system.UserRes;
import com.aling.core.exception.AppException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FirstTestService extends BaseService {



    public static final String RANDOM_KEY_PREFIX = "RANDOMCODE-";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JetCacheFactory jetCacheFactory;

    @Autowired
    BeginProperties beginProperties;

    @Autowired
    private SysUserId2SessionInfoCache sysUserId2SessionInfoCache;

    @Autowired
    private AppSessionCache appSessionCache;

    /**
     * 这是个测试的案例
     * @param requestParams
     * @return
     * @throws AppException
     */
    //@Cached(name="quereyUser",cacheType = CacheType.REMOTE, expire = 60) //这里是缓存的使用
    public ResponseParams<UserRes> queryUser(RequestParams requestParams)  throws AppException {
        log.info("请求查询用户的接口");
        //构造出请求参数
        UserReq req = paramToObject(requestParams, UserReq.class);
        String userName = req.getUserName();
        log.info("请求参数：userName={}",userName);

        //构造出返回值
        ResponseParams<UserRes> responseParams = buildResponseParams(requestParams);
        UserRes userRes = new UserRes();
        //业务内容 这里只是举个例子

        log.info("开始查询用户。。。。");
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.eq("USER_NAME",userName);
        List<User> userList =  userMapper.selectList(ew);
        if(userList !=null && userList.size()>0){
            User user = userList.get(0);
            userRes.setUserName(user.getUserName());
            userRes.setPhone(user.getPhone());
            responseParams.setParam(userRes); //包装返回参数
        }
        log.info("查询成功了。。。");
        return RespUtil.success(responseParams); //返回内容
    }



}
