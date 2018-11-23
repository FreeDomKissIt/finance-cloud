package com.qykj.finance.core.ztree;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * TreeNode tree 节点对象 文件名: TreeNode.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Setter
@Getter
public class TreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4354651025125642778L;
	// id
	private Integer id;
	// 名称
	private String label;
	// 是否父节点
	private Boolean isParent = true;
	// 样式
	private String cls;
	// 父节点id
	private String parentId;
	// 额外参数
	private Map<String, String> extra;
	// 是否选中
	private Boolean checked = false;
	// 是否半选中(子节点选中时使用)
	private Boolean halfCheck = false;
	// 是否禁用选中(可用状态)
	private Boolean chkDisabled = false;
	// 是否禁隐藏
	private Boolean isHidden = false;
	// 是展开子节点
	private Boolean open = false;
	private String click;

	public TreeNode(Integer id, String label, String parentId) {
		this.id = id;
		this.label = label;
		this.parentId = parentId;
	}

	public TreeNode() {

	}

}
