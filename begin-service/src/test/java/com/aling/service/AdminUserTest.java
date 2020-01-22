package com.aling.service;

import com.alibaba.fastjson.JSON;
import com.aling.core.bean.ResponseParams;
import com.aling.core.dto.system.LoginRes;
import com.aling.core.dto.system.SysUserRes;
import com.aling.core.dto.system.UserReq;
import com.aling.core.dto.system.UserRes;
import org.junit.Before;
import org.junit.Test;

public class AdminUserTest extends BaseTest {

    private UserReq userReq = new UserReq();

    @Before
    public void before() {

        //userReq.setSessionId(sessionId);
    }

    @Test
    public void loginTest() {

        userReq.setUserName("kobe");
        userReq.setPassword("242424");
        userReq.setCode("");
        userReq.setRandomKey("");
        ResponseParams<LoginRes> res = adminClient.login(userReq);
        System.out.println("结果：" + JSON.toJSONString(res));
        System.out.println("好果：" + JSON.toJSONString(res.getParam()));
    }

    @Test
    public void logoutTest() {

        userReq.setSessionId(sessionId);

        ResponseParams<LoginRes> res = adminClient.logout(userReq);
        System.out.println("结果：" + JSON.toJSONString(res));
        System.out.println("好果：" + JSON.toJSONString(res.getParam()));
    }


    @Test
    public void getMenuTest() {

        userReq.setSessionId(sessionId);

        ResponseParams<SysUserRes> res = adminClient.getMenuInfo(userReq);
        System.out.println("结果：" + JSON.toJSONString(res));
        System.out.println("好果：" + JSON.toJSONString(res.getParam()));
    }

}
