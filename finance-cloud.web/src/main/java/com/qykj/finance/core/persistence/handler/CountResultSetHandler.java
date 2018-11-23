package com.qykj.finance.core.persistence.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * 处理数据库计数量 
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
public class CountResultSetHandler implements ResultSetHandler<Long> {
	public static final long ZERO_COUNT = 0L;// 计数0
	public static final int COUNT_INDEX = 1;// count位置

	/**
	 * 处理方法
	 */
	@Override
	public Long handle(ResultSet rs) throws SQLException {
		long result = ZERO_COUNT;
		while (rs.next()) {
			result = rs.getLong(COUNT_INDEX);
			break;
		}
		return result;
	}
}
