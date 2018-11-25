package com.qykj.finance.sys.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qykj.finance.core.persistence.service.BaseQueryService;
import com.qykj.finance.sys.model.Org;

/**
 * 单位接口 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public interface OrgService extends BaseQueryService<Org> {
	
	/**
	 * 添加新单位
	 * @param org
	 * @param districtId
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	Org addOrg(Org org, Integer districtId);

	/**
	 * 更新单位
	 * @param org
	 * @param districtId
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	Org updateOrg(Org org, Integer districtId);

	/**
	 * 根据单位名称查找单位
	 * 
	 * @param name
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	Org findByOrgName(String name);
	
	/**
	 * 根据单位编码查找单位
	 * 
	 * @param name
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	Org findByOrgCode(String code);

	/**
	 * 根据行政区划id查单位分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param districtId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	Page<Org> findPageByDistrictCode(Integer pageNum, Integer pageSize, String districtCode);

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
	Page<Map<String, Object>> findPageList(Integer pageNum, Integer pageSize);

	/**
	 * 根据行政区划id查单位列表
	 * 
	 * @param districtId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	List<Org> findByDistrictCode(String districtCode);

	/**
	 * 查找所有单位
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	List<Org> findAll();

}
