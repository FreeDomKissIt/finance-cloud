package com.qykj.finance.sys.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qykj.finance.core.persistence.model.BaseEnitity;

import lombok.Getter;
import lombok.Setter;

/**
 *  角色拥有菜单id
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
@Entity
@Table(name = "role_menu")
public class RoleMenu extends BaseEnitity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3014230824164785678L;
	/** 菜单id **/
	private Integer menuId;
	
	public static RoleMenu builder(Integer menuId) {
		RoleMenu roleMenu = new RoleMenu();
		roleMenu.setMenuId(menuId);
		return roleMenu;
	}
}
