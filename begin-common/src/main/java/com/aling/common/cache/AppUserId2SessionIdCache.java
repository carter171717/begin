package com.aling.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppUserId2SessionIdCache {

	private static final String CACHE_PREFIX = "USERID2SESSIONID_";

	@Autowired
	private JetCacheFactory jetCacheFactory;

	public String getSessionId(String userId) {
		String key = CACHE_PREFIX + userId;
		return jetCacheFactory.getSessionIdCache().get(key);
	}

	public void saveSessionId(String userId, String appSessionId) {
		String key = CACHE_PREFIX + userId;
		jetCacheFactory.getSessionIdCache().put(key, appSessionId);
	}
}
