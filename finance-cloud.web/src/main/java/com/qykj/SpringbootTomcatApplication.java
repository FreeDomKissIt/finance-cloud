package com.qykj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.qykj.finance.core.boot.BeforeStartup;

import lombok.extern.slf4j.Slf4j;

/**
 * 主程序 文件名: SpringbootApplication.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
@ServletComponentScan // 扫描方式添加Servlet、Filter、ServletListener
@EnableTransactionManagement
@ImportResource("classpath:transaction.xml")
@Slf4j
// @EnableRedisHttpSession//开启后使用redis来存储session
public class SpringbootTomcatApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		log.info("start in configure");
		BeforeStartup.initDB();// 建库
		return builder.sources(SpringbootTomcatApplication.class);
	}

	/**
	 * 程序启动主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("start in main");
		BeforeStartup.initDB();// 建库
		SpringApplication.run(SpringbootTomcatApplication.class, args);

	}
}
