package com.aling.service;


import com.aling.core.client.AppointPlatformAdminClient;

public class BaseTest {


    public static final String serverUrl = "http://127.0.0.1:8888/appointPlatform/unifyapi"; //本机地址

    public static String sessionId = "APP_SESSION_CACHE1001#b7874b79-753d-4669-b569-5e1d03b3e399";

    public static String token = "1D2CKE0CP0135C2CA8C000005C6C16FD"; //测试
   //public static String token = "1D0JJ28OC00T100310BC0000B13435CC"; //正式

    public static AppointPlatformAdminClient adminClient = new AppointPlatformAdminClient(serverUrl, "app20200121152627610O1FNHG16TZ2N", "sec20200121152627610FIRNJP8NW1RG", "MD5", "AES", "v.1.0.0");


}
