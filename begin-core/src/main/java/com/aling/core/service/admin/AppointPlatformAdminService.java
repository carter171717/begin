package com.aling.core.service.admin;

import com.aling.core.bean.ResponseParams;
import com.aling.core.dto.system.*;
import com.aling.core.enums.AppointPlatformAdiminServiceId;
import com.aling.core.exception.BusinessException;
import com.aling.core.service.GuahaoMainService;

public class AppointPlatformAdminService extends GuahaoMainService {


    public AppointPlatformAdminService(String serviceUrl, String appId, String appSecret, String signType, String encryptType, String version) {
        super(serviceUrl, appId, appSecret, signType, encryptType, version);
    }
    /**
     * 第一个单元测试
     */
    public ResponseParams<UserRes> firstTest(UserReq req) throws BusinessException {
        String serviceId = AppointPlatformAdiminServiceId.TEST.getCode();
        return doBusiness(serviceId, req,UserRes.class);
    }

    /**
     * 用户登录
     */
    public ResponseParams<LoginRes> login(UserReq req) throws BusinessException {
        String serviceId = AppointPlatformAdiminServiceId.ADMIN_USER_LOGIN.getCode();
        return doBusiness(serviceId, req,LoginRes.class);
    }

    /**
     * 用户登出
     */
    public ResponseParams<LoginRes> logout(UserReq req) throws BusinessException {
        String serviceId = AppointPlatformAdiminServiceId.ADMIN_USER_LOGOUT.getCode();
        return doBusiness(serviceId, req,LoginRes.class);
    }

    /**
     * 获取菜单信息
     */
    public ResponseParams<SysUserRes> getMenuInfo(UserReq req) throws BusinessException {
        String serviceId = AppointPlatformAdiminServiceId.ADMIN_USER_GET_MENU_INFO.getCode();
        return doBusiness(serviceId, req,SysUserRes.class);
    }




}
