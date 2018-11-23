package com.qykj.finance.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.qykj.finance.core.persistence.service.impl.AbstractQueryServiceImpl;
import com.qykj.finance.sys.model.Permission;
import com.qykj.finance.sys.repository.PermissionRepository;
import com.qykj.finance.sys.service.PermissionService;

/**
 *  权限实现
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public class PermissionServiceImpl extends AbstractQueryServiceImpl<Permission> implements PermissionService {
	@Autowired
	PermissionRepository permissionRepository;
	
	/**
	 * 添加新权限
	 * 
	 * @param permission
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public Permission addPermission(Permission permission) {
		return permissionRepository.save(permission);
	}

	/**
	 * 更新权限
	 * 
	 * @param permission
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public Permission updatePermission(Permission permission) {
		return permissionRepository.save(permission);
	}

}
