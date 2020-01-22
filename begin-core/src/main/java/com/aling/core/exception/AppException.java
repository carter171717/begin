package com.aling.core.exception;

public class AppException extends Exception {
	private static final long serialVersionUID = 1410233337970024513L;

	private String errorCode;
	private String errorMsg;

	public AppException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}

	public AppException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public AppException(String message, String errorCode, String errorMsg) {
		super(message);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public AppException(String message, Throwable cause, String errorCode, String errorMsg) {
		super(message, cause);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public AppException(Throwable cause, String errorCode, String errorMsg) {
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
