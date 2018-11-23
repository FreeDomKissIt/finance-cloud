package com.qykj.finance.sys.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qykj.finance.core.persistence.service.BaseQueryService;
import com.qykj.finance.sys.model.Role;

/**
 * 角色接口 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public interface RoleService extends BaseQueryService<Role> {

	/**
	 * 添加新角色
	 * @param role
	 * @param menuIds
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	Role addRole(Role role, List<Integer> menuIds);

	/**
	 * 更新角色
	 * @param role
	 * @param menuIds
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	Role updateRole(Role role, List<Integer> menuIds);

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @return
	 * @since 1.0.0
	 */
	void deleteRole(Integer id);

	/**
	 * 角色分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	Page<Role> findPageList(Integer pageNum, Integer pageSize);

	/**
	 * 查找所有角色
	 * 
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	List<Role> findAll();
	
	/**
	 * 查找所有有效角色 用于下拉框选择
	 * 
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	List<Map<String, Object>> findAllEnable();
	
	/**
	 * 查找所有有效角色 用于下拉框选择
	 * 
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	List<Map<String, Object>> findAllROOTEnable();
	
	/**
	 * 角色分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	Page<Role> findRootPageList(Integer pageNum, Integer pageSize);

}
