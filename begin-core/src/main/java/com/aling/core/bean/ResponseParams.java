package com.aling.core.bean;

import java.io.Serializable;
import java.util.Map;

public class ResponseParams<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String termNo; // 交易终端编号

	//hospface通信标示码【SUCCESS/FAIL】
	private String resultCode;

	//hospface返回信息
	private String resultMsg;

	//业务标示码
	private String respCode;

	//业务返回消息
	private String respMsg;

	private T param;

	private String sign;

	private String timestamp;

	private Pager pageParam;

	/**签名类型*/
	private String signType;

	/**加密类型*/
	private String encryptType;

	/**版本号*/
	private String version;

	/**密文*/
	private String encryptData;

	/**服务编号*/
	private String serviceId;

	/**appId*/
	private String appId;

	/**
	 * 额外的返回参数 (供controller处理的参数)
	 */
	private Map<String, Object> externalParam;

	private Map<String, Object> pageParams;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

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

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
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

	public Pager getPageParam() {
		return pageParam;
	}

	public void setPageParam(Pager pageParam) {
		this.pageParam = pageParam;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public Map<String, Object> getExternalParam() {
		return externalParam;
	}

	public void setExternalParam(Map<String, Object> externalParam) {
		this.externalParam = externalParam;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Map<String, Object> getPageParams() {
		return pageParams;
	}

	public void setPageParams(Map<String, Object> pageParams) {
		this.pageParams = pageParams;
	}
}
