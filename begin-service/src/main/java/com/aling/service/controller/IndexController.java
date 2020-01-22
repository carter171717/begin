package com.aling.service.controller;


import com.aling.common.cache.JetCacheFactory;
import com.aling.common.cache.SysUserId2SessionInfoCache;
import com.aling.common.constant.Constants;
import com.aling.common.constant.RespConstant;
import com.aling.common.util.RandomCodeUtil;
import com.aling.common.util.StringUtil;
import com.aling.core.bean.SessionInfo;
import com.aling.core.dto.system.AppSessionDto;
import com.aling.core.dto.system.UserReq;
import com.aling.core.exception.AppException;
import com.aling.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@Slf4j
public class IndexController {

    public static final String RANDOM_KEY = "randomKey";

    public static final String RANDOM_KEY_PREFIX = "RANDOMCODE-";

    @Autowired
    private JetCacheFactory jetCacheFactory;

    @Autowired
    private SysUserId2SessionInfoCache sysUserId2SessionInfoCache;

    // @描述：创建验证码
    @RequestMapping("createCodeImg")
    public void createRandomCodeImg(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.info("createRandomCodeImg...");

        String randomKey = request.getParameter(RANDOM_KEY);
        log.info("请求的randomKey:{},请求获取验证码的sessionId:{}", randomKey, request.getRequestedSessionId());

        if (StringUtil.isEmpty(randomKey)) {
            log.error("randomKey is null");
            return;
        }

        Map<String, Object> map = RandomCodeUtil.createRandomImg(request);
        BufferedImage buffImg = (BufferedImage) map.get(RandomCodeUtil.KEY_BUFF_IMG);

        String randomCode = (String) map.get(RandomCodeUtil.KEY_RANDOM_CODE);
        log.info("生成的验证码:{}", randomCode);

        jetCacheFactory.getCommonCache().put(RANDOM_KEY_PREFIX + randomKey, randomCode);

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }


    @RequestMapping("/unifyapi")
    @ResponseBody
    public Map<String,String> doJob(@RequestBody UserReq req, HttpServletRequest request){
        Map<String,String> result = new HashMap<String,String>();
        String sessionId = req.getSessionId();
        log.info("用户登录的sessionId={}",sessionId);
        try {

            SessionInfo sessionInfo  =checkSession(sessionId);

            log.info("当前登录的用户Id={}",sessionInfo.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error","sessionId已经过期，请重新登录");

        }
        return result;
    }


    private SessionInfo checkSession(String sessionId) throws BusinessException {


        log.info("请求参数sessionId:{}", sessionId);

        if (StringUtils.isBlank(sessionId)) {
            throw new BusinessException(RespConstant.ERR_SESSION_MISS, RespConstant.ERR_SESSION_MISS_MS);
        }

        AppSessionDto dto = jetCacheFactory.getAppSessionCache().get(sessionId);
        if (dto == null) {
            throw new BusinessException(RespConstant.ERR_NO_APPSESSIONDTO, RespConstant.ERR_NO_APPSESSIONDTO_MS);
        }


        SessionInfo sessionInfo = sysUserId2SessionInfoCache.getSessionInfo(dto.getUserId());
        if (sessionInfo == null) {
            throw new BusinessException(RespConstant.ERR_SESSION_TIMEOUT, RespConstant.ERR_SESSION_TIMEOUT_MS);
        }

        return sessionInfo;

    }


    public SessionInfo getSession(String sessionId) throws AppException {
        SessionInfo sessionInfo = null;
        try {

            AppSessionDto dto = jetCacheFactory.getAppSessionCache().get(sessionId);
            sessionInfo = sysUserId2SessionInfoCache.getSessionInfo(dto.getUserId());

        } catch (Exception e) {
            throw new AppException("获取SessionInfo失败！");
        }
        return sessionInfo;
    }




}
