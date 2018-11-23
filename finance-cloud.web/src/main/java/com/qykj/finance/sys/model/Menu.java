package com.qykj.finance.sys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.qykj.finance.core.persistence.model.BaseEnitity;

import lombok.Getter;
import lombok.Setter;

/**
 *  菜单
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
@Entity
@Table(name = "menu")
public class Menu extends BaseEnitity {
	public static final int  ALL_MENU = 0; // 所有菜单
	public static final int  MENU_MANAGER = 1;// 菜单管理
	public static final int  ROLE_MANAGER = 2;// 角色管理
	private static final long serialVersionUID = -6137602684580551004L;
	/** 父菜单Id **/
	private Integer parentId;
	/** 菜单名 **/
	@Column(length = 250)
	private String name;
	/** 地址 **/
	@Column(length = 250)
	private String url;
	/** 是否父节点 **/
	@Type(type="yes_no")
	private Boolean parent;
	/** 级别 **/
	private Integer level;
	/** 排序 **/
	private Integer sort;
	/** 是否模块 **/
	@Type(type="yes_no")
	private Boolean module;
	/** 是否开启权限控制  **/
	@Type(type="yes_no")
	private Boolean control;
	/** 自定义菜单 用户id **/
	private Integer userId;
	
	/**
	 * 创建模块
	 * @param name
	 * @param sort
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static Menu createModule(String name,int sort) {
		Menu menu = new Menu();
		menu.setModule(true);
		menu.setParent(true);
		menu.setName(name);
		menu.setSort(sort);
		menu.setLevel(1);
		
		menu.setControl(true);
		return menu;
	}
	
	/**
	 * 创建目录
	 * @param name
	 * @param sort
	 * @param parentId
	 * @param level
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static Menu createContents(String name,int sort,Integer parentId,int level) {
		Menu menu = new Menu();
		menu.setParent(true);
		menu.setName(name);
		menu.setSort(sort);
		menu.setParentId(parentId);
		menu.setLevel(level);
		
		menu.setControl(true);
		return menu;
	}
	
	/**
	 * 创建叶子节点
	 * @param name
	 * @param sort
	 * @param parentId
	 * @param url
	 * @param level
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static Menu createLeaf(String name,int sort,Integer parentId,String url,int level) {
		Menu menu = new Menu();
		menu.setParent(false);
		menu.setName(name);
		menu.setSort(sort);
		menu.setParentId(parentId);
		menu.setUrl(url);
		menu.setLevel(level);
		
		menu.setControl(true);
		return menu;
	}
	
}
