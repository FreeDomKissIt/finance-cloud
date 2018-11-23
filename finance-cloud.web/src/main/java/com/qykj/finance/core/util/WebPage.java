package com.qykj.finance.core.util;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.github.pagehelper.Page;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *  分页帮助类 文件名: WebPage.java <br/>
 *  创 建 人: wenjing <br/>
 *  版 本 号: V1.0.0 <br/>
 */
@Data
@Slf4j
public class WebPage<T> {
	/**
	 * 页码，从1开始
	 */
	private int pageNum;
	/**
	 * 页面大小
	 */
	private int pageSize;
	/**
	 * 起始行
	 */
	private int startRow;
	/**
	 * 末行
	 */
	private int endRow;
	/**
	 * 总数
	 */
	private long total;
	/**
	 * 总页数
	 */
	private int pages;

	/**
	 * 数据内容
	 */
	private List<T> pageDatas;

	/**
	 * 构造方法
	 * @param page
	 */
	public WebPage(Page<T> page) {
		try {
			BeanUtils.copyProperties(this, page);
		} catch (Exception e) {
			log.error("Page ->WebPage转化错误:{}", e);
		}
		pageDatas = page.getResult();
	}

}
