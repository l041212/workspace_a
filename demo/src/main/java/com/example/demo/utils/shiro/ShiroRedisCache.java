package com.example.demo.utils.shiro;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.exceptions.PrincipalIdNullException;
import com.example.demo.exceptions.PrincipalInstanceException;

import io.micrometer.core.instrument.util.StringUtils;

/**
 * 
 * @author Hekate
 * @see https://blog.csdn.net/qq_34021712/article/details/80791219
 *
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {

	private static Logger logger = LoggerFactory.getLogger(ShiroRedisCache.class);
	private static ShiroRedisManager shiroRedisManager = ShiroRedisManager.getInstance();
	private String keyPrefix = "shiro::unkown::";
	private String principalIdFieldName = "user";
	private int expire = 0;

	public ShiroRedisCache(String keyPrefix, String principalIdFieldName, int expire) {
		if (StringUtils.isNotBlank(keyPrefix)) {
			this.keyPrefix = keyPrefix;
		}
		if (StringUtils.isNotBlank(principalIdFieldName)) {
			this.principalIdFieldName = principalIdFieldName;
		}
		if (expire > 0) {
			this.expire = expire;
		}
	}

	public String getPrincipalIdFieldName() {
		return principalIdFieldName;
	}

	public void setPrincipalIdFieldName(String principalIdFieldName) {
		this.principalIdFieldName = principalIdFieldName;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	@Override
	public void clear() throws CacheException {
		logger.debug("clear cache...");
		try {
			Set<String> keys = shiroRedisManager.scan(this.keyPrefix + "*");
			if (keys != null && !keys.isEmpty()) {
				shiroRedisManager.delete(keys);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public V get(K key) throws CacheException {
		logger.debug("get key: ", key);
		try {
			if (key != null) {
				String redisCacheKey = getRedisCacheKey(key);
				Object rawValue = shiroRedisManager.get(redisCacheKey);
				if (rawValue != null) {
					return (V) rawValue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<K> keys() {
		logger.debug("get keys...");
		try {
			Set<String> keys = shiroRedisManager.scan(this.keyPrefix + "*");
			if (CollectionUtils.isNotEmpty(keys)) {
				return keys.stream().map(key -> (K) key).collect(Collectors.toSet());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptySet();
	}

	@Override
	public V put(K key, V value) throws CacheException {
		logger.debug("put key: ", key);
		try {
			if (key != null) {
				String redisCacheKey = getRedisCacheKey(key);
				shiroRedisManager.set(redisCacheKey, value != null ? value : null, expire);
			} else {
				logger.warn("Saving a null key is meaningless, return value directly without call Redis.");
			}
		} catch (Exception e) {
			throw new CacheException(e);
		}
		return value;
	}

	@Override
	@SuppressWarnings("unchecked")
	public V remove(K key) throws CacheException {
		logger.debug("remove key: ", key);
		try {
			if (key != null) {
				String redisCacheKey = getRedisCacheKey(key);
				Object rawValue = shiroRedisManager.get(redisCacheKey);
				shiroRedisManager.delete(redisCacheKey);
				return (V) rawValue;
			}
		} catch (Exception e) {
			throw new CacheException(e);
		}
		return null;
	}

	@Override
	public int size() {
		Long longSize = 0L;
		try {
			longSize = shiroRedisManager.scanSize(this.keyPrefix + "*");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return longSize.intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		logger.debug("get values...");
		try {
			Set<String> keys = shiroRedisManager.scan(this.keyPrefix + "*");
			if (CollectionUtils.isNotEmpty(keys)) {
				return keys.stream().map(key -> (V) shiroRedisManager.get(key)).filter(value -> value != null)
						.collect(Collectors.toCollection(null));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptySet();
	}

	private String getRedisCacheKey(K key) {
		String cacheKey = null;
		if (key != null) {
			cacheKey = key.toString();
			if (key instanceof PrincipalCollection) {
				cacheKey = getRedisKeyFromPrincipalIdField((PrincipalCollection) key);
			}
			cacheKey = this.keyPrefix + cacheKey;
		}
		return cacheKey;
	}

	private String getRedisKeyFromPrincipalIdField(PrincipalCollection key) {
		Object principalObject = key.getPrimaryPrincipal();
		Method[] methods = principalObject.getClass().getDeclaredMethods();
		Method pincipalIdGetter = null;
		for (Method method : methods) {
			String[] fieldNames = { "authCacheKey", "id", this.principalIdFieldName };
			for (String fieldName : Arrays.asList(fieldNames)) {
				String methodName = "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
				if (methodName.equals(method.getName())) {
					pincipalIdGetter = method;
					break;
				}
			}
		}
		if (pincipalIdGetter == null) {
			throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName);
		}
		try {
			Object idObject = pincipalIdGetter.invoke(principalObject);
			if (idObject == null) {
				throw new PrincipalIdNullException(principalObject.getClass(), this.principalIdFieldName);
			}
			return idObject.toString();
		} catch (IllegalAccessException e) {
			throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, e);
		} catch (InvocationTargetException e) {
			throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, e);
		}
	}

}
