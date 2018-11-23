package com.qykj.finance.sys.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.qykj.finance.core.cache.sys.MenuNode;
import com.qykj.finance.core.persistence.service.BaseQueryService;
import com.qykj.finance.sys.model.Menu;

/**
 * 菜单接口 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public interface MenuService extends BaseQueryService<Menu> {
	/**
	 * 添加新菜单
	 * 
	 * @param menu
	 * @return
	 * @since 1.0.0
	 */
	Menu addMenu(Menu menu);

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 * @return
	 * @since 1.0.0
	 */
	Menu updateMenu(Menu menu);

	/**
	 * 获得所有模块菜单
	 * 
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	List<Menu> getAllModules();

	/**
	 * 获得子菜单
	 * 
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	List<Menu> getAllChildrens(Menu menu);

	/**
	 * 获得页面使用菜单数据列表
	 * 
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	List<MenuNode> getAllMenus();

	/**
	 * 获取角色对应菜单
	 * 
	 * @param roleId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	MenuNode getRoleManagerMenu(Integer roleId);

	/**
	 * 菜单分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param id
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	Page<Map<String, Object>> findPageList(Integer pageNum, Integer pageSize, Integer id);

	/**
	 * 获得数据库菜单数据列表
	 * 
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	MenuNode getMenuForManager();

	/**
	 * 获得儿子菜单的下一个排序
	 * 
	 * @param parentId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	long getNextChildrenSort(Integer parentId);
	
	/**
	 *  添加自定义菜单
	 * @param userId
	 * @param name
	 * @param url
	 * @param contionId
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	Menu addUserDefined(Integer userId, String name, String url, Integer contionId);

	/**
	 * 获得用户自定义菜单数据
	 * @param parentId
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	List<MenuNode> getUserDefinedTree(Integer parentId);

	/**
	 * 更新菜单名称
	 * @param id
	 * @param name
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	void updateMenuName(Integer id, String name);

	/**
	 * 将菜单数据保存到缓存中
	 * 
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	void fetchMenusToCache();
}
