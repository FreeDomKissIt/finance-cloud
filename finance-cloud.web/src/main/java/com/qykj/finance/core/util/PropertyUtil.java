package com.qykj.finance.core.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;

import lombok.extern.slf4j.Slf4j;

/**
 *  Properties工具类
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Slf4j
public class PropertyUtil {
	/**
	 *  web环境properties文件名{@value}  例 application.properties 即 application
	 */
	private String propertyFilePath = "application";
	/**
	 * ResourceBundle
	 */
	private ResourceBundle bundle;
	/**
	 * 是否以web方式启动
	 */
	private static boolean isWeb = true;
	/**
	 * Properties对象
	 */
	private Properties prop;
	/**
	 * web 内置 PropertyUtil
	 */
	private static PropertyUtil webpropertyUtil;
	private static PropertyUtil propertyUtil;

	/**
	 * 私有构造方法
	 */
	private PropertyUtil() {
		if(isWeb)
		this.bundle = ResourceBundle.getBundle(this.propertyFilePath);
	}

	/**
	 * 设置使用方式 多线程使用 勿用此方法
	 * @param isWeb
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public static void setWeb(boolean isWeb) {
		PropertyUtil.isWeb = isWeb;
	}

	/**
	 * 设置Properties对象
	 * @param prop
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public void setProp(Properties prop) {
		this.prop = prop;
	}

	/**
	 * 获得一个实例
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static synchronized PropertyUtil getInstance() {
		if (isWeb) {
			if (webpropertyUtil == null)
				webpropertyUtil = new PropertyUtil();
			return webpropertyUtil;
		} else {
			if (propertyUtil == null) {
				propertyUtil = new PropertyUtil();
				InputStream in = null;
				try {
					String filePath = ClassLoader.getSystemResource("application.properties").getFile();
					filePath = URLDecoder.decode(filePath, "UTF-8");
					log.info(filePath);
					in = new BufferedInputStream(
							new FileInputStream(filePath));
					Properties properties = new Properties();
					properties.load(in);
					propertyUtil.setProp(properties);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly(in);
				}
			}

			return propertyUtil;
		}
	}

	/**
	 * 根据key取值
	 * @param key
	 * @return
	 * @author    wenjing
	 * @since     V1.0.0
	 */
	public String getValue(String key) {
		if (isWeb) {
			return this.bundle.getString(key);
		} else {
			return this.prop.getProperty(key);
		}
	}
}