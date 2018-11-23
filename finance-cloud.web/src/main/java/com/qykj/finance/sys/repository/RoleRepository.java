package com.qykj.finance.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qykj.finance.sys.model.Role;

/**
 *  角色仓库
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	// 查询普通有效状态列表
	public static final String QUERY_ROLE_ENABLE_LIST = "select id,name from role where state=1 and isRoot is null";
	// 查询有效状态列表
	public static final String QUERY_ROLE_ROOT_ENABLE_LIST = "select id,name from role where state=1";
	// 查询普通所有列表
	public static final String QUERY_ROLE_LIST = "select id,name,code,note,state from role where isRoot is null";	// 查询所有列表
	// 查询所有列表
	public static final String QUERY_ROLE_ROOT_LIST = "select id,name,code,note,state from role";
}
