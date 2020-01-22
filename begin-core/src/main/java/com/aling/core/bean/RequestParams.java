package com.aling.core.bean;

import java.io.Serializable;
import java.util.Map;

public class RequestParams implements Serializable {

	private static final long serialVersionUID = 1L;

	/**应用id**/
	private String appId;

	/**请求参数**/
	private Object param;

	/**签名**/
	private String sign;

	/**时间戳**/
	private String timestamp;

	/**
	 * 接口ID，对应ServiceFunc中的funcId
	 **/
	private String serviceId;

	/**额外拓展参数*/
	private Map<String, Object> externalMap;

	/**签名类型**/
	private String signType;

	/**加密类型**/
	private String encryptType;

	/** 版本号**/
	private String version;

	/**密文**/
	private String encryptData;

	private String termType; //终端类型 @see TermType

	private String sessionId;//客户端会话sessionId

	private String deviceId;//访问的设备ID

	private String reqNo;

	private Pager pageParam;

	private String token;

	//private Integer isEncrypt = 0;//是否是加密请求，加密请求对应加密响应，0=明文 1=密文

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEncryptData() {
		return encryptData;
	}

	public void setEncryptData(String encryptData) {
		this.encryptData = encryptData;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Map<String, Object> getExternalMap() {
		return externalMap;
	}

	public void setExternalMap(Map<String, Object> externalMap) {
		this.externalMap = externalMap;
	}

	public Pager getPageParam() {
		return pageParam;
	}

	public void setPageParam(Pager pageParam) {
		this.pageParam = pageParam;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

//	public Integer getIsEncrypt() {
//		return isEncrypt;
//	}
//
//	public void setIsEncrypt(Integer isEncrypt) {
//		this.isEncrypt = isEncrypt;
//	}

}
