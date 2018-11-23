package com.qykj.finance.sys.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.qykj.finance.core.persistence.service.QueryService;
import com.qykj.finance.core.persistence.service.impl.AbstractQueryServiceImpl;
import com.qykj.finance.sys.model.Role;
import com.qykj.finance.sys.model.RoleMenu;
import com.qykj.finance.sys.repository.RoleMenuRepository;
import com.qykj.finance.sys.repository.RoleRepository;
import com.qykj.finance.sys.service.RoleService;
import com.qykj.finance.util.SessionUtil;

/**
 * 角色接口实现 文件名: RoleServiceImpl.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Service
public class RoleServiceImpl extends AbstractQueryServiceImpl<Role> implements RoleService {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	RoleMenuRepository roleMenuRepository;
	@Autowired
	QueryService queryService;

	
	public static final int SYS_ADMIN = 1;
	/**
	 * 添加新角色
	 * @param role
	 * @param menuIds
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public Role addRole(Role role, List<Integer> menuIds) {
		Set<RoleMenu> roleMenus = saveRoleMenu(menuIds);
		role.setRoleMenus(roleMenus);
		roleRepository.save(role);
		return role;
	}

	/**
	 * 保存角色对应菜单配置
	 * @param menuIds
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	private Set<RoleMenu> saveRoleMenu(List<Integer> menuIds) {
		Set<RoleMenu> roleMenus = new HashSet<RoleMenu>();
		for (Integer menuId : menuIds) {
			RoleMenu roleMenu = RoleMenu.builder(menuId);
			roleMenus.add(roleMenu);
		}
		roleMenuRepository.save(roleMenus);
		return roleMenus;
	}

	/**
	 * 更新角色
	 * @param role
	 * @param menuIds
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public Role updateRole(Role role, List<Integer> menuIds) {
		Role dbRole = roleRepository.findOne(role.getId());
		//更新字段
		dbRole.setName(role.getName());
		dbRole.setNote(role.getNote());
		dbRole.setCode(role.getCode());
		dbRole.setState(role.getState());
		// 删除原有菜单关联
		roleMenuRepository.delete(dbRole.getRoleMenus());
		// 新增菜单关联
		Set<RoleMenu> roleMenus = saveRoleMenu(menuIds);
		dbRole.setRoleMenus(roleMenus);
		roleRepository.save(dbRole);
		return dbRole;
	}

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public void deleteRole(Integer id) {
		Role dbRole = roleRepository.findOne(id);
		// 删除原有菜单关联
		roleMenuRepository.delete(dbRole.getRoleMenus());
		roleRepository.delete(dbRole);
	}

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
	@Override
	public Page<Role> findPageList(Integer pageNum, Integer pageSize) {
		Page<Role> roles = selectPage(RoleRepository.QUERY_ROLE_LIST, pageNum, pageSize);
		return roles;
	}
	
	/**
	 * 查找所有角色
	 * 
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public List<Role> findAll() {
		return select(RoleRepository.QUERY_ROLE_LIST);
	}

	/**
	 * 查找所有有效角色 用于下拉框选择
	 * 
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public List<Map<String, Object>> findAllEnable() {
		return queryService.select(RoleRepository.QUERY_ROLE_ENABLE_LIST);
	}
	
	/**
	 * 查找所有有效角色 用于下拉框选择
	 * 
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public List<Map<String, Object>> findAllROOTEnable() {
		return queryService.select(RoleRepository.QUERY_ROLE_ROOT_ENABLE_LIST);
	}
	
	
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
	@Override
	public Page<Role> findRootPageList(Integer pageNum, Integer pageSize) {
		Page<Role> roles = selectPage(RoleRepository.QUERY_ROLE_ROOT_LIST, pageNum, pageSize);
		return roles;
	}

}
