package com.qykj.finance.sys.service;

import com.qykj.finance.core.persistence.service.BaseQueryService;
import com.qykj.finance.sys.model.QuickQueryParams;

/**
 * 快捷查询接口 
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public interface QuickQueryParamsService extends BaseQueryService<QuickQueryParams> {
	
	/**
	 * 添加快捷查询
	 * @param permission
	 * @return
	 * @since 1.0.0
	 */
	QuickQueryParams addQuickQueryParams(QuickQueryParams quickQueryParams);
	
	/**
	 * 更新快捷查询
	 * @param permission
	 * @return
	 * @since 1.0.0
	 */
	QuickQueryParams updateQuickQueryParams(QuickQueryParams quickQueryParams);

}
