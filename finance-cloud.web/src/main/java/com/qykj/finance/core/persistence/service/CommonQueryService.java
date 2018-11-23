package com.qykj.finance.core.persistence.service;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.qykj.finance.core.persistence.handler.CountResultSetHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 通用查询接口 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Slf4j
public abstract class CommonQueryService {
	/**
	 * 数据库连接池
	 */
	@Autowired
	protected DataSource dataSource;

	public static final int FIX_INDEX = 1;
	public static final String SQL_ORDER = "order";
	public static final String SQL_FROM = " from";

	
	/**
	 * 查询总数
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数
	 * @return
	 * @since V1.0.0
	 */
	protected long countBySql(String sql, Object... params) {
		long result = 0;
		try {
			String lowerSql = sql.toLowerCase();
			int index = lowerSql.indexOf(SQL_FROM);
			int indexOrder = lowerSql.indexOf(SQL_ORDER);

			if (indexOrder > 0) {
				sql = sql.substring(0, indexOrder - FIX_INDEX);
			}
			StringBuilder sqlBuilder = new StringBuilder();
			if (lowerSql.contains("distinct")) {
				sqlBuilder.append("SELECT COUNT(*) AS NUM FROM (").append(sql).append(") a");
			} else if (lowerSql.contains("group")) {
				sqlBuilder.append("SELECT COUNT(*) AS NUM FROM (").append(sql).append(") a");
			} else {
				sqlBuilder.append("SELECT COUNT(*) AS NUM").append(sql.substring(index));
			}
			QueryRunner qr = new QueryRunner(dataSource);
			result = qr.query(sqlBuilder.toString(), new CountResultSetHandler(), params);
		} catch (Exception e) {
			log.error(sql + ":查询出错", e);
		}
		return result;
	}
}
