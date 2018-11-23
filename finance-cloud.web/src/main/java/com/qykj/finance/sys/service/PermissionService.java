package com.qykj.finance.sys.service;

import com.qykj.finance.core.persistence.service.BaseQueryService;
import com.qykj.finance.sys.model.Permission;

/**
 * 权限接口 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public interface PermissionService extends BaseQueryService<Permission> {
	
	/**
	 * 添加新权限
	 * @param permission
	 * @return
	 * @since 1.0.0
	 */
	Permission addPermission(Permission permission);
	
	/**
	 * 更新权限
	 * @param permission
	 * @return
	 * @since 1.0.0
	 */
	Permission updatePermission(Permission permission);
	
}
