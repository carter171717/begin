package com.aling.service.controller;



import com.alicp.jetcache.anno.Cached;
import com.aling.common.config.properties.BeginProperties;
import com.aling.common.entity.User;

import com.aling.common.mapper.UserMapper;
import com.aling.core.dto.system.LoginRes;
import com.aling.core.dto.system.UserReq;
import com.aling.core.exception.AppException;
import com.aling.service.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private BeginProperties beginProperties;


    @RequestMapping("/test")
    @ResponseBody
    public Map<String,Object> test(HttpServletRequest request) throws AppException {
        log.info("测试缓存");
        Map<String,Object> result = new HashMap<String,Object>();

        boolean isNeedCheckCode = beginProperties.isNeedCheckLoginRandomCode();
        String name = beginProperties.getTestName();
        result.put("isNeedCheckCode",isNeedCheckCode);
        result.put("name",name);
        return result;
    }

    @RequestMapping("/queryUser")
    @ResponseBody
    public Map<String,String> queryUser(@RequestParam String name) throws AppException {
        log.info("请求过来的名字是={}",name);
        Map<String,String> result = new HashMap<String,String>();
                //userService.queryUser();
        return result;
    }

    @RequestMapping("/login")
    @ResponseBody
    public LoginRes login(@RequestBody UserReq req,HttpServletRequest request) throws AppException {

        String userName = req.getUserName();
        String password = req.getPassword();
        String code = req.getCode();
        String randomKey = req.getRandomKey();
        log.info("登录的用户名：username={},password={}",userName,password);
        log.info("登录的验证码: code={},randomKey={}",code,randomKey);
        LoginRes result = null;
        //userService.login(userName,password,randomKey,code);
        return result;
    }



    @RequestMapping("/addUser")
    @ResponseBody
    public Map<String,String> addUser(@RequestBody UserReq req)throws AppException {
        String userName = req.getUserName();
        String password = req.getPassword();

        log.info("新增的用户名：username={},password={}",userName,password);
        Map<String,String> result = userService.addUser(userName,password);
        return result;
    }


}
