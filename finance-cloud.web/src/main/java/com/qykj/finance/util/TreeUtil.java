package com.qykj.finance.util;

import com.qykj.finance.core.ztree.TreeNode;

/**
 * 
 * 文件名: TreeUtil.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public class TreeUtil {
	// 请求根节点值
	public static final String EMPTY = "";
	// 根节点
	public static final Integer ROOT_ID_INT = 0;
	public static final String ROOT_ID = "0";

	public static TreeNode createDistrictRoot() {
		TreeNode root = new TreeNode(ROOT_ID_INT, "行政区划", null);
		return root;
	}

}
