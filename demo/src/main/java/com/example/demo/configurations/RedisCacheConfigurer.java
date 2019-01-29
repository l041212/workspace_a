package com.example.demo.configurations;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisCacheConfigurer extends CachingConfigurerSupport {

	@Resource
	private LettuceConnectionFactory lettuceConnectionFactory;
	private static Logger logger = LoggerFactory.getLogger(RedisCacheConfigurer.class);
	private StringRedisSerializer stringSerializer = new StringRedisSerializer();
	private GenericFastJsonRedisSerializer objectSerializer = new GenericFastJsonRedisSerializer();
	private SerializationPair<String> stringSerializationPair = SerializationPair.fromSerializer(stringSerializer);
	private SerializationPair<Object> objectSerializationPair = SerializationPair.fromSerializer(objectSerializer);

	@Bean
	@Override
	public CacheManager cacheManager() {
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory);
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration.serializeKeysWith(stringSerializationPair);
		redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(objectSerializationPair);
		redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(3600L));
		logger.info("redis host " + lettuceConnectionFactory.getHostName() + ":" + lettuceConnectionFactory.getPort());
		return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> {
			StringBuilder builder = new StringBuilder();
			builder.append(target.getClass().getSimpleName());
			builder.append(":");
			builder.append(method.getName());
			if (ArrayUtils.isNotEmpty(params)) {
				builder.append(Arrays.stream(params).map(param -> param.getClass().getSimpleName())
						.collect(Collectors.joining(",", "(", ")")));
			}
			return builder.toString();
		};
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(lettuceConnectionFactory);
		template.setKeySerializer(stringSerializer);
		template.setValueSerializer(objectSerializer);
		template.setHashKeySerializer(stringSerializer);
		template.setHashValueSerializer(objectSerializer);
		template.setDefaultSerializer(objectSerializer);
		template.afterPropertiesSet();
		return template;
	}

}
