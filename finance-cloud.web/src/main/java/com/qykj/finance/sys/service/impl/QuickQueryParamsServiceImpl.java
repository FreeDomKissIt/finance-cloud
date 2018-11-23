package com.qykj.finance.sys.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qykj.finance.core.cache.sys.DistrictCache;
import com.qykj.finance.core.cache.sys.MenuNode;
import com.qykj.finance.core.persistence.service.impl.AbstractQueryServiceImpl;
import com.qykj.finance.event.qo.EventReportQo;
import com.qykj.finance.law.qo.LawCaseReportQo;
import com.qykj.finance.sys.model.District;
import com.qykj.finance.sys.model.Menu;
import com.qykj.finance.sys.model.QuickQueryParams;
import com.qykj.finance.sys.model.User;
import com.qykj.finance.sys.repository.QuickQueryParamsRepository;
import com.qykj.finance.sys.service.MenuService;
import com.qykj.finance.sys.service.QuickQueryParamsService;
import com.qykj.finance.util.DistrictUtil;
import com.qykj.finance.util.SessionUtil;

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
	
	/**
	 * 保存事件快捷查询
	 * @param qo
	 * @param queryName
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @return
	 * @since     V1.0.0
	 */
	@Override
	public MenuNode saveEventQuery(EventReportQo qo, String queryName) {
		QuickQueryParams queryParams = new QuickQueryParams();
		String code = qo.getInvolveAreaDownwards();
		
		if(StringUtils.isNoneBlank(code)){
			DistrictCache districtCache = DistrictUtil.getDistrictCache(code);
			District province = districtCache.getProvince();
			qo.setInvolveProvince(province == null?null:province.getCode());
			District city = districtCache.getCity();
			qo.setInvolveCity(city == null?null:city.getCode());
			District district = districtCache.getDistrict();
			qo.setInvolveDistrict(district == null?null:district.getCode());
		}
		
		String jsonString = JSONObject.toJSONString(qo);
		queryParams.setJsonString(jsonString);
		queryParams = addQuickQueryParams(queryParams);
		User user = SessionUtil.getCurrentUser();
		
		Menu menu = menuService.addUserDefined(user.getId(), queryName, QUICK_SEARCH_EVENT_URL, queryParams.getId());
		
		MenuNode menuNode = MenuNode.createByMenu(menu, false, Menu.ALL_MENU);

		return menuNode;
	}
	

	/**
	 * 根据查询id获取EventReportQo
	 * @param queryId
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public EventReportQo getEventReportQo(Integer queryId) {
		QuickQueryParams queryParams = findOne(queryId);
		EventReportQo eventReportQo = JSONObject.toJavaObject(JSONObject.parseObject(queryParams.getJsonString()),
		        EventReportQo.class);
		return eventReportQo;
	}

	public static final String QUICK_SEARCH_LAW_URL = "/lawcasereportsort/quickSearch";
	
	/**
	 *  保存案件快捷查询
	 * @param qo
	 * @param queryName
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public MenuNode saveLawCaseQuery(LawCaseReportQo qo, String queryName) {
		QuickQueryParams queryParams = new QuickQueryParams();
		String jsonString = JSONObject.toJSONString(qo);
		queryParams.setJsonString(jsonString);
		queryParams = addQuickQueryParams(queryParams);
		User user = SessionUtil.getCurrentUser();
		Menu menu = menuService.addUserDefined(user.getId(), queryName, QUICK_SEARCH_LAW_URL, queryParams.getId());
		MenuNode menuNode = MenuNode.createByMenu(menu, false, Menu.ALL_MENU);

		return menuNode;
	}
	
	/**
	 * 根据查询id获取LawCaseReportQo
	 * @param queryId
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public LawCaseReportQo getLawCaseReportQo(Integer queryId) {
		QuickQueryParams queryParams = findOne(queryId);
		LawCaseReportQo lawCaseQo = JSONObject.toJavaObject(JSONObject.parseObject(queryParams.getJsonString()),
				LawCaseReportQo.class);
		return lawCaseQo;
	}
}
