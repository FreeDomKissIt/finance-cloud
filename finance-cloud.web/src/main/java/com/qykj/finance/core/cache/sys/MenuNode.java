package com.qykj.finance.core.cache.sys;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.qykj.finance.core.ztree.TreeNode;
import com.qykj.finance.sys.model.Menu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuNode extends TreeNode {
	
	/** 地址 **/
	private String url;
	/** 级别 **/
	private Integer level;
	/** 排序 **/
	private Integer sort;
	/** 是否模块 **/
	private Boolean module = false;
	/** 是否开启权限控制 **/
	private Boolean control = true;
	/** 自定义菜单 用户id **/
	private Integer userId;
	
	public MenuNode() {
		
	}
	
	public MenuNode(Integer id, String label, String parentId) {
		super(id, label, parentId);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1710334815331240997L;
	private List<MenuNode> children;
	
	public static MenuNode createRoot(String name, List<MenuNode> children, boolean checked) {
		MenuNode root = new MenuNode();
		root.setLabel(name);
		root.setIsParent(true);
		root.setChildren(children);
		root.setOpen(true);
		root.setChecked(checked);
		return root;
	}
	
	public static MenuNode createModule(String name, boolean checked) {
		MenuNode module = new MenuNode();
		module.setLabel(name);
		module.setModule(true);
		module.setIsParent(true);
		module.setChecked(checked);
		return module;
	}
	
	/**
	 * @param menu
	 * @param checked
	 * @param isManagerMenu 是否菜单管理模块
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	public static MenuNode createByMenu(Menu menu, boolean checked, int type) {
		MenuNode module = new MenuNode();
		BeanUtils.copyProperties(menu, module);
		module.setLabel(menu.getName());
		module.setIsParent(menu.getParent());
		module.setChecked(checked);
		if (!menu.getParent() && Menu.MENU_MANAGER == type) { // 菜单管理模块使用时 叶子节点不可点击查看列表
			module.setChkDisabled(true);
		}
		
		return module;
	}
	
	/**
	 * 获取当前级+下级所有菜单数量
	 * @return
	 * @author wenjing
	 * @see [相关类/方法]
	 * @since V1.0.0
	 */
	public long getSize() {
		long size = 1;
		if (CollectionUtils.isNotEmpty(children)) {
			for (MenuNode menuNode : children) {
				size += menuNode.getSize();
			}
		}
		return size;
	}
	
	public static final String[] sysMenus = {"菜单管理", "行政区划管理"};
	
	public boolean isSysMenu() {
		for (String name : sysMenus) {
			if (name.equals(this.getLabel())) {
				return true;
			}
		}
		
		return false;
	}
}
