package com.qykj.finance.core.persistence.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;

/**
 *  通用分页查询接口
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public interface QueryService {

	/**
	 * sql查询列表
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @since 1.0.0
	 */
	public List<Map<String, Object>> select(String sql, Object... params);

	/**
	 * sql查询完整分页，带count ( mysql )
	 * 
	 * @param sql
	 * @param pageNum
	 * @param pageSize
	 * @param params
	 * @return
	 * @since 1.0.0
	 */
	public Page<Map<String, Object>> selectPage(String sql, int pageNum, int pageSize, Object... params);

	/**
	 * sql查询分页，支持是否计算count  ( mysql )
	 * 
	 * @param sql
	 * @param pageNum
	 * @param pageSize
	 * @param needCount
	 *            是否需要计算count
	 * @param params
	 * @return
	 * @since 1.0.0
	 */
	public Page<Map<String, Object>> selectPage(String sql, int pageNum, int pageSize, boolean needCount,
			Object... params);

	/**
	 * 根据id查询单个实体
	 * 
	 * @param sql
	 * @param clazz
	 * @param params
	 * @return
	 * @since 1.0.0
	 */
	public <T> T selectOneById(Class<T> clazz, Object id);
}
