package com.qykj.finance.core.persistence.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.qykj.finance.core.persistence.handler.MapRecordResultSetHandler;
import com.qykj.finance.core.persistence.handler.RecordResultSetHandlerBean;
import com.qykj.finance.core.persistence.service.CommonQueryService;
import com.qykj.finance.core.persistence.service.QueryService;
import com.qykj.finance.core.util.BeanUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 通用分页查询实现
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Service
@Slf4j
public class QueryServiceImpl extends CommonQueryService implements QueryService {

	/**
	 * 数据库连接池对象
	 */
	@Autowired
	private BeanUtil beanUtil;
	
	/**
	 * 根据id查询单个实体
	 * 
	 * @param sql
	 * @param clazz
	 * @param params
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public <T> T selectOneById(Class<T> clazz, Object id) {
		String sql = null;
		try {
			QueryRunner qr = new QueryRunner(dataSource);
			sql = BeanUtil.getSelectOneByIdSql(clazz);
			List<T> result = qr.query(sql, new RecordResultSetHandlerBean<T>(clazz, beanUtil), id);
			if (CollectionUtils.isNotEmpty(result))
				return result.get(0);
		} catch (Exception e) {
			log.error(sql + ":查询出错", e);
		}
		return null;
	}
	
	/**
	 * sql查询列表
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @since 1.0.0
	 */
	@Override
	public List<Map<String, Object>> select(String sql, Object... params) {
		try {
			QueryRunner qr = new QueryRunner(dataSource);
			List<Map<String, Object>> result = qr.query(sql, new MapRecordResultSetHandler(), params);

			return result;
		} catch (Exception e) {
			log.error(sql + ":查询出错", e);
		}
		return null;
	}

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
	@Override
	public Page<Map<String, Object>> selectPage(String sql, int pageNum, int pageSize, boolean needCount,
			Object... params) {
		try {
			int start = (pageNum - 1) * pageSize;
			Object[] values = null;
			if (params != null && params.length != 0) {
				values = Arrays.copyOf(params, params.length + 2);
				values[values.length - 2] = start;
				values[values.length - 1] = pageSize;

			} else {
				values = new Object[] { start, pageSize };
			}

			QueryRunner qr = new QueryRunner(dataSource);
			long totol = -1l;
			if (needCount)
				totol = this.countBySql(sql, params);

			Page<Map<String, Object>> page = new Page<Map<String, Object>>();
			List<Map<String, Object>> result = qr.query(sql + " limit ?,?", new MapRecordResultSetHandler(), values);

			page.setPageNum(pageNum);
			page.setPageSize(pageSize);
			page.setTotal(totol);
			page.addAll(result);

			return page;
		} catch (Exception e) {
			log.error(sql + ":查询出错", e);
		}
		return null;
	}
	
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
	@Override
	public Page<Map<String, Object>> selectPage(String sql, int pageNum, int pageSize, Object... params) {
		return selectPage(sql, pageNum, pageSize, true, params);
	}

}
