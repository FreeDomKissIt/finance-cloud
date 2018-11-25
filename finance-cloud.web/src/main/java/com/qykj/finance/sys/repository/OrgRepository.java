package com.qykj.finance.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qykj.finance.sys.model.Org;

/**
 *  单位仓库
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Repository
public interface OrgRepository extends JpaRepository<Org, Integer> {
	//  根据行政区划id查找单位列表语句
	public static final String QUERY_BY_DISTRICT_ID_LIST = "select id,name,districtId from org where districtId=? ";
	public static final String QUERY_LIST = "select id,code,name,districtId from org";

	/**
	 * 根据单位名称查找单位
	 * @param name
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	Org findByName(String name);
	/**
	 * 根据单位编码查找单位
	 * @param code
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	Org findByCode(String code);
}
