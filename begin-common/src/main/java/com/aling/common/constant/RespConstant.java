package com.aling.common.constant;

/**
 * 接口错误码
 */
public class RespConstant {
	/**通用返回**/
	public final static String SUCCESS_CODE = "000000";
	public final static String SUCCESS_CODE_MS = "处理成功";

	public final static String ERROR_UNKOWN = "999999";
	public final static String ERROR_UNKOWN_MS = "未知错误";

	public final static String ERROR_SERVICE_TIMEOUT = "888888";
	public final static String ERROR_SERVICE_TIMEOUT_MSG = "服务请求超时,请稍后重试";

	/**报文合法性**/
	public final static String ERROR_REPORT_ENC_FAIL = "000001";
	public final static String ERROR_REPORT_ENC_FAIL_MS = "报文加解密失败";

	/**签名合法性**/
	public final static String EROOR_FAIL_VERIFY = "000002";
	public final static String EROOR_FAIL_VERIFY_MS = "验签失败";

	public final static String ERROR_REQUEST_MSG_EMPTY = "000003";
	public final static String ERROR_REQUEST_MSG_EMPTY_MS = "请求报文为空";

	public final static String ERROR_REQUEST_MSG_ILLEGAL = "000004";
	public final static String ERROR_REQUEST_MSG_ILLEGAL_MS = "请求格式非法";

	public final static String ERROR_NO_PARAM = "010000";
	public final static String ERROR_NO_PARAM_MS = "请求参数为空";

	public final static String EROOR_NULL_TRANSTYPE = "010001";
	public final static String EROOR_NULL_TRANSTYPE_MS = "交易类型为空";

	public final static String EROOR_UNKOWN_TRANSTYPE = "010002";
	public final static String EROOR_UNKOWN_TRANSTYPE_MS = "未知交易类型";

	public final static String ERROR_APP_ID_EMPTY = "010003";
	public final static String ERROR_APP_ID_EMPTY_MS = "应用编号为空";

	public final static String ERROR_APP_ID_ILLEGAL = "010005";
	public final static String ERROR_APP_ID_ILLEGA_MS = "应用编号非法";

	public final static String ERROR_UNKOWN_SERVICEID = "010004";
	public final static String ERROR_UNKOWN_SERVICEID_MS = "未知服务ID";

	public final static String ERROR_TIMESTAMP_EMPTY = "010006";
	public final static String ERROR_TIMESTAMP_EMPTY_MS = "请求时间戳为空";

	public final static String ERROR_VERSION_EMPTY = "010007";
	public final static String ERROR_VERSION_EMPTY_MS = "版本信息为空";

	public final static String ERR_VALIDATOR = "010012";
	public final static String ERR_VALIDATOR_MS = "验证传入参数出错";

	public final static String ERR_PARAMETER_TYPE = "010013";
	public final static String ERR_PARAMETER_TYPE_MS = "参数类型错误";

	public final static String ERROR_IN_VALID_PARAM = "010014";
	public final static String ERROR_IN_VALID_PARAM_MS = "非法参数";

	public final static String EROOR_NO_DEFINED_BEAN = "010015";
	public final static String EROOR_NO_DEFINED_BEAN_MS = "未定义此BEAN";

	public final static String EROOR_NO_RECORD = "010017";
	public final static String EROOR_NO_RECORD_MS = "找不到记录";

	public final static String EROOR_RETURN_TYPE = "010019";
	public final static String EROOR_RETURN_TYPE_MS = "返回类型非法";

	/** NO session*/
	public final static String ERROR_NOT_LOGIN = "110002";//
	public final static String ERROR_NOT_LOGIN_MSG = "没有登录或登录过期了";

	public final static String ERROR_SOURCE_STATUS = "40001";
	public final static String ERROR_SOURCE_STATUS_MSG = "号源状态不为可预约,可能已被预约";

	//******************************************************************************************************************//

	public final static String VALID_HOSPITAL_ID_EMPTY = "000010";
	public static final String VALID_HOSPITAL_ID_EMPTY_MSG = "医院编号为空";

	public static final String VALID_FUNC_ID_EMPTY = "000020";
	public static final String VALID_FUNC_ID_EMPTY_MSG = "接口编号为空";

	public static final String VALID_NO_OPEN_FUN = "000030"; //接口未开放
	public static final String VALID_NO_OPEN_FUN_MSG = "服务未开放";

	public static final String VALID_SERVICE_FUN_EMPTY = "000040";
	public static final String VALID_SERVICE_FUN_EMPTY_MSG = "未知服务ID";

	public static final String VALID_SERVICE_NO_AUTH = "000050";
	public static final String VALID_SERVICE_NO_AUTH_MSG = "无权限访问该接口";

	public static final String VALID_HOSPITAL_NO_SWITCH = "000060";
	public static final String VALID_HOSPITAL_NO_SWITCH_MSG = "当前医院尚未开放";

	public static final String VALID_MODULE_NO_SWITCH = "000070";
	public static final String VALID_MODULE_NO_SWITCH_MSG = "当前服务模块尚未开放";

	public static final String VALID_FUNC_NO_SWITCH = "000080";
	public static final String VALID_FUNC_NO_SWITCH_MSG = "当前服务尚未开放";

	public static final String VALID_VERSION_NO_SWITCH = "000085";
	public static final String VALID_VERSION_NO_SWITCH_MSG = "当前版本尚未开放";

	public static final String VALID_PARAM_REQUIRED = "000090";
	public static final String VALID_PARAM_REQUIRED_MSG = "必须参数不能为空";

	public final static String VALID_HOSPITAL_EMPTY = "000100";
	public static final String VALID_HOSPITAL_EMPTY_MSG = "未找到该医院信息";

	public static final String VALID_UPD_FAIL = "000120";
	public static final String VALID_UPD_FAIL_MSG = "更新失败";

	public static final String VALID_ADD_FAIL = "000110";
	public static final String VALID_ADD_FAIL_MSG = "添加失败";

	//******************************************************************************************************************//

	/**会话相关**/
//	public final static String SESSION_TIMEOUT = "020001";
//	public final static String SESSION_TIMEOUT_MS = "会话超时，请重新登录";

	public final static String ERR_SESSION_TIMEOUT = "020001";
	public final static String ERR_SESSION_TIMEOUT_MS = "会话超时或没有登录，请重新登录";

	public final static String ERR_SESSION_MISS = "020002";
	public final static String ERR_SESSION_MISS_MS = "sessionId为空！";

	public final static String ERR_NO_APPSESSIONDTO = "020055";
	public final static String ERR_NO_APPSESSIONDTO_MS = "登录失效，请重新登录";

	//******************************************************************************************************************//

	/**
	* 交易相关
	**/
	public final static String AMOUNT_ERROR = "030001";
	public final static String AMOUNT_ERROR_MS = "金额错误";

	public final static String OPENID_NULL = "030002";
	public final static String OPENID_NULL_MS = "微信openId为空";

	public final static String ALI_USERID_NULL = "030003";
	public final static String ALI_USERID_NULL_MS = "支付宝userId为空";

	public final static String PAY_ERROR = "031000";
	public final static String PAY_ERROR_MS = "下单失败";

	//******************************************************************************************************************//
	public final static String ERR_TOKEN_TIMEOUT = "050001";
	public final static String ERR_TOKEN_TIMEOUT_MS = "token已经过期,请重新获取";

	public final static String ERR_TOKEN_MISS = "050002";
	public final static String ERR_TOKEN_MISS_MS = "token为空！";

	public final static String ERR_TOKEN_RECORD_MISS = "050003";
	public final static String ERR_TOKEN_RECORD_MISS_MS = "预约记录TOKEN为空！";

	public final static String ERR_NO_TOKEN = "050055";
	public final static String ERR_NO_TOKEN_MS = "token记录未找到,请重新获取";

	public final static String ERR_GENERATE_LOGIN_TOKEN = "050069";
	public final static String ERR_GENERATE_LOGIN_TOKEN_MS = "产生自动登录TOKEN异常";

	//**************************************************数据权限相关****************************************************************//

	public static final String AUTH_NO_PERMISSION = "070001";
	public static final String AUTH_NO_PERMISSION_MSG = "没有权限";

	public static final String AUTH_NO_PERMISSION_RECORD = "070002";
	public static final String AUTH_NO_PERMISSION_RECORD_MSG = "您没有权限查看该订单,请确认token权限";

	public static final String AUTH_NO_PERMISSION_MODIFY = "070003";
	public static final String AUTH_NO_PERMISSION_MODIFY_MSG = "您没有权限修改该记录";

	//******************************************************************************************************************//

	/************************消息推送*************************/
	public static final String MSG_PUSH_EMPTY_CARD = "600001";
	public static final String MSG_PUSH_EMPTY_CARD_MS = "卡号不能为空";

	public static final String MSG_PUSH_EMPTY_TEMPLATE = "600002";
	public static final String MSG_PUSH_EMPTY_TEMPLATE_MS = "消息模板号不能为空";

	public static final String MSG_PUSH_EMPTY_CONTENT = "600003";
	public static final String MSG_PUSH_EMPTY_CONTENT_MS = "消息内容不能为空";

	public static final String MSG_PUSH_NOT_LINK_PHONE = "601001";
	public static final String MSG_PUSH_NOT_LINK_PHONE_MS = "未找到该卡的关联手机账号";

	public static final String MSG_PUSH_EMPTY_WX_AND_ALI = "601002";
	public static final String MSG_PUSH_EMPTY_WX_AND_ALI_MS = "没有绑定微信公众号和阿里生活号";

	public static final String MSG_PUSH_REPEAT = "601003";
	public static final String MSG_PUSH_REPEAT_MS = "该卡号推送消息重复";

	public static final String MSG_PUSH_INSERT_FAIL = "602001";
	public static final String MSG_PUSH_INSERT_FAIL_MS = "消息插入数据失败";

	//******************************************************************************************************************//

}
