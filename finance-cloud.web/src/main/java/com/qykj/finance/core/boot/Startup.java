package com.qykj.finance.core.boot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.qykj.finance.core.cache.CacheContainer;
import com.qykj.finance.core.cache.sys.MenuNode;
import com.qykj.finance.sys.service.DictionaryService;
import com.qykj.finance.sys.service.MenuService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 项目启动初始化执行 文件名: Startup.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Component
@Slf4j
@Setter
@Getter
public class Startup implements CommandLineRunner {
	@Autowired
	private MenuService menuService;
	@Autowired
	private DictionaryService dictionaryService;

	@Override
	public void run(String... args) throws Exception {
		// 授权license缓存
		//CacheContainer.put(CacheContainer.MACHINE_KEY, MachineCode.getCode());
		// 初始化菜单缓存数据
		initMenuData();
		// 缓存加载字典
		dictionaryService.loadDictionaryCache();
		log.info("Startup");
	}

	private void initMenuData() {
		List<MenuNode> webMenus = menuService.getAllMenus();
		CacheContainer.put(CacheContainer.MENU_KEY, webMenus);
	}
}
