package com.aling.core.client;

import com.aling.core.bean.ResponseParams;
import com.aling.core.dto.system.LoginRes;
import com.aling.core.dto.system.SysUserRes;
import com.aling.core.dto.system.UserReq;
import com.aling.core.dto.system.UserRes;
import com.aling.core.enums.AppointPlatformAdiminServiceId;
import com.aling.core.exception.BusinessException;
import com.aling.core.service.admin.AppointPlatformAdminService;

public class AppointPlatformAdminClient {

    /**
     * 应用id
     */
    private String appId;

    /**
     * 应用密钥
     */
    private String appSecret;

    /**
     * 签名类型
     */
    private String signType;

    /**
     * 加密类型
     */
    private String encryptType;

    /**
     * 版本号
     */
    private String version;

    private AppointPlatformAdminService appointPlatformAdminService;


    public AppointPlatformAdminClient(String serviceUrl, String appId, String appSecret, String signType, String encryptType, String version) {
        super();
        this.appId = appId;
        this.appSecret = appSecret;
        this.signType = signType;
        this.encryptType = encryptType;
        this.version = version;

        appointPlatformAdminService = new AppointPlatformAdminService(serviceUrl, appId, appSecret, signType, encryptType, version);
    }

    /**
     * 第一个测试
     */
    public ResponseParams<UserRes> firstTest(UserReq req) throws BusinessException {
        return appointPlatformAdminService.firstTest(req);
    }

    /****************************************用户登录模块*******************************************/

    /**
     * 用户登录
     */
    public ResponseParams<LoginRes> login(UserReq req) throws BusinessException {
        return appointPlatformAdminService.login(req);
    }

    /**
     * 用户登出
     */
    public ResponseParams<LoginRes> logout(UserReq req) throws BusinessException {
        return appointPlatformAdminService.logout(req);
    }

    /**
     * 获取菜单
     */
    public ResponseParams<SysUserRes> getMenuInfo(UserReq req) throws BusinessException {
        return appointPlatformAdminService.getMenuInfo(req);
    }



}
