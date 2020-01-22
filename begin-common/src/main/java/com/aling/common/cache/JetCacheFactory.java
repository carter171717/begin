package com.aling.common.cache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.aling.core.bean.SessionInfo;
import com.aling.core.dto.system.AppSessionDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Data
public class JetCacheFactory {
	/**
	 * 通用缓存
	 */
	@CreateCache(name = JetCacheKey.NAME_COMMON_CACHE, cacheType = CacheType.REMOTE, expire = 600)
	@CacheRefresh(timeUnit = TimeUnit.MINUTES, refresh = 60, stopRefreshAfterLastAccess = 100)
	@CachePenetrationProtect
	private Cache<String, Object> commonCache;

	//会话缓存
	@CreateCache(name = JetCacheKey.NAME_SYS_USER_SESSION_CACHE, cacheType = CacheType.REMOTE, expire = 12, timeUnit = TimeUnit.HOURS)
	@CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = 10)
	@CachePenetrationProtect
	private Cache<String, SessionInfo> sysUserSessionCache;

	@CreateCache(name = JetCacheKey.NAME_USER_SESSION_CACHE, cacheType = CacheType.REMOTE, expire = 12, timeUnit = TimeUnit.HOURS)
	@CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = 10)
	@CachePenetrationProtect
	private Cache<String, SessionInfo> userSessionCache;

	@CreateCache(name = JetCacheKey.NAME_SESSION_ID_CACHE, cacheType = CacheType.REMOTE, expire = 12, timeUnit = TimeUnit.HOURS)
	@CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = 10)
	@CachePenetrationProtect
	private Cache<String, String> sessionIdCache;

	@CreateCache(name = JetCacheKey.NAME_SESSION_ID_CACHE, cacheType = CacheType.REMOTE, expire = 12, timeUnit = TimeUnit.HOURS)
	@CacheRefresh(timeUnit = TimeUnit.HOURS, refresh = 1, stopRefreshAfterLastAccess = 10)
	@CachePenetrationProtect
	private Cache<String, AppSessionDto> appSessionCache;

}
