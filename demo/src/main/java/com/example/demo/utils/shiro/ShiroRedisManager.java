package com.example.demo.utils.shiro;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.util.CollectionUtils;

import com.example.demo.DemoApplication;

/**
 * 
 * @author Hekate
 * @see https://blog.csdn.net/qq_34021712/article/details/80791219
 *
 */
public class ShiroRedisManager {

	private static ConfigurableApplicationContext applicationContext;
	private static RedisTemplate<String, Object> redisTemplate;

	@SuppressWarnings("unchecked")
	private ShiroRedisManager() {
		applicationContext = DemoApplication.getApplicationContext();
		redisTemplate = (RedisTemplate<String, Object>) applicationContext.getBean("redisTemplate");
	}

	private static class SingletonClassInstance {
		private static final ShiroRedisManager instance = new ShiroRedisManager();
	}

	public static ShiroRedisManager getInstance() {
		return SingletonClassInstance.instance;
	}

	public void expire(String key, long time) {
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	@SuppressWarnings("unchecked")
	public void delete(String... keys) {
		if (keys != null && keys.length > 0) {
			if (keys.length == 1) {
				redisTemplate.delete(keys[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(keys));
			}
		}
	}

	public void delete(Collection<String> keys) {
		redisTemplate.delete(keys);
	}

	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	public void set(String key, Object value, long time) {
		if (time > 0) {
			redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
		} else {
			set(key, value);
		}
	}

	public Set<String> scan(String key) {
		return redisTemplate.execute((RedisConnection connection) -> {
			Set<String> binaryKeys = new HashSet<String>();
			Cursor<byte[]> cursor = connection
					.scan(new ScanOptions.ScanOptionsBuilder().match(key).count(1000).build());
			while (cursor.hasNext()) {
				binaryKeys.add(new String(cursor.next()));
			}
			return binaryKeys;
		});
	}

	public long scanSize(String key) {
		return redisTemplate.execute((RedisConnection connection) -> {
			long count = 0L;
			Cursor<byte[]> cursor = connection
					.scan(new ScanOptions.ScanOptionsBuilder().match(key).count(1000).build());
			while (cursor.hasNext()) {
				cursor.next();
				count++;
			}
			return count;
		});
	}

}
