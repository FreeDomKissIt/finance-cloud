package com.qykj.finance.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qykj.finance.core.persistence.service.impl.AbstractQueryServiceImpl;
import com.qykj.finance.sys.model.QuickQueryParams;
import com.qykj.finance.sys.repository.QuickQueryParamsRepository;
import com.qykj.finance.sys.service.MenuService;
import com.qykj.finance.sys.service.QuickQueryParamsService;

/**
 * 快捷查询实现 文件名: PermissionServiceImpl.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Service
public class QuickQueryParamsServiceImpl extends AbstractQueryServiceImpl<QuickQueryParams> implements QuickQueryParamsService {
	
	@Autowired
	QuickQueryParamsRepository quickQueryParamsRepository;
	@Autowired
	MenuService menuService;
	
	/**
	 * 添加快捷查询
	 * @param permission
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public QuickQueryParams addQuickQueryParams(QuickQueryParams quickQueryParams) {
		return repository.save(quickQueryParams);
	}
	
	/**
	 * 更新快捷查询
	 * @param permission
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public QuickQueryParams updateQuickQueryParams(QuickQueryParams quickQueryParams) {
		return repository.save(quickQueryParams);
	}
	
	public static final String QUICK_SEARCH_EVENT_URL = "/event/quickSearch";
	
}
