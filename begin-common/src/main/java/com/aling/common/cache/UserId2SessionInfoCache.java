package com.aling.common.cache;

import com.aling.core.bean.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserId2SessionInfoCache {

	private static final String CACHE_PREFIX = "USERID2SESSIONINFO_";

	@Autowired
	private JetCacheFactory jetCacheFactory;

	public void saveSessionInfo(String userId, SessionInfo sessionInfo) {
		String key = CACHE_PREFIX + userId;

		sessionInfo.setLastSaveTime(System.currentTimeMillis());

		jetCacheFactory.getUserSessionCache().put(key, sessionInfo);
	}

	public SessionInfo getSessionInfo(String userId) {
		String key = CACHE_PREFIX + userId;
		return jetCacheFactory.getUserSessionCache().get(key);
	}

	public void removeSessionInfo(String userId) {
		String key = CACHE_PREFIX + userId;
		jetCacheFactory.getUserSessionCache().remove(key);
	}
}
