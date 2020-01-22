package com.aling.core.enums;

import java.util.HashMap;
import java.util.Map;

public enum AppointPlatformAdiminServiceId {
    //测试
    TEST("admin.appointPlatform.user.test"),

    ADMIN_USER_LOGIN("admin.appointPlatform.user.login"),

    ADMIN_USER_LOGOUT("admin.appointPlatform.user.logout"),

    ADMIN_USER_GET_MENU_INFO("admin.appointPlatform.user.getMenuInfo"),

    /*************************************/
    END("end");

    private String code;

    AppointPlatformAdiminServiceId(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    static Map<Object, AppointPlatformAdiminServiceId> enumMap = new HashMap<Object, AppointPlatformAdiminServiceId>();

    static {
        for (AppointPlatformAdiminServiceId type : AppointPlatformAdiminServiceId.values()) {
            enumMap.put(type.getCode(), type);
        }
    }

    public static AppointPlatformAdiminServiceId getEnum(String code) {
        return enumMap.get(code);
    }

}
