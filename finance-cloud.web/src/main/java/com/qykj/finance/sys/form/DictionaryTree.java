
package com.qykj.finance.sys.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DictionaryTree {
	private Integer id;
	/** 父菜单Id **/
	private Integer parentId;
	/** 菜单名 **/
	private String label;
	/** 地址 **/
	private String url;
	/** 是否父节点 **/
	private Boolean parent;
	/** 级别 **/
	private Integer level;
	/** 排序 **/
	private Integer sort;
	/** 是否模块 **/
	private Boolean module;
	/** 是否开启权限控制  **/
	private Boolean control;
	/** 是否选中  **/
	private boolean checked;
	/** 是否打开  **/
	private boolean open = false;
	private List<DictionaryTree> children;
}
