package com.qykj.finance.demo.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qykj.finance.core.persistence.service.impl.AbstractQueryServiceImpl;
import com.qykj.finance.demo.model.Demo;
import com.qykj.finance.demo.repository.DemoRepository;
import com.qykj.finance.demo.service.DemoService;

@Service
public class DemoServiceImpl extends AbstractQueryServiceImpl<Demo> implements DemoService {
	@Autowired
	DemoRepository demoRepository;

	/**
	 * 根据状态更新
	 * @param demo
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public void updateDemoByStatus(Demo demo) {
		demoRepository.updateSatatusById(demo.getStatus(), demo.getId());
	}

}
