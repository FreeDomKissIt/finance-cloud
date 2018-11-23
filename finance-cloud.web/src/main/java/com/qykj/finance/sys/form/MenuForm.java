package com.qykj.finance.sys.form;

import lombok.Getter;
import lombok.Setter;

/**
 * 菜单 文件名: Menu.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Getter
@Setter
public class MenuForm {
	/** 父菜单Id **/
	private Integer id;
	/** 父菜单Id **/
	private Integer parentId;
	/** 菜单名 **/
	private String name;
	/** 地址 **/
	private String url;
	/** 是否父节点 **/
	private Boolean parent;
	/** 排序 **/
	private Integer sort;
	/** 是否开启权限控制 **/
	private Boolean control = true;
}
