package com.example.demo.utils.shiro;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Hekate
 * @see https://blog.csdn.net/qq_34021712/article/details/80791219
 *
 */
@SuppressWarnings("rawtypes")
public class ShiroRedisCacheManager implements CacheManager {

	private static final Logger logger = LoggerFactory.getLogger(ShiroRedisCacheManager.class);
	private static final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	public static final String DEFAULT_CACHE_KEY_PREFIX = "shiro::cache::";
	public static final String DEFAULT_PRINCIPAL_ID_FIELD_NAME = "username";
	public static final int DEFAULT_EXPIRE = 0;
	private String keyPrefix = DEFAULT_CACHE_KEY_PREFIX;
	private String principalIdFieldName = DEFAULT_PRINCIPAL_ID_FIELD_NAME;
	private int expire = DEFAULT_EXPIRE;

	@Override
	@SuppressWarnings("unchecked")
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		logger.debug("get cache, name: ", name);
		Cache cache = caches.get(name);
		if (cache == null) {
			cache = new ShiroRedisCache<K, V>(keyPrefix + name + "::", principalIdFieldName, expire);
			caches.put(name, cache);
		}
		return cache;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public String getPrincipalIdFieldName() {
		return principalIdFieldName;
	}

	public void setPrincipalIdFieldName(String principalIdFieldName) {
		this.principalIdFieldName = principalIdFieldName;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

}
