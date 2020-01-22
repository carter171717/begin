package com.aling.core.dto.system;

import java.io.Serializable;

/**
 * @Description: 登录响应
 * @author fugl
 * @date 2018年8月31日
 */
public class LoginRes implements Serializable {
	private static final long serialVersionUID = 6384358854954909969L;

	private String sessionId;//登录之后要放在requestParam的sessionId之中，
	private SysUserRes sysUserRes;//用户信息

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public SysUserRes getSysUserRes() {
		return sysUserRes;
	}

	public void setSysUserRes(SysUserRes sysUserRes) {
		this.sysUserRes = sysUserRes;
	}
}
