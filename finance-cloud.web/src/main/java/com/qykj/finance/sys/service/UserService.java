package com.qykj.finance.sys.service;

import com.github.pagehelper.Page;
import com.qykj.finance.core.persistence.service.BaseQueryService;
import com.qykj.finance.sys.model.User;

/**
 * 用户接口 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public interface UserService extends BaseQueryService<User> {

	/**
	 * 添加新用户
	 * 
	 * @param user
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	User addUser(User user);

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	User updateUser(User user);

	/**
	 * 根据用户名获得用户对象
	 * 
	 * @param username
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	User obtain(String username);

	/**
	 * 重置用户名密码
	 * 
	 * @param userId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	boolean resetPassword(Integer userId);

	/**
	 * 用户修改密码
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @since 1.0.0
	 */
	boolean updatePassword(Integer userId, String oldPassword, String newPassword);

	/**
	 * 根据组织id查用户分页
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param orgId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	Page<User> findPageByOrgId(Integer pageNo, Integer pageSize, Integer orgId);

	/**
	 * 查询用户列表 用于列表显示
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	Page<User> findPageList(Integer pageNo, Integer pageSize);

	/**
	 * 添加新用户
	 * 
	 * @param user
	 * @param orgId
	 * @param roleId
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	void addUser(User user, Integer orgId, Integer roleId);

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @param orgId
	 * @param roleId
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	void updateUser(User user, Integer orgId, Integer roleId);

	/**
	 * 启用
	 * 
	 * @param id
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	void enable(Integer id);

	/**
	 * 禁用
	 * 
	 * @param id
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	void unenable(Integer id);
	
	/**
	 * 查询用户列表 用于列表显示
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	Page<User> findROOTPageList(Integer pageNo, Integer pageSize);

}
