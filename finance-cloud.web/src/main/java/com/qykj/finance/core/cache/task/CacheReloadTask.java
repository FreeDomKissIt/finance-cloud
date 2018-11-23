package com.qykj.finance.core.cache.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qykj.finance.sys.service.DictionaryService;
import com.qykj.finance.sys.service.DistrictService;
import com.qykj.finance.sys.service.MenuService;

import lombok.extern.slf4j.Slf4j;

/**
 * 缓存定时更新执行 文件名: Startup.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Component
@Slf4j
public class CacheReloadTask {
	@Autowired
	private MenuService menuService;
	@Autowired
	private DistrictService districtService;
	@Autowired
	private DictionaryService dictionaryService;
	
	@Scheduled(cron = "30 * * * * *") // 每30秒
	public void runTask() {
		log.info("run CacheReloadTask");
		// 刷新菜单数据
		menuService.fetchMenusToCache();
		log.info("CacheReloadTask end");
	}
	
	@Scheduled(cron = "0 */5 * * * *") // 每10分钟
	public void runDictionaryTask() {
		log.info("run runDistrict");
		// 刷新数据字典缓存数据
		dictionaryService.loadDictionaryCache();
		log.info("runDictionaryTask end");
	}

}
