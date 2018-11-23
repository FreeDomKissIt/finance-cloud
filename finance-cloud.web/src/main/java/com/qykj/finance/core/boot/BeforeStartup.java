package com.qykj.finance.core.boot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.qykj.finance.core.util.JDBCUtil;
import com.qykj.finance.core.util.JDBCUtil.DataBaseBean;
import com.qykj.finance.core.util.PropertyUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 启动前执行 文件名: BeforeStartup.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Slf4j
public class BeforeStartup {
	// url配置
	public static final String URL = "spring.datasource.url";
	// 数据库用户名配置
	public static final String USERNAME = "spring.datasource.username";
	// 数据库密码配置
	public static final String PASSWORD = "spring.datasource.password";
	// 数据库驱动名配置
	public static final String DRIVER_CLASS_NAME = "spring.datasource.driverClassName";
	// 数据库地址
	public static String url;
	// 数据库名字
	public static String dbname;

	/**
	 * 检查初始化新数据库
	 * 
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static void initDB() {
		Connection connection = getConnection();
		Set<String> sets = getDatabaseSet(connection);
		if (sets.contains(dbname)) {
			log.info("contains {}", dbname);
		} else {
			// 建库
			createDatabase(connection);
		}
		// 关闭当前连接
		JDBCUtil.releaseConnection(connection, null, null);
	}

	/**
	 * 获取数据库集
	 * 
	 * @param con
	 * @return
	 * @author wenjing
	 * @since V1.0.0
	 */
	public static Set<String> getDatabaseSet(Connection con) {
		Set<String> databases = new HashSet<String>();
		Statement pstmtInsert = null;
		ResultSet rs = null;
		try {
			pstmtInsert = con.createStatement();
			rs = pstmtInsert.executeQuery("show databases");
			while (rs.next()) {
				databases.add(rs.getString(1));
			}
		} catch (Exception e) {
			log.info("show databases error:", e);
		} finally {
			JDBCUtil.releaseConnection(null, pstmtInsert, rs);
		}
		return databases;
	}

	/**
	 * 创建数据库
	 * 
	 * @param con
	 * @author wenjing
	 * @since V1.0.0
	 */
	public static void createDatabase(Connection con) {
		Statement pstmtInsert = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		try {
			pstmtInsert = con.createStatement();
			builder.append("create database ").append(dbname).append(" CHARACTER SET utf8 COLLATE utf8_general_ci");
			pstmtInsert.execute(builder.toString());
		} catch (Exception e) {
			log.info("{} error:", builder.toString(), e);
		} finally {
			JDBCUtil.releaseConnection(null, pstmtInsert, rs);
		}
	}

	/**
	 * 获得连接
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static Connection getConnection() {
		DataBaseBean dataBaseBean = new DataBaseBean();
		PropertyUtil propertyUtil = PropertyUtil.getInstance();
		String driver = propertyUtil.getValue(DRIVER_CLASS_NAME);
		String url = propertyUtil.getValue(URL);
		String username = propertyUtil.getValue(USERNAME);
		String password = propertyUtil.getValue(PASSWORD);
		BeforeStartup.url = url;
		dataBaseBean.setDriver(driver);
		String headUrl = StringUtils.substringBeforeLast(url, "/");
		dbname = StringUtils.substringAfterLast(url, "/").split("\\?")[0];
		log.info(dbname);
		String defaultUrl = new StringBuilder(headUrl).append("/mysql").toString();
		dataBaseBean.setUrl(defaultUrl);
		dataBaseBean.setUsername(username);
		dataBaseBean.setPassword(password);
		Connection connection = JDBCUtil.getConnection(dataBaseBean);
		return connection;
	}

}
