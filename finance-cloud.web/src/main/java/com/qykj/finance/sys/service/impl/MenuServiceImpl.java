package com.qykj.finance.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.qykj.finance.core.cache.CacheContainer;
import com.qykj.finance.core.cache.sys.MenuNode;
import com.qykj.finance.core.persistence.service.QueryService;
import com.qykj.finance.core.persistence.service.impl.AbstractQueryServiceImpl;
import com.qykj.finance.sys.model.Menu;
import com.qykj.finance.sys.model.Role;
import com.qykj.finance.sys.model.RoleMenu;
import com.qykj.finance.sys.model.User;
import com.qykj.finance.sys.repository.MenuRepository;
import com.qykj.finance.sys.service.MenuService;
import com.qykj.finance.sys.service.RoleService;
import com.qykj.finance.util.SessionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 菜单接口实现 文件名: MenuServiceImpl.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Service
@Slf4j
public class MenuServiceImpl extends AbstractQueryServiceImpl<Menu> implements MenuService {
	
	// 用户快捷查询菜单目录id 固定为9999
	public static final int USER_DEFINE_MENU_ID = 9999;
	@Autowired
	MenuRepository menuRepository;
	@Autowired
	RoleService roleService;
	@Autowired
	QueryService queryService;
	
	/**
	 * 添加新菜单
	 * @param org
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public Menu addMenu(Menu menu) {
		setLevel(menu);
		menu = menuRepository.save(menu);
		fetchAllMenusToCache();
		return menu;
	}
	
	/**
	 * 通用删除覆盖
	 * @param id
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public void delete(Integer id) {
		repository.delete(id);
		fetchAllMenusToCache();
	}
	
	/**
	 * 通用批量删除覆盖
	 * @param id
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public void delete(List<Integer> idList) {
		for (Integer id : idList) {
			repository.delete(id);
		}
		fetchAllMenusToCache();
	}
	
	private void setLevel(Menu menu) {
		Integer parentId = menu.getParentId();
		if (null == menu.getParentId()) {
			menu.setLevel(1);
			menu.setModule(true);
			menu.setParent(true);
		} else {
			Menu parentMenu = menuRepository.findOne(parentId);
			int level = parentMenu.getLevel();
			level += 1;
			menu.setLevel(level);
		}
	}
	
	/**
	 * 更新菜单
	 * @param org
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public Menu updateMenu(Menu menu) {
		Menu dbMenu = menuRepository.findOne(menu.getId());
		dbMenu.setName(menu.getName());
		dbMenu.setParent(menu.getParent());
		dbMenu.setParentId(menu.getParentId());
		dbMenu.setSort(menu.getSort());
		dbMenu.setUrl(menu.getUrl());
		Menu updateMenu = menuRepository.save(dbMenu);
		fetchAllMenusToCache();
		return updateMenu;
	}
	
	/**
	 * 获得所有模块菜单
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@Override
	public List<Menu> getAllModules() {
		Boolean module = true;// 模块菜单
		return menuRepository.findByModuleOrderBySort(module);
	}
	
	/**
	 * 获得子菜单
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	@Override
	public List<Menu> getAllChildrens(Menu menu) {
		if (menu.getParent()) { // 只有父菜单才查询子节点
			return menuRepository.findByParentIdOrderBySort(menu.getId());
		}
		return null;
	}
	
	/**
	 * 获得所有菜单列表(包含下级) 非管理使用 不过滤权限
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public List<MenuNode> getAllMenus() {
		return getAllMenus(Menu.ALL_MENU);
	}
	
	/**
	 * 获得所有菜单列表(包含下级)
	 * @param isManager 是否菜单管理使用
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	public List<MenuNode> getAllMenus(int type) {
		List<MenuNode> menuNodes = new ArrayList<>();
		List<Menu> modules = getAllModules();// 获得顶级菜单列表 (模块)
		if(CollectionUtils.isNotEmpty(modules))
		for (Menu menu : modules) {
			MenuNode module = MenuNode.createModule(menu.getName(), false);// 创建模块菜单
			BeanUtils.copyProperties(menu, module);
			processChildren(module, menu, type);// 处理子节点菜单
			menuNodes.add(module);
		}
		return menuNodes;
	}
	
	/**
	 * 处理子节点菜单
	 * @param parentMenuNode 父节点
	 * @param parentMenu 父节点菜单菜单
	 * @param isManager
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	private void processChildren(MenuNode parentMenuNode, Menu parentMenu, int type) {
		// 获取子节点
		List<Menu> childrens = getAllChildrens(parentMenu);
		// 构造子节点点数据
		if (CollectionUtils.isNotEmpty(childrens)) {
			List<MenuNode> menuNodes = new ArrayList<>();// 子菜单列表
			for (Menu menu : childrens) {
				MenuNode menuNode = MenuNode.createByMenu(menu, false, type);// 转化数据为树节点对象
				// 权限控制菜单才处理叶子节点 或者获取所有菜单是处理叶子节点
				if ((menu.getControl() && menu.getParent()) || (Menu.ALL_MENU == type && menu.getParent())) {
					processChildren(menuNode, menu, type);// 处理子节点
				}
				if (Menu.MENU_MANAGER == type) {
					menuNode.setUrl(null);
				}
				if (Menu.ALL_MENU == type || menu.getControl()) {
					if (menuNode.isSysMenu()&&Menu.ALL_MENU != type) {
						if (SessionUtil.isRoot()) {
							menuNodes.add(menuNode);
						}
					} else {
						menuNodes.add(menuNode);
					}
				}
			}
			parentMenuNode.setChildren(menuNodes);
		}
	}
	
	/**
	 * 获取角色管理页面菜单
	 * @param roleId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public MenuNode getRoleManagerMenu(Integer roleId) {
		List<MenuNode> menus = getRoleManagerMenus(roleId);// 缓存获取
		boolean hasChecked = false;
		if(CollectionUtils.isNotEmpty(menus))
		for (MenuNode menuCache : menus) {
			if (menuCache.getChecked()) {
				hasChecked = true;
				break;
			}
		}
		
		MenuNode root = MenuNode.createRoot("功能权限", menus, hasChecked);
		return root;
	}
	
	/**
	 * 获取菜单用于管理
	 * @param roleId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public MenuNode getMenuForManager() {
		List<MenuNode> menus = getAllMenus(Menu.MENU_MANAGER);
		MenuNode root = MenuNode.createRoot("所有菜单", menus, false);
		return root;
	}
	
	/**
	 * 角色管理页面菜单列表
	 * @param roleId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	public List<MenuNode> getRoleManagerMenus(Integer roleId) {
		List<MenuNode> selectedMenuNodes = new ArrayList<>();
		List<MenuNode> menuNodes = getAllMenus(Menu.ROLE_MANAGER);// 此处为角色管理界面 不从缓存获取 直接取数据库
		// 根据角色设置当前菜单树选中状态
		if (null != roleId) {
			Role role = roleService.findOne(roleId);
			if (null != role) {
				Set<RoleMenu> roleMenus = role.getRoleMenus();
				Set<Integer> sets = new HashSet<>();
				for (RoleMenu roleMenu : roleMenus) {
					sets.add(roleMenu.getMenuId());
				}
				// 设置菜单选择状态
				selectMenusByIds(menuNodes, sets, selectedMenuNodes, null);
				return selectedMenuNodes;
			}
		}
		
		return menuNodes;
	}
	
	/**
	 * 生成选中状态
	 * @param menuNodes 父节点下所有子节点
	 * @param roleMenus 角色包含菜单id集合
	 * @param selectedMenus 当前层级菜单数据包含选中状态
	 * @param parentMenuNode 父节点
	 * @param isAll 是否显示所有
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	private void selectMenusByIds(List<MenuNode> menuNodes, Set<Integer> roleMenus, List<MenuNode> selectedMenus,
	        MenuNode parentMenuNode) {
		int len = menuNodes.size();// 当前节点下子菜单个数
		int count = 0;// 计数器
		boolean parentOpen = false;
		for (int i = 0; i < len; i++) {
			MenuNode menuNode = menuNodes.get(i);
			Integer id = Integer.valueOf(menuNode.getId());
			if (menuNode.getControl()) {
				MenuNode selectedMenuNode = new MenuNode();
				List<MenuNode> childrens = menuNode.getChildren();
				List<MenuNode> selectChildrenNodes = new ArrayList<>();
				BeanUtils.copyProperties(menuNode, selectedMenuNode);
				// 根据角色菜单id集合设置选中状态
				if (roleMenus.contains(id)) {
					selectedMenuNode.setChecked(true);
					count++;// 选中计数
				}
				if (childrens != null) {
					selectMenusByIds(childrens, roleMenus, selectChildrenNodes, selectedMenuNode);
					if (selectedMenuNode.getOpen()) {
						parentOpen = true;
					}
				}
				selectedMenuNode.setChildren(selectChildrenNodes);
				selectedMenus.add(selectedMenuNode);
			} else {
				count++;// 非管控菜单默认算选中状态
			}
		}
		
		// 设置父节点展开状态
		if (null != parentMenuNode) {
			// 当子节点未完全选中(全部无选中也不展开)或子节点有节点半选中
			if ((count < len && count != 0) || parentOpen) {
				parentMenuNode.setOpen(true);
			}
		}
	}
	
	@Override
	public Page<Map<String, Object>> findPageList(Integer pageNum, Integer pageSize, Integer id) {
		Page<Map<String, Object>> menus = null;
		if (null == id) {
			menus = queryService.selectPage(MenuRepository.QUERY_LIST_ROOT, pageNum, pageSize);
		} else {
			menus = queryService.selectPage(MenuRepository.QUERY_LIST, pageNum, pageSize, id);
		}
		return menus;
	}
	
	// 排序号数据位置
	public static final int SORT_INDEX = 0;
	// 排序号内容别名
	public static final String ALIAS = "sort";
	
	/**
	 * 获得儿子菜单的下一个排序
	 * @param parentId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public long getNextChildrenSort(Integer parentId) {
		long sort = 0L;
		List<Map<String, Object>> datas = null;
		if (parentId == null) {
			datas = queryService.select(MenuRepository.QUERY_MODULES_NEXT_SORT);
		} else {
			datas = queryService.select(MenuRepository.QUERY_NEXT_SORT, parentId);
		}
		if (CollectionUtils.isNotEmpty(datas)) {
			Object value = datas.get(SORT_INDEX).get(ALIAS);
			if (value != null) {
				sort = (long)value;
			}
		}
		
		return sort;
	}
	
	// 查询参数
	public static final String USER_DEFINED_KEY_PARAMS = "?queryId=";
	// 用户自定义快捷查询菜单id
	public static final int USER_DEFINED_PARENTID = 9999;
	
	/**
	 * 添加自定义菜单
	 * @param name
	 * @param url
	 * @param contionId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public Menu addUserDefined(Integer userId, String name, String url, Integer queryId) {
		StringBuilder builder = new StringBuilder();
		builder.append(url).append(USER_DEFINED_KEY_PARAMS).append(queryId);
		Menu menu = Menu.createLeaf(name, 0, USER_DEFINED_PARENTID, builder.toString(), 3);
		menu.setControl(false);
		menu.setUserId(userId);
		menu = menuRepository.save(menu);
		fetchAllMenusToCache();
		return menu;
	}
	
	/**
	 * 获得用户自定义菜单数据
	 * @param parentId
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public List<MenuNode> getUserDefinedTree(Integer parentId) {
		List<Menu> menus = menuRepository.findByParentIdOrderBySort(USER_DEFINED_PARENTID);
		List<MenuNode> menuNodes = new ArrayList<>();
		User user = SessionUtil.getCurrentUser();
		Integer userId = user.getId();
		if(CollectionUtils.isNotEmpty(menus))
		for (Menu menu : menus) {
			if (userId.equals(menu.getUserId())) {
				MenuNode menuNode = MenuNode.createByMenu(menu, false, Menu.ALL_MENU);// 转化数据为树节点对象
				menuNodes.add(menuNode);
			}
		}
		return menuNodes;
	}
	
	/**
	 * 更新菜单名称
	 * @param id
	 * @param name
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public void updateMenuName(Integer id, String name) {
		Menu dbMenu = menuRepository.findOne(id);
		dbMenu.setName(name);
		menuRepository.save(dbMenu);
		fetchAllMenusToCache();
	}
	
	/**
	 * 将菜单数据保存到缓存中
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	@Override
	public void fetchMenusToCache() {
		/*
		 * List<MenuNode> caches = CacheContainer.getMenus(); long count = menuRepository.count(); long cacheCount =
		 * getCount(caches); if (count != cacheCount) {
		 */
		// log.debug("数据库菜单数:{},缓存菜单数:{},更新中...", count, cacheCount);
		fetchAllMenusToCache();
		/* } */
	}
	
	public void fetchAllMenusToCache() {
		log.debug("缓存菜单数更新中...");
		// 刷新菜单缓存数据
		List<MenuNode> menuNodes = getAllMenus();
		CacheContainer.put(CacheContainer.MENU_KEY, menuNodes);
		log.debug("缓存菜单数:{},更新完成...", getCount(menuNodes));
	}
	
	/**
	 * 计算菜单数量
	 * @param menuNodes
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	public long getCount(List<MenuNode> menuNodes) {
		long count = 0;
		if (CollectionUtils.isNotEmpty(menuNodes)) {
			for (MenuNode menuNode : menuNodes) {
				count += menuNode.getSize();
			}
		}
		return count;
	}
}
