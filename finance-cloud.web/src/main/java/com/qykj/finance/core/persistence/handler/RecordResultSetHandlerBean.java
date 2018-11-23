package com.qykj.finance.core.persistence.handler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.qykj.finance.core.util.BeanUtil;

/**
 * 查询List结果处理类
 * @author wenjing
 * @version v1.0.0
 */
public class RecordResultSetHandlerBean<T> implements ResultSetHandler<List<T>> {
	/**
	 * 泛型对象
	 */
	private Class<T> entityClass;
	private BeanUtil beanUtil;

	public RecordResultSetHandlerBean(Class<T> entityClass, BeanUtil beanUtil) {
		this.entityClass = entityClass;
		this.beanUtil = beanUtil;
	}
	/**
	 * 处理方法
	 */
	@Override
	public List<T> handle(ResultSet rs) throws SQLException {
		List<T> result = new ArrayList<T>();

		ResultSetMetaData rsmd = rs.getMetaData();
		List<String> columnNameList = new ArrayList<String>();
		for (int t = 0; t < rsmd.getColumnCount(); t++) {
			String columnName = rsmd.getColumnLabel(t + 1);
			columnNameList.add(columnName);
		}

		while (rs.next()) {
			T record = beanUtil.getDbData(entityClass, rs);
			result.add(record);
		}
		return result;
	}

}
