package com.aling.common.cache;

import com.aling.common.config.properties.BeginProperties;
import com.aling.common.util.DateUtil;
import com.aling.common.util.UUIDHelper;
import com.aling.core.bean.SessionInfo;
import com.aling.core.dto.system.AppSessionDto;
import com.aling.core.exception.AppSessionException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppSessionCache {

	private static Logger logger = LoggerFactory.getLogger(AppSessionCache.class);

	private static final String CACHE_PREFIX = "APP_SESSION_CACHE";

	private static final String DATEFORMATLONG = "yyyy-MM-dd HH:mm:ss";

	private final static int TIMEOUT_APPSESSIONID = 60 * 60 * 24;

	@Autowired
	private JetCacheFactory jetCacheFactory;

	@Autowired
	private AppUserId2SessionIdCache appUserId2SessionIdCache;

	@Autowired
	private UserId2SessionInfoCache userId2SessionInfoCache;

	@Autowired
	private SysUserId2SessionInfoCache sysUserId2SessionInfoCache;

	@Autowired
	private BeginProperties beginProperties;

	public void setSession(AppSessionDto appSessionDto) {
		if (StringUtils.isEmpty(appSessionDto.getSessionId())) {
			appSessionDto.setSessionId(CACHE_PREFIX + UUIDHelper.getUUID());
		}
		String sessionId = appSessionDto.getSessionId();
		if (StringUtils.isEmpty(appSessionDto.getCreateTime())) {
			appSessionDto.setCreateTime(DateUtil.getCurrentDateStr(DATEFORMATLONG));
		}
		if (StringUtils.isEmpty(appSessionDto.getUpdateTime())) {
			appSessionDto.setUpdateTime(appSessionDto.getCreateTime());
		}

		jetCacheFactory.getAppSessionCache().put(sessionId, appSessionDto);
	}

	public AppSessionDto getSession(String sessionId) throws AppSessionException {
		if (StringUtils.isEmpty(sessionId)) {
			return null;
		}
		AppSessionDto appSessionDto = jetCacheFactory.getAppSessionCache().get(sessionId);
		if (appSessionDto == null) {
			logger.error("用户会话缓存过期{}...", sessionId);
		}
		return appSessionDto;
	}

	/**
	 * 初始化session缓存
	 **/
	public String initSession(SessionInfo sessionInfo, String deviceId) throws AppSessionException {
		AppSessionDto appSessionDto = new AppSessionDto();
		String sessionId = CACHE_PREFIX + sessionInfo.getUserId() + "#" + UUIDHelper.getUUID();
		appSessionDto.setSessionId(sessionId);
		sessionInfo.setSessionId(sessionId);
		appSessionDto.setCreateTime(DateUtil.getCurrentDateStr(DATEFORMATLONG));
		appSessionDto.setUpdateTime(appSessionDto.getCreateTime());
		appSessionDto.setUserId(sessionInfo.getUserId());
		appSessionDto.setTimeout(TIMEOUT_APPSESSIONID);
		appSessionDto.setDeviceId(deviceId);
		appSessionDto.setSys(sessionInfo.isSys());

		//保存APP的sessionInfo信息
		if (sessionInfo.isSys()) {
			sysUserId2SessionInfoCache.saveSessionInfo(sessionInfo.getUserId(), sessionInfo);
		} else {
			userId2SessionInfoCache.saveSessionInfo(sessionInfo.getUserId(), sessionInfo);
		}
		//保存用户对应的SESSIONID,控制手机只能登录一个，
		if (beginProperties.isEnableLimitSingleTermTypeLogin()) {
			String oldSessionId = appUserId2SessionIdCache.getSessionId(sessionInfo.getUserId());
			if (oldSessionId != null) {//清除sessionId
				//cacheClient.remove(CacheConstant.CACHE_GROUP, oldSessionId);
				AppSessionDto appSessionDtoOld = (AppSessionDto) jetCacheFactory.getAppSessionCache().get(oldSessionId);
				if (appSessionDtoOld != null) {

					if ((appSessionDtoOld.getDeviceId() != null) && appSessionDtoOld.getDeviceId().equals(deviceId)) {//如果是同一个deviceId,直接只用一个sessionId;避免被挤掉的情况
						setSession(appSessionDtoOld);
						return oldSessionId;
					} else {
						appSessionDtoOld.setOtherDeviceLogin(1);//设置好已经有其他设备登录的标志
						setSession(appSessionDtoOld);
					}
				}
			}
			appUserId2SessionIdCache.saveSessionId(sessionInfo.getUserId(), sessionId);
		}

		setSession(appSessionDto);
		return sessionId;
	}

	/**
	 * 获取用户当前状态信息
	 */
	public void removeSession(String sessionId) {
		jetCacheFactory.getSessionIdCache().remove(sessionId);
	}

}
