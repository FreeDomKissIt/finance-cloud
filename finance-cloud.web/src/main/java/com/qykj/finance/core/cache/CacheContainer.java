package com.qykj.finance.core.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.qykj.finance.core.cache.sys.MenuNode;

import lombok.Getter;
import lombok.Setter;

/**
 * 缓存容器 文件名: CacheContainer.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Setter
@Getter
public class CacheContainer {
	
	public static final String MENU_KEY = "menu-key";
	public static final String PUBLIC_KEY = "public-key";
	public static final String LICENSE = "license";
	public static final String MACHINE_KEY = "machine-key";
	
	private Map<String, Object> cache = new ConcurrentHashMap<>();
	private static CacheContainer cacheContainer = new CacheContainer();
	
	private CacheContainer() {
		
	}
	
	public static CacheContainer getInstance() {
		return cacheContainer;
	}
	
	public static void put(String key, Object value) {
		getInstance().cache.put(key, value);
	}
	
	public static Object get(String key) {
		return getInstance().cache.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public static List<MenuNode> getMenus() {
		return (List<MenuNode>)get(MENU_KEY);
	}
	
	public static String getPublicKey() {
		return (String)get(PUBLIC_KEY);
	}
	
	public static String getMachineCode() {
		return (String)get(MACHINE_KEY);
	}
	
	public static String getLicense() {
		return (String)get(LICENSE);
	}
}
