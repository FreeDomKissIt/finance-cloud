/**
 * 
 */
package com.qykj.finance.core.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Shiro缓存 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ShiroCache<K, V> implements Cache<K, V> {

	/**
	 * 缓存名
	 */
	private static final String REDIS_SHIRO_CACHE = "shiro-cache:";
	/**
	 * 缓存关键字
	 */
	private String cacheKey;
	/**
	 * redis模板
	 */
	private RedisTemplate<K, V> redisTemplate;
	/**
	 * 全局
	 */
	private long globalExpire = 30;

	public ShiroCache(String name, RedisTemplate client) {
		this.cacheKey = REDIS_SHIRO_CACHE + name + ":";
		this.redisTemplate = client;
	}

	/**
	 * 根据键获得值
	 */
	@Override
	public V get(K key) throws CacheException {
		redisTemplate.boundValueOps(getCacheKey(key)).expire(globalExpire, TimeUnit.MINUTES);
		return redisTemplate.boundValueOps(getCacheKey(key)).get();
	}

	/**
	 * 存入键值对
	 */
	@Override
	public V put(K key, V value) throws CacheException {
		V old = get(key);
		redisTemplate.boundValueOps(getCacheKey(key)).set(value);
		return old;
	}

	/**
	 * 移除
	 */
	@Override
	public V remove(K key) throws CacheException {
		V old = get(key);
		redisTemplate.delete(getCacheKey(key));
		return old;
	}

	/**
	 * 清除所有信息
	 */
	@Override
	public void clear() throws CacheException {
		redisTemplate.delete(keys());
	}

	/**
	 * 返回缓存列表大小
	 */
	@Override
	public int size() {
		return keys().size();
	}

	/**
	 * 获得缓存对象所有键集合
	 */
	@Override
	public Set<K> keys() {
		return redisTemplate.keys(getCacheKey("*"));
	}

	/**
	 * 获得缓存对象所有值集合
	 */
	@Override
	public Collection<V> values() {
		Set<K> set = keys();
		List<V> list = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(set)) {
			for (K s : set) {
				list.add(get(s));
			}
		}
		return list;
	}

	/**
	 * 根据键获得值
	 * 
	 * @param k
	 *            键
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	private K getCacheKey(Object k) {
		return (K) (this.cacheKey + k);
	}
}
