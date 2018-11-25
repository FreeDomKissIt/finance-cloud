package com.qykj.finance.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;

import com.qykj.finance.core.cache.CacheContainer;
import com.qykj.finance.core.cache.sys.MenuNode;
import com.qykj.finance.core.exception.PageException;
import com.qykj.finance.sys.model.Role;
import com.qykj.finance.sys.model.RoleMenu;
import com.qykj.finance.sys.model.User;

/**
 * 会话工具类 文件名: SessionUtil.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public class SessionUtil {
	/**
	 * 获取当前用户
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static User getCurrentUser() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
	
		if (user == null) {
			SessionUtil.logout();
			throw new PageException("会话已超时");
		}

		return user;
	}
	
	/**
	 * 是否超级管理员  sysadmin
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static boolean isRoot()  {
		try {
			User user = (User)SecurityUtils.getSubject().getPrincipal();
			if (user != null) {
				return user.isSys();
			}
		} catch (Exception e) {
			
		}
		return false;
	}

	/**
	 * 获得当前菜单
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static List<MenuNode> getCurrentMenus() {
		User user = getCurrentUser();
		Role role = user.getRole();
		List<MenuNode> userMenus = new ArrayList<>();
		if (role != null) {
			Set<RoleMenu> roleMenus = role.getRoleMenus();
			Set<Integer> sets = new HashSet<>();
			for (RoleMenu roleMenu : roleMenus) {
				sets.add(roleMenu.getMenuId());
			}
			List<MenuNode> webMenus = CacheContainer.getMenus();// 缓存获取
			filter(webMenus, sets, userMenus, user.getId());
		}

		return userMenus;
	}

	/**
	 * 过滤菜单
	 * @param webMenus
	 * @param roleMenus
	 * @param userMenus
	 * @param userId
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	private static void filter(List<MenuNode> webMenus, Set<Integer> roleMenus, List<MenuNode> userMenus,Integer userId) {
		if(CollectionUtils.isNotEmpty(webMenus))
		for (MenuNode webMenu : webMenus) {
			Integer id = Integer.valueOf(webMenu.getId());
			// 权限控制菜单过滤
			if (roleMenus.contains(id) || !webMenu.getControl()) {
				List<MenuNode> childrens = webMenu.getChildren();
				List<MenuNode> userChildrens = new ArrayList<>();
				MenuNode userMenu = new MenuNode();
				BeanUtils.copyProperties(webMenu, userMenu);
				filter(childrens, roleMenus, userChildrens,userId);
				userMenu.setChildren(userChildrens);
				// 过滤自定义菜单
				if(!webMenu.getControl()) {
					if(userId!=null &&userId.equals(webMenu.getUserId())) {
						userMenus.add(userMenu);
					}else if(webMenu.getUserId()==null){
						userMenus.add(userMenu);
					}
				}else {
					userMenus.add(userMenu);//非自定义菜单
				}
			}
		}
	}

	/**
	 * 获得当前登录单位
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	/*public static Org getCurrentOrg() {
		User user = getCurrentUser();
		if (user != null) {
			Org org = user.getOrg();
			return org;
		}
		return null;
	}*/

	/**
	 * 获得当前用户角色
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static Role getCurrentRole() {
		User user = getCurrentUser();
		if (user != null) {
			Role role = user.getRole();
			return role;
		}
		return null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

}
