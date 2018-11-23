package com.qykj.finance.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 文件名: JDBCSync.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Slf4j
public class JDBCUtil {
	/**
	 * 数据库对象
	 */
	public static class DataBaseBean {
		private String url;
		private String driver;
		private String username;
		private String password;

		public DataBaseBean() {

		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getDriver() {
			return driver;
		}

		public void setDriver(String driver) {
			this.driver = driver;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static Connection getConnection(DataBaseBean dataBaseBean) {
		try {
			Class.forName(dataBaseBean.getDriver());
			Connection conn = DriverManager.getConnection(dataBaseBean.getUrl(), dataBaseBean.getUsername(),
					dataBaseBean.getPassword());
			return conn;
		} catch (Exception e) {
			log.error("{}", e);
			return null;
		}
	}

	/**
	 * 
	 * @param con
	 * @param statement
	 * @param rs
	 */
	public static void releaseConnection(Connection con,Statement statement, ResultSet rs) {
		try {
			if (con != null) {
				con.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
