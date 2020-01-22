package com.aling.test;


import com.aling.core.client.AppointPlatformAdminClient;

public class BaseTest {


    public static final String serverUrl = "http://127.0.0.1:7001/vaccination/unifyapi"; //本机地址

    public static String sessionId = "APP_SESSION_CACHE1322#9c78400e-6449-4581-9b26-57291b358933";

    public static String token = "1D2CKE0CP0135C2CA8C000005C6C16FD"; //测试
   //public static String token = "1D0JJ28OC00T100310BC0000B13435CC"; //正式

    public static AppointPlatformAdminClient adminClient = new AppointPlatformAdminClient(serverUrl, "app20180918145452726KYK9GGF3BQEO", "sec20180918145452726T1I4R9E61CJN", "MD5", "AES", "v.1.0.0");


}
