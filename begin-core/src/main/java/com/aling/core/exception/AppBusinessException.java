package com.aling.core.exception;

import java.util.HashMap;
import java.util.Map;

public class AppBusinessException extends Exception {
	private static final long serialVersionUID = -1134955951203297180L;

	private String errorCode;
	private Map<String, Object> otherInfo; //异常返回时可能需要多带的额外信息

	public AppBusinessException(String message) {
		super(message);
	}

	public AppBusinessException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public AppBusinessException(String message, String errorCode, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public AppBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppBusinessException(Throwable cause) {
		super(cause);
	}

	public AppBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Map<String, Object> getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(Map<String, Object> otherInfo) {
		this.otherInfo = otherInfo;
	}

	public void addOtherInfo(String key, Object value) {
		if (this.otherInfo == null) {
			this.otherInfo = new HashMap<String, Object>();
		}
		otherInfo.put(key, value);
	}

	public void addOtheInfos(Map<String, Object> infos) {
		if (this.otherInfo == null) {
			this.otherInfo = new HashMap<String, Object>();
		}
		otherInfo.putAll(infos);
	}
}
