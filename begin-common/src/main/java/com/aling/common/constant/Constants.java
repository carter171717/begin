package com.aling.common.constant;

/**
 * 常量
 */
public class Constants {
	public static final String SSO_ENCODING = "UTF-8";

	//*************************************************************************************************************************//
	public static final String COMMON_DOCTOR_CODE = "COMMON_999";//医生类型-按科室

	//医生类型
	public static final String KEY_DOCTOR_TYPE = "type";//医生类型
	public static final String DOCTOR_TYPE_SCHEDULE = "1";//医生类型-按排班
	public static final String DOCTOR_TYPE_DOCTOR = "2";//医生类型-按医生
	public static final String DOCTOR_TYPE_DEPT = "3";//医生类型-按科室

	public static final String SOURCE_TYPE_DOCTOR = "1";//医生号源
	public static final String SOURCE_TYPE_DEPT = "2";//科室号源

	//合肥二院时段
	public static final String SCHEDULE_PERIOD_MORNING_HFEY = "9";//上午
	public static final String SCHEDULE_PERIOD_AFTERNOON_HFEY = "10";//下午
	public static final String SCHEDULE_PERIOD_DAY_HFEY = "11";//全天

	public static final String KEY_HOSPITAL = "hospital";
	public static final String KEY_DEPT = "dept";
	public static final String KEY_DOCTOR = "doctor";
	public static final String KEY_SCHEDULE = "schedule";
	public static final String KEY_SOURCE = "source";

	public static final String KEY_START_TIME = "startTime";
	public static final String KEY_END_TIME = "endTime";

	public static final String AREA_DEFAULT = "350000";

	public static final int ERROR_CHANCE = 5;
	public static final int FREEZE_MIN = 30;

	public static final String STATUS_DISABLED = "0";
	public static final String STATUS_ENABLED = "1";

	// 是否管理员
	public static final String SYSUSER_ADMIN_YES = "1";// 是管理员
	public static final String SYSUSER_ADMIN_NO = "0";// 不是管理员

	//管理员角色状态
	public static final String SYSUSER_ROLE_STATUS_ENABLE = "1";//启用
	public static final String SYSUSER_ROLE_STATUS_DISABLE = "0";//停用

	// 默认密码
	public static final String DEFAULT_PASSWORD = "123456";

	public static final String PARAMS_TYPE_REQUEST = "REQUEST";
	public static final String PARAMS_TYPE_RESPONSE = "RESPONSE";

	public static final String QUERY_TYPE_PROPER = "1";
	public static final String QUERY_TYPE_ALL = "2";

	public static final String CHANNEL = "channel";

	public static final String H5 = "H5";
	public static final String ANDROID = "ANDROID";
	public static final String IOS = "IOS";

}