package com.qykj.finance.core.util;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.ResponseEntity;

import com.github.pagehelper.Page;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 响应对象 文件名: ActionResponse.java <br/>
 * 创 建 人: wenjing <br/>
 * 版 本 号: V1.0.0 <br/>
 */
@Accessors(chain = true)
@Getter
@Setter
@Slf4j
public class ActionPageResponse<T> {
	/**
	 * 响应代码
	 */
	private int code = 200;
	/**
	 * 响应成功与否
	 */
	private boolean success = true;
	/**
	 * 响应信息
	 */
	private String message = "";

	private T condition;

	private int first = 0;

	private int pageNo;

	private int pageSize;

	/**
	 * 响应数据
	 */
	private List<T> result;

	private long total;

	private int totalPage;

	/**
	 * 根据page创建响应对象
	 * @param page
	 * @param condition
	 * @return
	 * @author    wenjing
	 * @see       [相关类/方法]
	 * @since     V1.0.0
	 */
	public static <T> ResponseEntity<ActionPageResponse<T>> create(Page<T> page, T condition) {
		ActionPageResponse<T> actionPageResponse = new ActionPageResponse<T>();
		try {
			BeanUtils.copyProperties(actionPageResponse, page);
			actionPageResponse.setResult(page.getResult());
			actionPageResponse.setPageNo(page.getPageNum());
			actionPageResponse.setFirst(page.getStartRow());
			actionPageResponse.setPageSize(page.getPageSize());
			actionPageResponse.setTotal(page.getTotal());
			actionPageResponse.setTotalPage(page.getPages());
			actionPageResponse.setCondition(condition);
		} catch (Exception e) {
			log.error("Page ->WebPage转化错误:{}", e);
		}

		return ResponseEntity.ok(actionPageResponse);
	}

}
