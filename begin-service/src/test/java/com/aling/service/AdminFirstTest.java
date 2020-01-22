package com.aling.service;

import com.alibaba.fastjson.JSON;
import com.aling.core.bean.ResponseParams;
import com.aling.core.dto.system.UserReq;
import com.aling.core.dto.system.UserRes;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AdminFirstTest extends BaseTest {

    private UserReq userReq = new UserReq();

    @Before
    public void before() {
        userReq.setSessionId(sessionId);
    }

    @Test
    public void firstTest() {

        userReq.setUserName("kobe");
        ResponseParams<UserRes> res = adminClient.firstTest(userReq);
        System.out.println("结果：" + JSON.toJSONString(res));
        System.out.println("好果：" + JSON.toJSONString(res.getParam()));
    }


}
