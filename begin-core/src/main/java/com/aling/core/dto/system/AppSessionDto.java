package com.aling.core.dto.system;

import java.io.Serializable;

public class AppSessionDto implements Serializable {
	private static final long serialVersionUID = 9084462912261043581L;

	private String sessionId; //会话ID
	private String createTime; //会话创建时间
	private String updateTime; //会话更新时间
	private Integer timeout; //会话超时间隔时间,
	private String expireTime; //会话过期指定时间
	private Integer otherDeviceLogin = 0;//同一个用户其他APP设备登录 1=就是其他设备登录了
	private String deviceId;
	private String userId;
	private boolean sys;//为true时是后台管理员

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getOtherDeviceLogin() {
		return otherDeviceLogin;
	}

	public void setOtherDeviceLogin(Integer otherDeviceLogin) {
		this.otherDeviceLogin = otherDeviceLogin;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public boolean isSys() {
		return sys;
	}

	public void setSys(boolean sys) {
		this.sys = sys;
	}
}
