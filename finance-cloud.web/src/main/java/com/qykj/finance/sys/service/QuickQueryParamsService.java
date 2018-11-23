package com.qykj.finance.sys.service;

import com.qykj.finance.core.cache.sys.MenuNode;
import com.qykj.finance.core.persistence.service.BaseQueryService;
import com.qykj.finance.event.qo.EventReportQo;
import com.qykj.finance.law.qo.LawCaseReportQo;
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

	/**
	 * 保存事件快捷查询
	 * @param qo
	 * @param queryName
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @return
	 * @since     V1.0.0
	 */
	MenuNode saveEventQuery(EventReportQo qo, String queryName);

	/**
	 * 根据查询id获取EventReportQo
	 * @param queryId
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	EventReportQo getEventReportQo(Integer queryId);

	/**
	 *  保存案件快捷查询
	 * @param qo
	 * @param queryName
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	MenuNode saveLawCaseQuery(LawCaseReportQo qo, String queryName);

	/**
	 * 根据查询id获取LawCaseReportQo
	 * @param queryId
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	LawCaseReportQo getLawCaseReportQo(Integer queryId);
	
}
