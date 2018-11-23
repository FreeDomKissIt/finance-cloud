package com.qykj.finance.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qykj.finance.sys.model.Menu;

/**
 *  菜单仓库
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
	// 根菜单查询
	public static final String QUERY_LIST_ROOT = "select id,sort,name,url from menu where level=1 and control='Y' order by sort";
	// 子菜单查询
	public static final String QUERY_LIST = "select id,sort,name,url from menu where parentId=? and control='Y' order by sort";
	// 查询菜单模块当前级下一个排序号
	public static final String QUERY_MODULES_NEXT_SORT = "select MAX(sort)+1 as sort from menu where parentId is null";
	// 查询菜单当前级下一个排序号
	public static final String QUERY_NEXT_SORT = "select MAX(sort)+1 as sort from menu where parentId = ?";

	/**
	 * 查找模块菜单 根据sort排序
	 * @param module
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	List<Menu> findByModuleOrderBySort(Boolean module);

	/**
	 * 根据父菜单id查找子菜单 根据sort排序
	 * @param id
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	List<Menu> findByParentIdOrderBySort(Integer id);
}
