package com.aling.core.dto.system;

import java.io.Serializable;

public class BaseReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private int pageNo;
	private int rows;
	private String sessionId;
	private String startTime; // 开始时间
	private String endTime; // 结束时间

	//add by fgl 20180917
	protected String token; //token

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getPageNo() {
		return pageNo;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
