package com.aling.core.exception;

public class AppSessionException extends Exception {
	private static final long serialVersionUID = -4419424288679783892L;

	private String errorCode;
	private String errorMsg;

	public AppSessionException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;

	}

	public AppSessionException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public AppSessionException(String message, String errorCode, String errorMsg) {
		super(message);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public AppSessionException(String message, Throwable cause, String errorCode, String errorMsg) {
		super(message, cause);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public AppSessionException(Throwable cause, String errorCode, String errorMsg) {
		super(cause);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
