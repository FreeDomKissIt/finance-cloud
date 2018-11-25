package com.qykj.finance.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.qykj.finance.common.CommonConstant;
import com.qykj.finance.core.persistence.service.impl.AbstractQueryServiceImpl;
import com.qykj.finance.core.util.EncryptUtil;
import com.qykj.finance.sys.model.User;
import com.qykj.finance.sys.repository.UserRepository;
import com.qykj.finance.sys.service.RoleService;
import com.qykj.finance.sys.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户接口实现 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Service
@Slf4j
public class UserServiceImpl extends AbstractQueryServiceImpl<User> implements UserService {
	@Autowired
	private UserRepository userInfoRepository;
	@Autowired
	private RoleService roleService;

	/**
	 * 添加新用户
	 * @param user
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public User addUser(User user) {
		String salt = EncryptUtil.createSalt();
		String password = EncryptUtil.encrypt(EncryptUtil.DEFAULT_PASSWORD, salt);
		user.setSalt(salt);
		user.setPassword(password);
		return userInfoRepository.save(user);
	}

	/**
	 * 更新用户
	 * @param user
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public User updateUser(User user) {
		User dbUser = userInfoRepository.getOne(user.getId());
		dbUser.setName(user.getName());
		dbUser.setUsername(user.getUsername());
		dbUser.setPhone(user.getPhone());
		return userInfoRepository.save(dbUser);
	}

	/**
	 * 根据用户名获得用户对象
	 * @param username
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public User obtain(String username) {
		return userInfoRepository.findByUsername(username);
	}

	/**
	 * 重置用户名密码
	 * @param userId
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public boolean resetPassword(Integer userId) {
		User user = userInfoRepository.getOne(userId);
		if (null != user) {
			String salt = EncryptUtil.createSalt();
			String password = EncryptUtil.encrypt(EncryptUtil.DEFAULT_PASSWORD, salt);
			user.setSalt(salt);
			user.setPassword(password);
			userInfoRepository.save(user);
			return true;
		}
		log.info("重置用户密码失败未找到该用户id为{}的用户", userId);
		return false;
	}

	/**
	 * 用户修改密码
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public boolean updatePassword(Integer userId, String oldPassword, String newPassword) {
		User user = userInfoRepository.getOne(userId);
		if (user != null) {
			String salt = user.getSalt();
			String oldSaltPassword = EncryptUtil.encrypt(oldPassword, salt);
			if (oldSaltPassword.equals(user.getPassword())) {
				String newSalt = EncryptUtil.createSalt();
				String newSaltPassword = EncryptUtil.encrypt(newPassword, newSalt);
				user.setSalt(newSalt);
				user.setPassword(newSaltPassword);
				userInfoRepository.save(user);
				return true;
			}
		}

		return false;
	}

	/**
	 * 根据组织id查用户分页
	 * @param pageNo
	 * @param pageSize
	 * @param orgId
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public Page<User> findPageByOrgId(Integer pageNo, Integer pageSize, Integer orgId) {
		Page<User> users = selectPage(UserRepository.QUERY_LIST_BY_ORG_ID, pageNo, pageSize,
				orgId);
		return users;
	}
	
	
	/**
	 * 查询用户列表 用于列表显示
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public Page<User> findPageList(Integer pageNo, Integer pageSize) {
		Page<User> users = selectPage(UserRepository.QUERY_LIST, pageNo, pageSize);
		return users;
	}
	
	/**
	 * 查询用户列表 用于列表显示
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public Page<User> findROOTPageList(Integer pageNo, Integer pageSize) {
		Page<User> users = selectPage(UserRepository.QUERY_ROOT_LIST, pageNo, pageSize);
		return users;
	}

	/**
	 * 添加新用户
	 * @param user
	 * @param orgId
	 * @param roleId
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 *//*
	@Override
	public void addUser(User user, Integer orgId, Integer roleId) {
		updateOrAddRoleAndOrg(user, orgId, roleId);
		addUser(user);
	}

	
	*//**
	 * 修改用户角色和组织
	 * @param user
	 * @param orgId
	 * @param roleId
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 *//*
	private void updateOrAddRoleAndOrg(User user, Integer orgId, Integer roleId) {
		user.setRole(null);
		user.setOrg(null);
		if (orgId != null) {
			Org org = orgService.findOne(orgId);
			user.setOrg(org);
		}
		if (roleId != null) {
			Role role = roleService.findOne(roleId);
			user.setRole(role);
		}
	}*/
	
	/**
	 * 修改用户
	 * @param user
	 * @param orgId
	 * @param roleId
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	/*@Override
	public void updateUser(User user, Integer orgId, Integer roleId) {
		User dbUser = userInfoRepository.getOne(user.getId());
		updateOrAddRoleAndOrg(dbUser, orgId, roleId);
		dbUser.setName(user.getName());
		dbUser.setUsername(user.getUsername());
		dbUser.setPhone(user.getPhone());
		dbUser.setAddress(user.getAddress());
		dbUser.setEmail(user.getEmail());
		dbUser.setState(user.getState());
		userInfoRepository.save(dbUser);
	}*/
	
	/**
	 * 启用
	 * @param id
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public void enable(Integer id) {
		User dbUser = userInfoRepository.getOne(id);
		dbUser.setState(CommonConstant.USER_STATUS_NORMAL);
		userInfoRepository.save(dbUser);
	}
	
	/**
	 * 禁用
	 * @param id
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	@Override
	public void unenable(Integer id) {
		User dbUser = userInfoRepository.getOne(id);
		dbUser.setState(CommonConstant.USER_STATUS_LOCK);
		userInfoRepository.save(dbUser);
	}

	@Override
	public void addUser(User user, Integer orgId, Integer roleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User user, Integer orgId, Integer roleId) {
		// TODO Auto-generated method stub
		
	}
}
