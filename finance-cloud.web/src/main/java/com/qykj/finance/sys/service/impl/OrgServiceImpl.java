package com.qykj.finance.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.qykj.finance.core.persistence.service.QueryService;
import com.qykj.finance.core.persistence.service.impl.AbstractQueryServiceImpl;
import com.qykj.finance.sys.model.Org;
import com.qykj.finance.sys.repository.OrgRepository;
import com.qykj.finance.sys.service.OrgService;

/**
 * 单位接口实现 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Service
public class OrgServiceImpl extends AbstractQueryServiceImpl<Org> implements OrgService {
	@Autowired
	OrgRepository orgRepository;
	@Autowired
	QueryService queryService;
	
	/**
	 * 添加新单位
	 * @param org
	 * @param districtId
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	@Override
	public Org addOrg(Org org, Integer districtId) {
		return orgRepository.save(org);
	}

	/**
	 * 更新单位
	 * @param org
	 * @param districtId
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	@Override
	public Org updateOrg(Org org, Integer districtId) {
		return orgRepository.save(org);
	}

	/**
	 * 根据单位名称查找单位
	 * 
	 * @param name
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@Override
	public Org findByOrgName(String name) {
		return orgRepository.findByName(name);
	}

	/**
	 * 根据单位编码查找单位
	 * 
	 * @param name
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@Override
	public Org findByOrgCode(String code) {
		return orgRepository.findByCode(code);
	}
	
	/**
	 * 根据行政区划编码查找单位分页列表
	 * @param pageNum
	 * @param pageSize
	 * @param districtId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public Page<Org> findPageByDistrictCode(Integer pageNum, Integer pageSize, String districtCode) {
		Integer districtId = Integer.valueOf(districtCode);
		Page<Org> orgs = selectPage(OrgRepository.QUERY_BY_DISTRICT_ID_LIST, pageNum, pageSize, districtId);
		return orgs;
	}

	/**
	 * 根据行政区划编码查找单位列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param districtId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public List<Org> findByDistrictCode(String districtCode) {
		Integer districtId = Integer.valueOf(districtCode);
		List<Org> orgs = select(OrgRepository.QUERY_BY_DISTRICT_ID_LIST, districtId);
		return orgs;
	}

	/**
	 * 单位分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public Page<Map<String, Object>> findPageList(Integer pageNum, Integer pageSize) {
		Page<Map<String, Object>> orgs = queryService.selectPage(OrgRepository.QUERY_LIST, pageNum, pageSize);
		
		return orgs;
	}


	/**
	 * 查找所有单位
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public List<Org> findAll() {
		return orgRepository.findAll();
	}
	
}
