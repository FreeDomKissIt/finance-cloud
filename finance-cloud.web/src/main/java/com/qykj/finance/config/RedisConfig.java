package com.qykj.finance.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.qykj.finance.core.redis.RedisObjectSerializer;

/**
 * redis缓存配置类
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
//@Configuration
//@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	/**
	 *  缓存管理
	 * @param redisTemplate
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	@Bean
	public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(1800);
		return cacheManager;
	}

	/**
	 *  redis 模板
	 * @param factory
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new RedisObjectSerializer());
		return template;
	}
	

	/**
	 *  通用key
	 * @param factory
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	@Bean
	public KeyGenerator keyGenerator() {
		/* JDK8  lambda 
		 * this same as 
		 * return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
		 */
		return (target, method, params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append(method.getName());
			for (Object obj : params) {
				sb.append(obj.toString());
			}
			return sb.toString();
		};
	}

}