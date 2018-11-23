package com.qykj.finance.core.persistence.handler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 *  数据库记录转Map
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
public class MapRecordResultSetHandler implements ResultSetHandler<List<Map<String, Object>>> {
	/**
	 * 处理方法
	 */
	@Override
	public List<Map<String, Object>> handle(ResultSet rs) throws SQLException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		List<String> columnNameList = new ArrayList<String>();
		for (int t = 0; t < rsmd.getColumnCount(); t++) {
			String columnName = rsmd.getColumnLabel(t + 1);
			columnNameList.add(columnName);
		}
		while (rs.next()) {
			Map<String, Object> record = new HashMap<String, Object>();
			for (String key : columnNameList) {
				record.put(key, rs.getObject(key));
			}
			result.add(record);
		}
		return result;
	}

}
