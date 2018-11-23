package com.qykj.finance.demo.service;

import com.qykj.finance.core.persistence.service.BaseQueryService;
import com.qykj.finance.demo.model.Demo;


public interface DemoService extends BaseQueryService<Demo>{
	/**
	 * 根据状态更新
	 * @param demo
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public void updateDemoByStatus(Demo demo);
}
